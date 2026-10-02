package com.example.sefer

import android.app.Application
import com.example.sefer.data.DiaryDatabase
import com.example.sefer.data.DiaryRepository
import com.example.sefer.data.OfflineDiaryRepository

class DiaryApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}

interface AppContainer {
    val diaryRepository: DiaryRepository
}

class AppDataContainer(private val context: android.content.Context) : AppContainer {
    override val diaryRepository: DiaryRepository by lazy {
        OfflineDiaryRepository(DiaryDatabase.getDatabase(context).diaryDao())
    }
}
