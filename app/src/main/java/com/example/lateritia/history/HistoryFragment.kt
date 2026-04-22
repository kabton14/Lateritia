package com.example.lateritia.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.lateritia.R
import com.example.lateritia.database.FuelEntryRepository
import com.example.lateritia.databinding.FragmentHistoryBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    @Inject lateinit var fuelEntryRepository: FuelEntryRepository

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        val viewModelFactory = HistoryViewModelFactory(fuelEntryRepository)
        historyViewModel = ViewModelProvider(this, viewModelFactory)
            .get(HistoryViewModel::class.java)

        val adapter = HistoryAdapter { entry ->
            MaterialAlertDialogBuilder(requireContext())
                .setMessage(R.string.delete_entry_confirm)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.delete) { _, _ ->
                    historyViewModel.deleteEntry(entry)
                }
                .show()
        }
        binding.historyList.adapter = adapter

        historyViewModel.entries.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.historyEmpty.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_history, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.clear_all_history) {
            MaterialAlertDialogBuilder(requireContext())
                .setMessage(R.string.delete_all_confirm)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.delete) { _, _ ->
                    historyViewModel.deleteAll()
                }
                .show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
