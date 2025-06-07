package com.ubayadev.expensetracker.model.seeder

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

class BudgetSeeder: RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        // USER 1
        db.execSQL("INSERT INTO budget (user_id, name, nominal) VALUES (1, 'Food', 2000000)")
        db.execSQL("INSERT INTO budget (user_id, name, nominal) VALUES (1, 'Transportation', 1000000)")
        db.execSQL("INSERT INTO budget (user_id, name, nominal) VALUES (1, 'Healing', 5000000)")

        // USER 2
        db.execSQL("INSERT INTO budget (user_id, name, nominal) VALUES (2, 'School', 2400000)")
        db.execSQL("INSERT INTO budget (user_id, name, nominal) VALUES (2, 'Healing', 1250000)")

        // USER 3
        db.execSQL("INSERT INTO budget (user_id, name, nominal) VALUES (2, 'Rent', 7000000)")
    }
}