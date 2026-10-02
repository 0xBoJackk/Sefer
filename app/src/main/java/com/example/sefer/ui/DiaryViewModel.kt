package com.example.sefer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.sefer.DiaryApplication
import com.example.sefer.data.DiaryEntry
import com.example.sefer.data.DiaryRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DiaryViewModel(private val repository: DiaryRepository) : ViewModel() {

    val uiState: StateFlow<DiaryUiState> = repository.getAllEntriesStream()
        .map { DiaryUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = DiaryUiState()
        )

    fun addEntry(title: String, content: String) {
        viewModelScope.launch {
            repository.insertEntry(DiaryEntry(title = title, content = content))
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
