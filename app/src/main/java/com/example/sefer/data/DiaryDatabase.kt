package com.example.sefer.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DiaryEntry::class], version = 1, exportSchema = false)
abstract class DiaryDatabase : RoomDatabase() {
    abstract fun diaryDao(): DiaryDao

    companion object {
        @Volatile
        private var Instance: DiaryDatabase? = null

        fun getDatabase(context: Context): DiaryDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, DiaryDatabase::class.java, "diary_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
