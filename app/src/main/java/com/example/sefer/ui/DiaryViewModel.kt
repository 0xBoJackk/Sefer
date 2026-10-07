package com.example.sefer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.sefer.DiaryApplication
import com.example.sefer.data.DiaryEntry
import com.example.sefer.data.DiaryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

class DiaryViewModel(private val repository: DiaryRepository) : ViewModel() {

    private val _selectedDate = MutableStateFlow<Long?>(null)
    val selectedDate: StateFlow<Long?> = _selectedDate

    val uiState: StateFlow<DiaryUiState> = combine(
        repository.getAllEntriesStream(),
        _selectedDate
    ) { entries, date ->
        val filteredEntries = if (date == null) {
            entries
        } else {
            entries.filter { isSameDay(it.timestamp, date) }
        }
        DiaryUiState(filteredEntries)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = DiaryUiState()
    )

    fun setSelectedDate(date: Long?) {
        _selectedDate.value = date
    }

    fun addEntry(content: String) {
        viewModelScope.launch {
            repository.insertEntry(DiaryEntry(title = "", content = content))
        }
    }

    fun updateEntry(entry: DiaryEntry) {
        viewModelScope.launch {
            repository.updateEntry(entry)
        }
    }

    fun deleteEntry(entry: DiaryEntry) {
        viewModelScope.launch {
            repository.deleteEntry(entry)
        }
    }

    private fun isSameDay(timestamp1: Long, timestamp2: Long): Boolean {
        val cal1 = Calendar.getInstance().apply { timeInMillis = timestamp1 }
        val cal2 = Calendar.getInstance().apply { timeInMillis = timestamp2 }
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DiaryApplication)
                DiaryViewModel(application.container.diaryRepository)
            }
        }
    }
}

data class DiaryUiState(
    val entries: List<DiaryEntry> = emptyList()
)
