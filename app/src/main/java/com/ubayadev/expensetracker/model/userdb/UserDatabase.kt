package com.ubayadev.expensetracker.model.userdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ubayadev.expensetracker.model.User
import com.ubayadev.expensetracker.util.DB_NAME

@Database(entities = [User::class], version = 1)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao

//    companion object {
//        @Volatile private var instance: UserDatabase? = null
//        private val LOCK = Any()
//
//        fun buildDatabase(context: Context) =
//            Room.databaseBuilder(
//                context.applicationContext,
//                UserDatabase::class.java,
//                USER_DB_NAME
//            )
////                .fallbackToDestructiveMigration()
//                .build()
//
//        operator fun invoke(context: Context): UserDatabase {
//            return instance ?: synchronized(LOCK) {
//                instance ?: buildDatabase(context).also { instance = it }
//            }
//        }
//    }

    companion object {
        @Volatile private var instance: UserDatabase? = null

        private val LOCK = Any()

        fun buildDatabase(context: Context) =
            Room
                .databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    DB_NAME
                )
//                .fallbackToDestructiveMigration()
                .build()


        operator fun invoke(context: Context) {
            if (instance == null) {
                synchronized(LOCK) {
                    // Singleton Implementation: restricts the instalitation of a class to one "single" instance
                    instance ?: buildDatabase(context)
                        .also {
                            // Synchronized: A thread that enters a synchronized method obtains a lock and no other thread can enter the method until the lock is released
                            instance = it
                        }
                }
            }
        }
    }
}