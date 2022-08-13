package com.example.todo

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities=arrayOf(Activity::class),version=1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ActivityDao(): ActivityDao
}