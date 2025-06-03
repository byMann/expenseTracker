package com.ubayadev.expensetracker.model.userdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ubayadev.expensetracker.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("SELECT * FROM users_db WHERE id = :id LIMIT 1")
    fun select(id: Int): User?

    @Update
    fun update(user: User)
}