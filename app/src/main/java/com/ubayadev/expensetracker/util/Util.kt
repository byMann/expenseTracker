package com.ubayadev.expensetracker.util

import android.content.Context
import android.content.SharedPreferences
import com.ubayadev.expensetracker.model.budgetdb.BudgetDatabase
import com.ubayadev.expensetracker.model.expensedb.ExpenseDatabase
import com.ubayadev.expensetracker.model.userdb.UserDatabase

val DB_NAME = "moneytrackerdb"
private val SHARED_PREFERENCE_KEY = "SETTING"
private val USER_PREFERENCE_KEY = "USER"

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

private fun buildSharedPreference(context: Context): SharedPreferences {
    val sharedPreference = context
        .getSharedPreferences(SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE)
    return sharedPreference
}

fun login(context: Context, username: String) {
    buildSharedPreference(context)
        .edit()
        .apply {
            putString(USER_PREFERENCE_KEY, username)
            apply()
        }
}

fun logout(context: Context) {
    buildSharedPreference(context)
        .edit()
        .remove(USER_PREFERENCE_KEY)
        .apply()
}

fun getCurrentUser(context: Context): String {
    return buildSharedPreference(context)
        .getString(USER_PREFERENCE_KEY, "")
        .toString()
}