package com.ubayadev.expensetracker.viewmodel.expenses

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ubayadev.expensetracker.model.Budget
import com.ubayadev.expensetracker.model.Expense
import com.ubayadev.expensetracker.util.buildDb
import com.ubayadev.expensetracker.util.getCurrentUsername
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.Date
import kotlin.coroutines.CoroutineContext

class ListExpenseViewModel (application: Application):AndroidViewModel(application), CoroutineScope{
    val expenseLD = MutableLiveData<List<Expense>>()
    val expenseLoadErrorLD = MutableLiveData<Boolean>()
    val expenseloadingLD = MutableLiveData<Boolean>()

    val newExpenseSuccessLD = MutableLiveData<Boolean>()

    val expenseDetailLD = MutableLiveData<Expense>()

    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun refresh(username: String) {
        expenseloadingLD.value = true
        expenseLoadErrorLD.value = false

        launch {
            val db = buildDb(getApplication())
            val user_id = db.userDao().select(username)!!.id
            Log.d("USER_ID", user_id.toString())

//            expenseLD.postValue(db.expenseDao().getAllExpenses(user_id))
            expenseloadingLD.postValue(false)
        }
    }
    fun create(expDate: Int, expNominal: Int,expNote: String, budgetId:Int) {
        launch {
            val userDb = buildDb(getApplication())
            val newExp = Expense(budgetId, expDate, expNominal, expNote)
            userDb.expenseDao().insert(newExp);


            newExpenseSuccessLD.postValue(true)
        }
    }

    fun getExpenseDetail(expId:Int){
        launch {
            val db  = buildDb(getApplication())
//            expenseDetailLD.postValue(db.expenseDao().getExpensesbyId(expId))
        }
    }

}