package com.example.laterita.fuelops.topup

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
import com.example.laterita.databinding.FragmentTopUpBinding
import com.example.laterita.fuelops.FuelOperationsViewModel

/**
 * A simple [Fragment] subclass.
 */
class TopUpFragment : Fragment() {
    private var _binding: FragmentTopUpBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val fuelOpsViewModel: FuelOperationsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_top_up, container,
            false)
        binding.operationsViewModel = fuelOpsViewModel
        binding.lifecycleOwner = this

        //Live data observers
        fuelOpsViewModel.navigateToResult.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(
                    TopUpFragmentDirections
                        .actionTopUpFragmentToResultFragment())
                fuelOpsViewModel.onResultNavigated()
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fuelButtonNext.setOnClickListener {
            setTopUpAmount()
            binding.operationsViewModel?.navigateToResult()
        }
    }

    private fun setTopUpAmount() {
        binding.operationsViewModel?.setTopUpAmount(
            binding.enterAmountSpendingText.text.toString().toDoubleOrNull() ?: 0.0
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
