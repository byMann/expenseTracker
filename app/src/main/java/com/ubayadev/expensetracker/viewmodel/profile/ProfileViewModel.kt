package com.ubayadev.expensetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import at.favre.lib.crypto.bcrypt.BCrypt
import com.ubayadev.expensetracker.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ProfileViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    // two-way data binding dengan EditText di layout
    val oldPasswordLD = MutableLiveData<String>()
    val newPasswordLD = MutableLiveData<String>()
    val repeatPasswordLD = MutableLiveData<String>()

    // pesan error/sucess
    val toastMessageLD = MutableLiveData<String>()

    val navigateToSignInLD = MutableLiveData<Boolean>()

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun changePassword(username: String) {
        val oldPass = oldPasswordLD.value
        val newPass = newPasswordLD.value
        val repeatPass = repeatPasswordLD.value

        if (oldPass.isNullOrEmpty() || newPass.isNullOrEmpty() || repeatPass.isNullOrEmpty()) {
            toastMessageLD.postValue("Semua field wajib diisi!")
            return
        }

        if (newPass != repeatPass) {
            toastMessageLD.postValue("Password baru dan konfirmasi tidak cocok!")
            return
        }

        launch {
            val db = buildDb(getApplication())
            val user = db.userDao().select(username)

            if (user == null) {
                toastMessageLD.postValue("user tidak ditemukan.")
                return@launch
            }

            val verificationResult = BCrypt.verifyer().verify(oldPass.toCharArray(), user.password)

            if (verificationResult.verified) {
                val newHashedPassword = BCrypt.withDefaults().hashToString(12, newPass.toCharArray())
                db.userDao().updatePassword(username, newHashedPassword)
                toastMessageLD.postValue("Password berhasil diubah!")

                oldPasswordLD.postValue("")
                newPasswordLD.postValue("")
                repeatPasswordLD.postValue("")

            } else {
                toastMessageLD.postValue("Password lama salah!")
            }
        }
    }

    fun signOut() {
        navigateToSignInLD.postValue(true)
    }

    fun onSignOutNavigated() {
        navigateToSignInLD.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
