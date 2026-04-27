package com.example.lateritia.history

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lateritia.R
import com.example.lateritia.database.FuelEntryRepository
import com.example.lateritia.databinding.DialogEditLocationBinding
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

        val adapter = HistoryAdapter(
            onLongClick = { entry ->
                MaterialAlertDialogBuilder(requireContext())
                    .setMessage(R.string.delete_entry_confirm)
                    .setNegativeButton(R.string.cancel, null)
                    .setPositiveButton(R.string.delete) { _, _ ->
                        historyViewModel.deleteEntry(entry)
                    }
                    .show()
            },
            onLocationEdit = { entry ->
                val dialogBinding = DialogEditLocationBinding.inflate(layoutInflater)
                // DigitsKeyListener is what actually filters characters on paste — the XML
                // inputType just tells Android which default key listener to install. By replacing
                // it with a custom instance that includes ',' we allow commas through to the
                // TextWatcher for coordinate splitting.
                // keyListener must be set LAST: setInputType() installs a new default
                // DigitsKeyListener that would overwrite ours. setRawInputType() updates the IME
                // keyboard hint without touching the key listener.
                val numericInputType = InputType.TYPE_CLASS_NUMBER or
                        InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_NUMBER_FLAG_SIGNED
                val coordKeyListener = DigitsKeyListener.getInstance("-0123456789.,")
                dialogBinding.editLat.keyListener = coordKeyListener
                dialogBinding.editLng.keyListener = coordKeyListener
                dialogBinding.editLat.setRawInputType(numericInputType)
                dialogBinding.editLng.setRawInputType(numericInputType)
                dialogBinding.editLat.setText(entry.lat?.toString() ?: "")
                dialogBinding.editLng.setText(entry.lng?.toString() ?: "")

                var isSplitting = false
                val splitWatcher = object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                    override fun afterTextChanged(s: Editable?) {
                        if (isSplitting) return
                        val text = s?.toString() ?: return
                        val commaIndex = text.indexOf(',')
                        if (commaIndex < 0) return
                        val latPart = text.substring(0, commaIndex).trim()
                        val lngPart = text.substring(commaIndex + 1).trim()
                        isSplitting = true
                        dialogBinding.editLat.setText(latPart)
                        dialogBinding.editLng.setText(lngPart)
                        dialogBinding.editLng.setSelection(lngPart.length)
                        dialogBinding.editLng.requestFocus()
                        isSplitting = false
                    }
                }
                dialogBinding.editLat.addTextChangedListener(splitWatcher)
                dialogBinding.editLng.addTextChangedListener(splitWatcher)

                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(R.string.edit_location)
                    .setView(dialogBinding.root)
                    .setNegativeButton(R.string.cancel, null)
                    .setPositiveButton(R.string.save) { _, _ ->
                        val lat = dialogBinding.editLat.text?.toString()?.toDoubleOrNull()
                        val lng = dialogBinding.editLng.text?.toString()?.toDoubleOrNull()
                        if (lat != null && lng != null &&
                            lat in -90.0..90.0 && lng in -180.0..180.0) {
                            historyViewModel.updateLocation(entry, lat, lng)
                        } else {
                            dialogBinding.layoutLat.error = getString(R.string.invalid_coordinates)
                        }
                    }
                    .show()
            }
        )
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
