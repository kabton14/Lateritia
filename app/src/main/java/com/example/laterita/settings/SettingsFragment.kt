package com.example.laterita.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.laterita.R
import com.example.laterita.database.VehicleRoomDatabase
import com.example.laterita.databinding.FragmentSettingsBinding
import com.example.laterita.home.HomeFragment

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Get a reference to the binding object and inflate the fragment views.
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings,
            container, false)

        val application = requireNotNull(this.activity).application
        val arguments = SettingsFragmentArgs.fromBundle(requireArguments())

        // Create an instance of the ViewModel Factory.
        val dataSource = VehicleRoomDatabase.getDatabase(application).vehicleDao()
        val viewModelFactory = SettingsViewModelFactory(arguments.vehicleKey, dataSource)

        // Get a reference to the ViewModel associated with this fragment.
        val settingsViewModelViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(SettingsViewModel::class.java)

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.settingsViewModel = settingsViewModelViewModel

        binding.lifecycleOwner = this

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSettingsSave.setOnClickListener {
            editVehicle()
        }
    }

    private fun editVehicle() {
        if (isEntryValid() == true){
            binding.settingsViewModel?.editVehicle(
                binding.makeInputText.text.toString(),
                binding.modelInputText.text.toString(),
                binding.vinInputText.text.toString(),
                binding.registrationInputText.text.toString(),
                binding.fuelCapacityInputText.text.toString().toIntOrNull(),
                binding.fuelReserveInputText.text.toString().toIntOrNull()
            )
        }
    }

    private fun isEntryValid(): Boolean? {
        return binding.settingsViewModel?.areSettingsValid(
            binding.makeInputText.text.toString(),
            binding.modelInputText.text.toString(),
            binding.vinInputText.text.toString(),
            binding.registrationInputText.text.toString(),
            binding.fuelCapacityInputText.text.toString().toIntOrNull() ?: 0,
            binding.fuelReserveInputText.text.toString().toIntOrNull() ?: 0
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}