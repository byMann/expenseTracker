package com.ubayadev.expensetracker.viewmodel.budgeting

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ubayadev.expensetracker.model.Budget
import com.ubayadev.expensetracker.util.buildDb
import com.ubayadev.expensetracker.util.getCurrentUsername
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ListBudgetViewModel(application: Application): AndroidViewModel(application), CoroutineScope {
    // List
    val budgetsLD = MutableLiveData<List<Budget>>()
    val budgetLoadingLD = MutableLiveData<Boolean>()
    val budgetErrorLD = MutableLiveData<String>()

    // Create
    val newBudgetSuccessLD = MutableLiveData<Boolean>()

    // Update
    val budgetDetailLD = MutableLiveData<Budget>()
    val editBudgetSuccessLD = MutableLiveData<Boolean>()
    val editBudgetErrorLD = MutableLiveData<String>()

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
            } else {
                budgetErrorLD.postValue("")
            }
        }
    }

    fun create(budgetName: String, budgetNominal: Int) {
        launch {
            val userDb = buildDb(getApplication())
            val user_id = userDb.userDao().select(getCurrentUsername(getApplication()))!!.id
            val newBudget = Budget(user_id, budgetName, budgetNominal)
            userDb.budgetDao().insert(newBudget);

            newBudgetSuccessLD.postValue(true)
        }
    }

    fun getBudgetDetail(id: Int) {
        launch {
            val db = buildDb(getApplication())
            budgetDetailLD.postValue(db.budgetDao().getBudgetById(id))
        }
    }

    fun update(id: Int, name: String, nominal: Int) {
        launch {
            // Cek total expenses di budget ini
            val db = buildDb(getApplication())
            val expenses = db.expenseDao().getCurrentExpenses(id)

            // Kalo kurang, return error


            // Kalo nominal baru lebih dari expenses, update

        }
    }
}