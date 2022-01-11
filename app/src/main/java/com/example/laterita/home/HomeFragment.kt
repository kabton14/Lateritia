package com.example.laterita.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.laterita.R
import com.example.laterita.database.VehicleRoomDatabase
import com.example.laterita.databinding.FragmentHomeBinding

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

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        var  application = requireNotNull(this.activity).application

        val dataSource = VehicleRoomDatabase.getDatabase(application).vehicleDao()

        val viewModelFactory = HomeViewModelFactory(dataSource)

        val homeViewModel = ViewModelProvider(this, viewModelFactory)
            .get(HomeViewModel::class.java)

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

        homeViewModel.navigateToOperations.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(
                    HomeFragmentDirections
                        .actionHomeFragmentToOperationsFragment())
                homeViewModel.onOperationsNavigated()
            }
        })

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