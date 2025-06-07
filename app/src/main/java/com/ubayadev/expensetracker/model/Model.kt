package com.ubayadev.expensetracker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @ColumnInfo("username")
    var username: String,

    @ColumnInfo("first_name")
    var firstName: String,

    @ColumnInfo("last_name")
    var lastName: String,

    @ColumnInfo("password")
    var password: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

@Entity
data class Budget(
    @ColumnInfo("user_id")
    var userId: Int,

    @ColumnInfo("name")
    var name: String,

    @ColumnInfo("nominal")
    var nominal: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

@Entity
data class Expense(
    @ColumnInfo("budget_id")
    var budgetId: Int,

    @ColumnInfo("date")
    var date: Int,

    @ColumnInfo("nominal")
    var nominal: Int,

    @ColumnInfo("notes")
    var notes: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

data class ExpenseWithBudgetName(
    val id: Int,
    val budgetId: Int,
    val date: Int,
    val nominal: Int,
    val name: String
)