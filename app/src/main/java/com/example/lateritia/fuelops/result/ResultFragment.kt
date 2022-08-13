package com.example.lateritia.fuelops.result

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
import com.example.lateritia.databinding.FragmentResultBinding
import com.example.lateritia.fuelops.FuelOperationsViewModel

/**
 * A simple [Fragment] subclass.
 */
class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val fuelOpsViewModel: FuelOperationsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_result, container,
            false)
        binding.operationsViewModel = fuelOpsViewModel
        binding.lifecycleOwner = this

        //Live data observers
        fuelOpsViewModel.navigateToHome.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(
                    ResultFragmentDirections
                        .actionResultFragmentToHomeFragment())
                fuelOpsViewModel.onHomeNavigated()
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}