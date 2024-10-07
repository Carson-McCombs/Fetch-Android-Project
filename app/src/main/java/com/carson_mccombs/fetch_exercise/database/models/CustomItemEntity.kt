package com.carson_mccombs.fetch_exercise.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.carson_mccombs.fetch_exercise.customItem.models.CustomItem


/*
    Purpose: to separate the database version of the model from the runtime version of the model. That way the database dependencies are propagated throughout the app.
 */

@Entity
data class CustomItemEntity (
    @PrimaryKey val id: Long,
    val listId: Long,
    val name: String?,
    val isValid: Boolean = !name.isNullOrBlank()
) {
    fun convertToModel(): CustomItem = CustomItem(
        id = id,
        listId = listId,
        name = name,
        )
    companion object {
        fun convertFromModel(model: CustomItem): CustomItemEntity = CustomItemEntity (
            id = model.id,
            listId = model.listId,
            name = model.name
        )
    }
}