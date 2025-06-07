package com.ubayadev.expensetracker.model.seeder

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

class ExpenseSeeder: RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        // USER 1
        db.execSQL("INSERT INTO expense (budget_id, date, nominal, notes) VALUES (1, 1749274431, 50000, 'McDonald')")
        db.execSQL("INSERT INTO expense (budget_id, date, nominal, notes) VALUES (1, 1749274431, 20000, 'KFC')")
        db.execSQL("INSERT INTO expense (budget_id, date, nominal, notes) VALUES (2, 1749274431, 34000, 'Grab')")
        db.execSQL("INSERT INTO expense (budget_id, date, nominal, notes) VALUES (3, 1749274431, 42000, 'Jalan - jalan')")

        // USER 2
        db.execSQL("INSERT INTO expense (budget_id, date, nominal, notes) VALUES (4, 1749274431, 1200000, 'SPP Bulanan')")
        db.execSQL("INSERT INTO expense (budget_id, date, nominal, notes) VALUES (4, 1749274431, 120000, 'Buku Paket')")
        db.execSQL("INSERT INTO expense (budget_id, date, nominal, notes) VALUES (5, 1749274431, 500000, 'Jalan ke luar kota')")

        // USER 3
        db.execSQL("INSERT INTO expense (budget_id, date, nominal, notes) VALUES (6, 1749274431, 1200000, 'Kamar mandi rusak')")
        db.execSQL("INSERT INTO expense (budget_id, date, nominal, notes) VALUES (6, 1749274431, 250000, 'Listrik konslet')")
    }
}