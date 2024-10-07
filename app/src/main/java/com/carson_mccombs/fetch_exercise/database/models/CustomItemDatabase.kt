package com.carson_mccombs.fetch_exercise.database.models

import androidx.room.Database
import androidx.room.RoomDatabase

/*
    Purpose: To store Data Access Objects (DAO) related to the Custom Item Class / Entity-Class
 */

@Database(entities = [CustomItemEntity::class], version = 1, exportSchema = false)
abstract class CustomItemDatabase : RoomDatabase() {
    abstract fun customItemDao(): CustomItemDao
}