package com.example.sefer.data

import kotlinx.coroutines.flow.Flow

interface DiaryRepository {
    fun getAllEntriesStream(): Flow<List<DiaryEntry>>
    suspend fun getEntry(id: Long): DiaryEntry?
    suspend fun insertEntry(entry: DiaryEntry)
    suspend fun deleteEntry(entry: DiaryEntry)
    suspend fun updateEntry(entry: DiaryEntry)
}

