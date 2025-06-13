package com.ubayadev.expensetracker.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ubayadev.expensetracker.model.Expense
import com.ubayadev.expensetracker.model.ExpenseWithBudgetName

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(expense: Expense)

    @Query("""
        SELECT exp.id, exp.budget_id as budgetId, exp.date, exp.nominal, bud.name as name
        FROM expense AS exp
        INNER JOIN budget AS bud ON exp.budget_id = bud.id
        WHERE bud.user_id = :userId
        ORDER BY exp.date DESC
    """)
    fun getAllExpenses(userId: Int): List<ExpenseWithBudgetName>

    @Query("SELECT SUM(nominal) FROM expense WHERE budget_id = :budgetId")
    fun getCurrentExpenses(budgetId: Int): Int

    @Query("""
        SELECT exp.id, exp.budget_id as budgetId, exp.date, exp.nominal, bud.name
        FROM expense AS exp
        INNER JOIN budget AS bud ON exp.budget_id = bud.id
        WHERE exp.id = :expId
    """)
    fun getExpensesbyId(expId: Int): ExpenseWithBudgetName
}