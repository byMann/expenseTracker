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
    var userId: String,
    var name: String,
    var nominal: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

@Entity
data class Expense(
    var budgetId: Int,
    var date: Int,
    var nominal: Int
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