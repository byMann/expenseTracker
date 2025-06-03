package com.ubayadev.expensetracker.util

import android.content.Context
import com.ubayadev.expensetracker.model.budgetdb.BudgetDatabase
import com.ubayadev.expensetracker.model.expensedb.ExpenseDatabase
import com.ubayadev.expensetracker.model.userdb.UserDatabase

val DB_NAME = "moneytrackerdb"

fun buildUserDB(context: Context): UserDatabase {
    val db = UserDatabase.buildDatabase(context)
    return db
}

fun buildBudgetDB(context: Context): BudgetDatabase {
    val db = BudgetDatabase.buildDatabase(context)
    return db
}

fun buildExpenseDB(context: Context): ExpenseDatabase {
    val db = ExpenseDatabase.buildDatabase(context)
    return db
}