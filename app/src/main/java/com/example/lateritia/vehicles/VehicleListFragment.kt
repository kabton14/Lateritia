package com.example.lateritia.vehicles

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.lateritia.R
import com.example.lateritia.database.VehicleRoomDatabase
import com.example.lateritia.databinding.FragmentVehicleListBinding

class VehicleListFragment : Fragment() {

    private var _binding: FragmentVehicleListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_vehicle_list, container,
            false)

        val sharedPref = activity?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val currentVehicle = sharedPref?.getInt(getString(R.string.lateritia_default_vehicle),
            resources.getInteger(R.integer.lateritia_default_vehicle))?.toLong()?:14L

        var  application = requireNotNull(this.activity).application
        val dataSource = VehicleRoomDatabase.getDatabase(application).vehicleDao()
        val viewModelFactory = VehicleListViewModelFactory(dataSource, currentVehicle)
        val vehicleListViewModel = ViewModelProvider(this, viewModelFactory)
            .get(VehicleListViewModel::class.java)

        val adapter = VehicleAdapter(vehicleListViewModel, VehicleListener { vehicleId ->
            vehicleListViewModel.onDefaultIndicatorClicked(vehicleId)
        })

        binding.vehicleListViewModel = vehicleListViewModel
        binding.vehicleList.adapter = adapter
        binding.lifecycleOwner = this

        //Live data observers
        vehicleListViewModel.vehicles.observe(viewLifecycleOwner, Observer{
            it?.let {
                adapter.submitList(it)
            }

        })

        vehicleListViewModel.setDefaultVehicle.observe(viewLifecycleOwner, Observer { vehicle ->
            vehicle?.let {
                with(sharedPref?.edit()) {
                    this?.putInt(getString(R.string.lateritia_default_vehicle), vehicle.toInt())
                    this?.apply()
                }
                Toast.makeText(
                    application,
                    "Vehicle Selected.",
                    Toast.LENGTH_LONG
                ).show()
                this.findNavController().navigate(
                    VehicleListFragmentDirections
                        .actionVehicleListFragmentToHomeFragment())
                vehicleListViewModel.onDefaultVehicleSet()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}