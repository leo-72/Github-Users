package com.bangkit.githubusers2.favuser

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavUser::class],
    version = 1
)
abstract class DatabaseUser : RoomDatabase() {
    companion object {
        var Instance: DatabaseUser? = null

        fun getDatabaseUser(context: Context): DatabaseUser? {
            if (Instance == null)
                synchronized(DatabaseUser::class) {
                    Instance = Room.databaseBuilder(
                        context.applicationContext,
                        DatabaseUser::class.java,
                        "database_user"
                    ).build()
                }
            return Instance
        }
    }

    abstract fun favUserDao(): FavUserDao
}