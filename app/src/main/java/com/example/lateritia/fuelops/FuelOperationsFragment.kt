package com.example.lateritia.fuelops

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.lateritia.R
import com.example.lateritia.database.VehicleRepository
import com.example.lateritia.database.VehicleRoomDatabase
import com.example.lateritia.databinding.FragmentOpBinding
import com.example.lateritia.settings.SettingsFragmentArgs

class FuelOperationsFragment : Fragment() {
    private var _binding: FragmentOpBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_op, container,
            false)

        var  application = requireNotNull(this.activity).application
        val arguments = FuelOperationsFragmentArgs.fromBundle(requireArguments())

        val dataSource = VehicleRoomDatabase.getDatabase(application).vehicleDao()
        val vehicleRepository = VehicleRepository(dataSource)
        val fuelOpsViewModel: FuelOperationsViewModel by activityViewModels {
            FuelOperationsViewModelFactory(arguments.vehicleKey,vehicleRepository)
        }

        fuelOpsViewModel.updateCurrentVehicle(arguments.vehicleKey)

        binding.operationsViewModel = fuelOpsViewModel
        binding.lifecycleOwner = this

        //Live data observers
        fuelOpsViewModel.navigateToPricePerLiter.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(
                    FuelOperationsFragmentDirections
                        .actionOperationsFragmentToPriceFragment())
                fuelOpsViewModel.onPricePerLiterNavigated()
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
