package com.example.lateritia.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lateritia.R
import com.example.lateritia.database.VehicleRepository
import com.example.lateritia.database.VehicleRoomDatabase
import com.example.lateritia.databinding.FragmentSettingsBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    @Inject lateinit var vehicleRepository:VehicleRepository

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
        val viewModelFactory = SettingsViewModelFactory(arguments.vehicleKey, vehicleRepository)

        // Get a reference to the ViewModel associated with this fragment.
        val settingsViewModelViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(SettingsViewModel::class.java)

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.settingsViewModel = settingsViewModelViewModel

        settingsViewModelViewModel.showSnackBarEvent.observe(viewLifecycleOwner, Observer {
            if (it == 1) { // Observed state is success.
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.success_settings_save),
                    Snackbar.LENGTH_SHORT
                ).show()
                settingsViewModelViewModel.doneShowingSnackbar()
            }
            if (it == 2) { // Observed state is failure.
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.failure_settings_save),
                    Snackbar.LENGTH_SHORT
                ).show()
                settingsViewModelViewModel.doneShowingSnackbar()
            }
        })

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
        binding.settingsViewModel?.editVehicle(
            binding.makeInputText.text.toString(),
            binding.modelInputText.text.toString(),
            binding.vinInputText.text.toString(),
            binding.registrationInputText.text.toString(),
            binding.divisionsInputText.text.toString().toIntOrNull() ?: 0,
            binding.fuelCapacityInputText.text.toString().toDoubleOrNull() ?: 0.0,
            binding.fuelReserveInputText.text.toString().toDoubleOrNull() ?: 0.0
            )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}