package com.example.lateritia.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.lateritia.database.FuelEntryRepository
import com.example.lateritia.databinding.FragmentHistoryBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    @Inject lateinit var fuelEntryRepository: FuelEntryRepository

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        val viewModelFactory = HistoryViewModelFactory(fuelEntryRepository)
        val historyViewModel = ViewModelProvider(this, viewModelFactory)
            .get(HistoryViewModel::class.java)

        val adapter = HistoryAdapter()
        binding.historyList.adapter = adapter

        historyViewModel.entries.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.historyEmpty.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
