package com.ubayadev.expensetracker.model.seeder

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

class UserSeeder: RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        db.execSQL("INSERT INTO user (username, first_name, last_name, password) VALUES ('user1', 'user', 'user', '\$2y\$12\$66BTLPjl5jl7BrfjhUqgOuZMoQpGLEy.QnR/dk7eNjLUWA4TXeb5G')")
        db.execSQL("INSERT INTO user (username, first_name, last_name, password) VALUES ('user2', 'user', 'user', '\$2y\$12\$66BTLPjl5jl7BrfjhUqgOuZMoQpGLEy.QnR/dk7eNjLUWA4TXeb5G')")
        db.execSQL("INSERT INTO user (username, first_name, last_name, password) VALUES ('user3', 'user', 'user', '\$2y\$12\$66BTLPjl5jl7BrfjhUqgOuZMoQpGLEy.QnR/dk7eNjLUWA4TXeb5G')")
    }
}