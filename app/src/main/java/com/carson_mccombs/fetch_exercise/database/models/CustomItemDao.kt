package com.carson_mccombs.fetch_exercise.database.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

/*
    Purpose: To define what database queries / calls we intend to use
 */

@Dao
interface CustomItemDao {

    @Query("SELECT (SELECT COUNT (*) FROM customitementity) == 0")
    fun isEmpty(): Flow<Boolean>

    @Transaction
    @Query("SELECT * FROM customitementity WHERE isValid = 1 ORDER BY listId ASC, name ASC")
    fun getAllValidItems(): Flow<List<CustomItemEntity>>

    @Query("DELETE FROM customitementity")
    suspend fun clear()

    @Insert
    suspend fun insertItems(items: List<CustomItemEntity>): List<Long>
}