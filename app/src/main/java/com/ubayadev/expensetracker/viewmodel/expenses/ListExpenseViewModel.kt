package com.ubayadev.expensetracker.viewmodel.expenses

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ubayadev.expensetracker.model.Budget
import com.ubayadev.expensetracker.model.Expense
import com.ubayadev.expensetracker.model.ExpenseWithBudgetName
import com.ubayadev.expensetracker.util.buildDb
import com.ubayadev.expensetracker.util.getCurrentUsername
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.Date
import kotlin.coroutines.CoroutineContext

class ListExpenseViewModel (application: Application):AndroidViewModel(application), CoroutineScope{
    val expenseLD = MutableLiveData<List<ExpenseWithBudgetName>>()
    val expenseLoadErrorLD = MutableLiveData<Boolean>()
    val expenseLoadingLD = MutableLiveData<Boolean>()

    val currentExpenses = MutableLiveData<Int>()
    val newExpenseSuccessLD = MutableLiveData<Boolean>()

    val expenseDetailLD = MutableLiveData<ExpenseWithBudgetName>()

    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun refresh(username: String) {
        expenseLoadingLD.value = true
        expenseLoadErrorLD.value = false

        launch {
            val db = buildDb(getApplication())
            val user_id = db.userDao().select(username)!!.id
            Log.d("USER_ID", user_id.toString())

            expenseLD.postValue(db.expenseDao().getAllExpenses(user_id))
            expenseLoadingLD.postValue(false)
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

    fun getCurrentExpense(budgetId: Int){
        launch {
            val db = buildDb(getApplication())
            currentExpenses.postValue(db.expenseDao().getCurrentExpenses(budgetId))
        }
    }

    fun getExpenseDetail(exp : ExpenseWithBudgetName){
        launch {
            expenseDetailLD.postValue(exp)
        }
    }

}