package com.jackie.getdatafrommockapianddatabase.logic.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jackie.getdatafrommockapianddatabase.AndroidVersionApplication

@Database(entities = [AndroidVersionEntity::class], version = 1, exportSchema = false)
abstract class AndroidVersionDatabase : RoomDatabase() {

    abstract fun androidVersionDao(): AndroidVersionDao

    companion object {
        private var instance: AndroidVersionDatabase? = null

        fun getInstance(): AndroidVersionDatabase {
            if (instance == null) {
                synchronized(AndroidVersionDatabase) {
                    if (instance == null) {
                        instance = buildRoomDb()
                    }
                }
            }
            return instance!!
        }

        private fun buildRoomDb() =
            Room.databaseBuilder(
                AndroidVersionApplication.context,
                AndroidVersionDatabase::class.java,
                "android_version_db"
            ).build()
    }
}