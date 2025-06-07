package com.ubayadev.expensetracker.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ubayadev.expensetracker.model.dao.BudgetDao
import com.ubayadev.expensetracker.model.dao.ExpenseDao
import com.ubayadev.expensetracker.model.dao.UserDao
import com.ubayadev.expensetracker.model.seeder.BudgetSeeder
import com.ubayadev.expensetracker.model.seeder.ExpenseSeeder
import com.ubayadev.expensetracker.model.seeder.UserSeeder
import com.ubayadev.expensetracker.util.DB_NAME

@Database(entities = [User::class, Budget::class, Expense::class], version = 1)
abstract class ExpenseTrackerDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun budgetDao(): BudgetDao
    abstract fun expenseDao(): ExpenseDao

    companion object {
        @Volatile private var instance: ExpenseTrackerDatabase? = null

        private val LOCK = Any()

        fun buildDatabase(context: Context) =
            Room
                .databaseBuilder(
                    context.applicationContext,
                    ExpenseTrackerDatabase::class.java,
                    DB_NAME
                )
                .fallbackToDestructiveMigration()
                .addCallback(UserSeeder())
                .addCallback(BudgetSeeder())
                .addCallback(ExpenseSeeder())
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