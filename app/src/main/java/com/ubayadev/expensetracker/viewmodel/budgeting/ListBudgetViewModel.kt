package com.ubayadev.expensetracker.viewmodel.budgeting

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ubayadev.expensetracker.model.Budget
import com.ubayadev.expensetracker.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ListBudgetViewModel(application: Application): AndroidViewModel(application), CoroutineScope {
    val budgetsLD = MutableLiveData<List<Budget>>()
    val budgetLoadingLD = MutableLiveData<Boolean>()
    val budgetErrorLD = MutableLiveData<String>()
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun fetch(username: String) {
        budgetLoadingLD.value = true

        launch {
            val userDb = buildDb(getApplication())
            val user_id = userDb.userDao().select(username)!!.id
            Log.d("USER_ID", user_id.toString())

//            val budgetDb = buildBudgetDB(getApplication())
            val budgets: List<Budget> = userDb.budgetDao().getAllUserBudgets(user_id)
            Log.d("LIST BUDGET", budgets.toString())

            budgetsLD.postValue(budgets)
            budgetLoadingLD.postValue(false)

            if (budgets.isEmpty()) {
                Log.d("LIST BUDGET EMPTY", "EMPTY")
                budgetErrorLD.postValue("You don't have any budgets yet! Let's create one!")
            }
        }
    }
}