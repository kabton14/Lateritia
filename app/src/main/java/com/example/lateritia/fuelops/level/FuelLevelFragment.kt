package com.example.lateritia.fuelops.level

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
import com.example.lateritia.databinding.FragmentFuelLevelBinding
import com.example.lateritia.fuelops.FuelOperationsViewModel

/**
 * A simple [Fragment] subclass.
 */
class FuelLevelFragment : Fragment() {
    private var _binding: FragmentFuelLevelBinding? = null
    private var fuelLevel: Int = 0

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val fuelOpsViewModel: FuelOperationsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.fuelLevel = fuelOpsViewModel.fuelLevel
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fuel_level, container,
            false)
        binding.operationsViewModel = fuelOpsViewModel
        binding.lifecycleOwner = this

        //Live data observers

        fuelOpsViewModel.navigateToTopupAmount.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(
                    FuelLevelFragmentDirections
                        .actionFuelLevelFragmentToTopUpFragment())
                fuelOpsViewModel.onTopUpAmountNavigated()
            }
        })

        fuelOpsViewModel.navigateToResult.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(
                    FuelLevelFragmentDirections
                        .actionFuelLevelFragmentToResultFragment())
                fuelOpsViewModel.onResultNavigated()
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fuelButtonNext.setOnClickListener {
            setViewModelFuelLevel()
            binding.operationsViewModel?.navigateToCorrectFragmentFromFuelLevel()
        }
        binding.increaseFuelLevel.setOnClickListener {
            increaseFuelLevel()
            binding.fuelLevelTextView.text = fuelLevel.toString()
            updateFuelGauge(fuelLevel)
        }
        binding.decreaseFuelLevel.setOnClickListener {
            decreaseFuelLevel()
            binding.fuelLevelTextView.text = fuelLevel.toString()
            updateFuelGauge(fuelLevel)
        }
        binding.fuelLevelTextView.text = fuelLevel.toString()
        updateFuelGauge(fuelLevel)
    }

    private fun setViewModelFuelLevel() {
        binding.operationsViewModel?.setFuelLevel(
            fuelLevel
        )
    }

    private fun increaseFuelLevel() {
        if (fuelLevel < 8) {
            fuelLevel++
        }

    }

    private fun decreaseFuelLevel() {
        if (fuelLevel > 0) {
            fuelLevel--
        }
    }

    private fun updateFuelGauge(value: Int) {
        val images = listOf(
            binding.fuelGauge0,
            binding.fuelGauge1,
            binding.fuelGauge2,
            binding.fuelGauge3,
            binding.fuelGauge4,
            binding.fuelGauge5,
            binding.fuelGauge6,
            binding.fuelGauge7,
            binding.fuelGauge8,
        )
        for (image in images.indices) {
            if (value == image) {
                images[image].visibility = View.VISIBLE
                continue
            }
            images[image].visibility = View.INVISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        setViewModelFuelLevel()
        _binding = null
    }
}