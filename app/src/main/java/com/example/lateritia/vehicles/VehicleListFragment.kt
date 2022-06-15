package com.example.lateritia.vehicles

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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

        var  application = requireNotNull(this.activity).application
        val dataSource = VehicleRoomDatabase.getDatabase(application).vehicleDao()
        val viewModelFactory = VehicleListViewModelFactory(dataSource)
        val vehicleListViewModel = ViewModelProvider(this, viewModelFactory)
            .get(VehicleListViewModel::class.java)
        val adapter = VehicleAdapter()

        binding.vehicleListViewModel = vehicleListViewModel
        binding.lifecycleOwner = this

        //Live data observers

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}