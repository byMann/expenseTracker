package com.ubayadev.expensetracker.util

import android.content.Context
import android.content.SharedPreferences
import com.ubayadev.expensetracker.model.ExpenseTrackerDatabase
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

val DB_NAME = "moneytrackerdb"
private val SHARED_PREFERENCE_KEY = "SETTING"
private val USER_PREFERENCE_KEY = "USER"
private val ID_PREFERENCE_KEY = "USER_ID"

fun buildDb(context: Context): ExpenseTrackerDatabase {
    val db = ExpenseTrackerDatabase.buildDatabase(context)
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
//            putInt(ID_PREFERENCE_KEY, id)
            apply()
        }
}

fun logout(context: Context) {
    buildSharedPreference(context)
        .edit()
        .remove(USER_PREFERENCE_KEY)
        .apply()
}

fun getCurrentUsername(context: Context): String {
    return buildSharedPreference(context)
        .getString(USER_PREFERENCE_KEY, "")
        .toString()
}

fun getCurrentId(context: Context): Int {
    return buildSharedPreference(context)
        .getInt(ID_PREFERENCE_KEY, -1)
}

fun formatToRupiah(amount: Int): String {
    val formatter = NumberFormat.getNumberInstance(Locale("id", "ID"))
    return formatter.format(amount)
}

fun convertUnixToFormattedDate(unixTime: Long): String {
    val date = Date(unixTime * 1000) // Jika dalam detik
    val format = SimpleDateFormat("dd MMMM yyyy hh.mm a", Locale("id"))
    return format.format(date)
}

fun convertToUnix(dateTimeString: String): Long {
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    format.timeZone = TimeZone.getTimeZone("Asia/Jakarta") // or your desired zone
    val date = format.parse(dateTimeString)
    return date?.time?.div(1000) ?: 0L // Convert from milliseconds to seconds
}