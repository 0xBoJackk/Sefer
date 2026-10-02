package com.example.sefer.data

import kotlinx.coroutines.flow.Flow

interface DiaryRepository {
    fun getAllEntriesStream(): Flow<List<DiaryEntry>>
    suspend fun getEntry(id: Long): DiaryEntry?
    suspend fun insertEntry(entry: DiaryEntry)
    suspend fun deleteEntry(entry: DiaryEntry)
    suspend fun updateEntry(entry: DiaryEntry)
}

class OfflineDiaryRepository(private val diaryDao: DiaryDao) : DiaryRepository {
    override fun getAllEntriesStream(): Flow<List<DiaryEntry>> = diaryDao.getAllEntries()
    override suspend fun getEntry(id: Long): DiaryEntry? = diaryDao.getEntryById(id)
    override suspend fun insertEntry(entry: DiaryEntry) = diaryDao.insertEntry(entry)
    override suspend fun deleteEntry(entry: DiaryEntry) = diaryDao.deleteEntry(entry)
    override suspend fun updateEntry(entry: DiaryEntry) = diaryDao.updateEntry(entry)
}
