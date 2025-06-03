package com.ubayadev.expensetracker.viewmodel

import android.app.Application
import android.content.Intent
import android.service.autofill.UserData
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import at.favre.lib.crypto.bcrypt.BCrypt
import com.ubayadev.expensetracker.model.User
import com.ubayadev.expensetracker.model.userdb.UserDatabase
import com.ubayadev.expensetracker.util.buildUserDB
import com.ubayadev.expensetracker.view.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class UserViewModel(application: Application): AndroidViewModel(application), CoroutineScope {
    val errorMessageLD = MutableLiveData<String>()
    val successLD = MutableLiveData<Boolean>()
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun init() {
        launch {
            val db = buildUserDB(getApplication())
        }
    }

    fun signUp(username: String, firstName: String, lastName: String, password: String) {
        launch {
            val db = buildUserDB(getApplication())
            val hashedPassword = BCrypt
                .withDefaults()
                .hashToString(12, password.toCharArray())
            val user = User(
                username = username,
                firstName = firstName,
                lastName = lastName,
                password = hashedPassword
            )
//            db.userDao().insert(user)
            successLD.postValue(true)
        }
    }

    fun signIn(username: String, password: String) {
        launch {
            val db = buildUserDB(getApplication())
            // Hash Password
//            val user = db.userDao().select(username)
            val user = null

            if (user == null) {
                errorMessageLD.postValue("Username atau Password salah!")
                successLD.postValue(false)
            } else {
                // Check Password
//                val verifiedPassword = BCrypt
//                    .verifyer()
//                    .verify(password.toCharArray(), user.password)
//
//                if (verifiedPassword.verified) {
//                    errorMessageLD.postValue("")
//                    successLD.postValue(true)
//                } else {
//                    errorMessageLD.postValue("Username atau Password salah!")
//                }
            }
        }
    }

}