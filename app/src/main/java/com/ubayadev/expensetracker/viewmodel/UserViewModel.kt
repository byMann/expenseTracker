package com.ubayadev.expensetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import at.favre.lib.crypto.bcrypt.BCrypt
import com.ubayadev.expensetracker.model.User
import com.ubayadev.expensetracker.util.buildDb
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

    fun refresh() {
        launch {
            successLD.postValue(false)
        }
    }

    fun signUp(username: String, firstName: String, lastName: String, password: String) {
        launch {
            val db = buildDb(getApplication())
            // Check user
            var user = db.userDao().select(username)

            if (user != null) {
                errorMessageLD.postValue("Username is already taken!")
                successLD.postValue(false)
            } else {
                val hashedPassword = BCrypt
                    .withDefaults()
                    .hashToString(12, password.toCharArray())
                user = User(
                    username = username,
                    firstName = firstName,
                    lastName = lastName,
                    password = hashedPassword
                )
                db.userDao().insert(user)
                successLD.postValue(true)
            }
        }
    }

    fun signIn(username: String, password: String) {
        launch {
            val db = buildDb(getApplication())

            // Hash Password
            val user = db.userDao().select(username)

            if (user == null) {
                errorMessageLD.postValue("Username or Password is Incorrect!")
                successLD.postValue(false)
            } else {
                // Check Password
                val verifiedPassword = BCrypt
                    .verifyer()
                    .verify(password.toCharArray(), user.password)

                if (verifiedPassword.verified) {
                    errorMessageLD.postValue("")
                    successLD.postValue(true)
                } else {
                    errorMessageLD.postValue("Username or Password is Incorrect!")
                }
            }
        }
    }

}