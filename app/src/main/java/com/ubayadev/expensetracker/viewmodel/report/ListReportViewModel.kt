package com.ubayadev.expensetracker.viewmodel.report

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ubayadev.expensetracker.model.Budget
import com.ubayadev.expensetracker.model.ExpenseWithBudgetName
import com.ubayadev.expensetracker.model.ReportData
import com.ubayadev.expensetracker.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ListReportViewModel (application: Application): AndroidViewModel(application), CoroutineScope {
    // List
    val reportsLD = MutableLiveData<List<ReportData>>()
    val totalBudgetLD = MutableLiveData<Int>()
    val totalSpentLD = MutableLiveData<Int>()

    val reportLoadingLD = MutableLiveData<Boolean>()
    val reportErrorLD = MutableLiveData<String>()

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun fetch(username: String) {
        reportLoadingLD.value = true
        reportErrorLD.value = ""

        launch {
            val userDb = buildDb(getApplication())
            val user_id = userDb.userDao().select(username)!!.id
            Log.d("USER_ID", user_id.toString())

            val budgets = userDb.budgetDao().getAllUserBudgets(user_id)
            Log.d("LIST BUDGET", budgets.toString())

            if (budgets.isEmpty()) {
                reportErrorLD.postValue("Anda belum memiliki budget.")
                reportsLD.postValue(emptyList())
                reportLoadingLD.postValue(false)
            } else {
                val reportList = mutableListOf<ReportData>()
                var totalExpense = 0
                var totalBudget = 0

                for (budget in budgets) {
                    val expenseBudget = userDb.expenseDao().getCurrentExpenses(budget.id) ?: 0
                    reportList.add(ReportData(budget = budget, totalExpense = expenseBudget))
                    totalExpense += expenseBudget
                    totalBudget += budget.nominal
                }

                reportsLD.postValue(reportList)
                totalBudgetLD.postValue(totalBudget)
                totalSpentLD.postValue(totalExpense)
                reportLoadingLD.postValue(false)
            }
        }

    }
}