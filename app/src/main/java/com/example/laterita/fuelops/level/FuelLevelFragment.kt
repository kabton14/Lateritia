package com.example.laterita.fuelops.level

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.laterita.R
import com.example.laterita.databinding.FragmentFuelLevelBinding
import com.example.laterita.fuelops.FuelOperationsViewModel

/**
 * A simple [Fragment] subclass.
 */
class FuelLevelFragment : Fragment() {
    private var _binding: FragmentFuelLevelBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val fuelOpsViewModel: FuelOperationsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fuel_level, container,
            false)
        binding.operationsViewModel = fuelOpsViewModel
        binding.lifecycleOwner = this

        //Live data observers

        fuelOpsViewModel.navigateToTopupAmount.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(
                    FuelLevelFragmentDirections
                        .actionFuelLevelFragmentToTopUpFragment())
                fuelOpsViewModel.onTopUpAmountNavigated()
            }
        })

        fuelOpsViewModel.navigateToResult.observe(viewLifecycleOwner, Observer {
            if (null != it) {
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
            setFuelLevel()
            binding.operationsViewModel.navigateToResult()
        }
    }

    private fun setFuelLevel() {
        binding.operationsViewModel?.setFueLevel(
            binding.enterFuelInputText.text.toString().toIntOrNull() ?: 0
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}