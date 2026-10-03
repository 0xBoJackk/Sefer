package com.example.sefer.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sefer.data.DiaryEntry
import com.example.sefer.ui.components.DiaryItem
import com.example.sefer.ui.components.EntryDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryScreen(
    viewModel: DiaryViewModel = viewModel(factory = DiaryViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var entryToEdit by remember { mutableStateOf<DiaryEntry?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("My Diary") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Create, contentDescription = "Add Entry")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.entries) { entry ->
                DiaryItem(
                    entry = entry,
                    onDelete = { viewModel.deleteEntry(entry) },
                    onClick = { entryToEdit = entry }
                )
            }
        }
    }

    if (showAddDialog) {
        EntryDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { title, content ->
                viewModel.addEntry(title, content)
                showAddDialog = false
            }
        )
    }

    entryToEdit?.let { entry ->
        EntryDialog(
            initialTitle = entry.title,
            initialContent = entry.content,
            onDismiss = { entryToEdit = null },
            onConfirm = { title, content ->
                viewModel.updateEntry(entry.copy(title = title, content = content))
                entryToEdit = null
            }
        )
    }
}

