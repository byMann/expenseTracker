package com.ubayadev.expensetracker.model.budgetdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ubayadev.expensetracker.model.Budget

@Dao
interface BudgetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(budget: Budget)

    @Query("SELECT * FROM budget WHERE user_id = :id")
    fun getAllUserBudgets(id: Int): List<Budget>

    @Update
    fun update(budget: Budget)
}