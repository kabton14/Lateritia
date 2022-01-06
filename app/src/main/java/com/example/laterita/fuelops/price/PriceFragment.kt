package com.example.laterita.fuelops.price

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
import com.example.laterita.databinding.FragmentPriceBinding
import com.example.laterita.fuelops.OperationsViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 */
class PriceFragment : Fragment() {
    private var _binding: FragmentPriceBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val fuelOpsViewModel: OperationsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_price, container,
            false)
        binding.operationsViewModel = fuelOpsViewModel
        binding.lifecycleOwner = this

        //Live data observers
        fuelOpsViewModel.navigateToFuelLevel.observe(viewLifecycleOwner, Observer {
            if (null != it && fuelOpsViewModel.pricePerLiter.value!! > 0.0) {
                this.findNavController().navigate(
                    PriceFragmentDirections
                        .actionPriceFragmentToFuelLevelFragment())
                fuelOpsViewModel.onFuelLevelNavigated()
            } else {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.incorrent_ppl_message),
                    Snackbar.LENGTH_SHORT
                ).show()
            }

        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pplSubmitButton.setOnClickListener {
            setPrice()
            binding.operationsViewModel?.onFuelLevelClicked()
        }
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
