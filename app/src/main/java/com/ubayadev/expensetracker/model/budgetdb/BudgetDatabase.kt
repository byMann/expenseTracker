package com.ubayadev.expensetracker.model.budgetdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ubayadev.expensetracker.model.Budget
import com.ubayadev.expensetracker.model.userdb.UserDao
import com.ubayadev.expensetracker.util.DB_NAME

@Database(entities = [Budget::class], version = 1)
abstract class BudgetDatabase: RoomDatabase() {
    abstract fun budgetDao(): BudgetDao

    companion object {
        @Volatile private var instance: BudgetDatabase? = null

        private val LOCK = Any()

        fun buildDatabase(context: Context) =
            Room
                .databaseBuilder(
                    context.applicationContext,
                    BudgetDatabase::class.java,
                    DB_NAME
                )
                .fallbackToDestructiveMigration()
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