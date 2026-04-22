package com.example.lateritia.fuelops.price

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.lateritia.R
import com.example.lateritia.databinding.FragmentPriceBinding
import com.example.lateritia.fuelops.FuelOperationsViewModel
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PriceFragment : Fragment() {
    private var _binding: FragmentPriceBinding? = null
    private val binding get() = _binding!!

    private val fuelOpsViewModel: FuelOperationsViewModel by activityViewModels()

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) fetchLocation()
    }

    private val speechLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val spoken = result.data
                ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                ?.firstOrNull() ?: return@registerForActivityResult
            val price = parseSpokenPrice(spoken)
            if (price != null) {
                binding.enterPriceInputText.setText(price)
            } else {
                showSnackbar(getString(R.string.voice_parse_error))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_price, container, false)
        binding.operationsViewModel = fuelOpsViewModel
        binding.lifecycleOwner = this

        fuelOpsViewModel.navigateToFuelLevel.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(
                    PriceFragmentDirections.actionPriceFragmentToFuelLevelFragment())
                fuelOpsViewModel.onFuelLevelNavigated()
            }
        })

        fuelOpsViewModel.showSnackbarEvent.observe(viewLifecycleOwner, Observer {
            it?.let {
                showSnackbar(it)
                fuelOpsViewModel.doneShowingSnackbar()
            }
        })

        fuelOpsViewModel.lastEntryAtLocation.observe(viewLifecycleOwner) { entry ->
            if (entry != null) {
                val date = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(entry.timestamp))
                binding.locationHintText?.text = getString(R.string.location_hint,
                    entry.pricePerLiter, date)
                binding.locationHintText?.visibility = View.VISIBLE
            } else {
                binding.locationHintText?.visibility = View.GONE
            }
        }

        requestLocationIfNeeded()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pplSubmitButton.setOnClickListener { setPrice() }
        binding.enterPriceInputLayout.setEndIconOnClickListener { launchSpeechRecognition() }
    }

    private fun requestLocationIfNeeded() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            fetchLocation()
        } else {
            locationPermissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun fetchLocation() {
        val lm = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try {
            val location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                ?: lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            location?.let { fuelOpsViewModel.setLocation(it.latitude, it.longitude) }
        } catch (e: SecurityException) {
            // location unavailable — hint stays hidden
        }
    }

    private fun launchSpeechRecognition() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.voice_prompt))
        }
        try {
            speechLauncher.launch(intent)
        } catch (e: ActivityNotFoundException) {
            showSnackbar(getString(R.string.voice_not_supported))
        }
    }

    private fun parseSpokenPrice(text: String): String? {
        val cleaned = text.replace(Regex("[^0-9.]"), "")
        return if (cleaned.toDoubleOrNull() != null) cleaned else null
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(requireActivity().findViewById(android.R.id.content),
            message, Snackbar.LENGTH_SHORT).show()
    }

    private fun setPrice() {
        binding.operationsViewModel?.setPricePerLiter(
            binding.enterPriceInputText.text.toString().toDoubleOrNull() ?: 0.0
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
