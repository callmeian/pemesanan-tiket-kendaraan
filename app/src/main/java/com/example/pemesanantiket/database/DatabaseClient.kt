package com.example.pemesanantiket.database

import android.content.Context
import androidx.room.Room

class DatabaseClient private constructor(context: Context) {
    val appDatabase: AppDatabase

    init {
        appDatabase = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "travel_db"
        ).build()
    }

    companion object {

        private var mInstance: DatabaseClient? = null

        @Synchronized
        @JvmStatic
        fun getInstance(context: Context): DatabaseClient? {
            if (mInstance == null) {
                mInstance = DatabaseClient(context)
            }
            return mInstance!!
        }
    }
}
