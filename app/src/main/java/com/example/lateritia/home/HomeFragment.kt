package com.example.lateritia.home

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.lateritia.R
import com.example.lateritia.database.VehicleRoomDatabase
import com.example.lateritia.databinding.FragmentHomeBinding
import java.time.Duration

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container,
            false)

        val sharedPref = activity?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val defaultValue = resources.getInteger(R.integer.lateritia_default_vehicle)
        val currentVehicle = sharedPref?.getInt(getString(R.string.lateritia_default_vehicle), defaultValue)?.toLong()

        Toast.makeText(this.context, "$currentVehicle", Toast.LENGTH_SHORT).show()

        var  application = requireNotNull(this.activity).application
        val dataSource = VehicleRoomDatabase.getDatabase(application).vehicleDao()
        val viewModelFactory = HomeViewModelFactory(dataSource, currentVehicle?:14)
        val homeViewModel = ViewModelProvider(this, viewModelFactory)
            .get(HomeViewModel::class.java)

        currentVehicle?.let {
            homeViewModel.updateVehicle(it)
//            Toast.makeText(this.context, "${homeViewModel.vehicle.value.toString()}", Toast.LENGTH_LONG).show()
        }

        binding.homeViewModel = homeViewModel
        binding.lifecycleOwner = this

        //Live data observers
        homeViewModel.navigateToSettings.observe(viewLifecycleOwner, Observer {vehicle ->
            vehicle?.let {
                this.findNavController().navigate(
                    HomeFragmentDirections
                        .actionHomeFragmentToSettingsFragment(vehicle))
                homeViewModel.onSettingsNavigated()
            }
        })

        homeViewModel.navigateToOperations.observe(viewLifecycleOwner, Observer {vehicle ->
            vehicle?.let {
                this.findNavController().navigate(
                    HomeFragmentDirections
                        .actionHomeFragmentToOperationsFragment(it))
                homeViewModel.onOperationsNavigated()
            }
        })

        homeViewModel.navigateToVehicles.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(
                    HomeFragmentDirections
                        .actionHomeFragmentToVechiclesListFragment())
                homeViewModel.onVehiclesNavigated()
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_fragment -> {
                binding.homeViewModel?.apply {
                    onSettingsClicked()
                }
            }

            R.id.vehicle_list -> {
                binding.homeViewModel?.apply {
                    onVehiclesClicked()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}