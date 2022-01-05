package com.example.laterita.ops

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.laterita.R
import com.example.laterita.databinding.FragmentOpBinding

class OperationsFragment : Fragment() {
    private var _binding: FragmentOpBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_op, container, false)

        val operationsViewModel = ViewModelProvider(this, defaultViewModelProviderFactory)[OperationsViewModel::class.java]

        binding.operationsViewModel = operationsViewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
