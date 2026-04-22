package com.example.lateritia.history

import androidx.lifecycle.*
import com.example.lateritia.database.FuelEntry
import com.example.lateritia.database.FuelEntryRepository
import kotlinx.coroutines.launch

class HistoryViewModel(private val fuelEntryRepository: FuelEntryRepository) : ViewModel() {

    private val _entries = MutableLiveData<List<FuelEntry>>()
    val entries: LiveData<List<FuelEntry>> get() = _entries

    init {
        loadEntries()
    }

    private fun loadEntries() {
        viewModelScope.launch {
            _entries.value = fuelEntryRepository.getRecentEntries()
        }
    }

    fun deleteEntry(entry: FuelEntry) {
        viewModelScope.launch {
            fuelEntryRepository.deleteEntry(entry)
            loadEntries()
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            fuelEntryRepository.deleteAll()
            loadEntries()
        }
    }
}

class HistoryViewModelFactory(private val fuelEntryRepository: FuelEntryRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoryViewModel(fuelEntryRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
