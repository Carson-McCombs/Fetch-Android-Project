package com.carson_mccombs.fetch_exercise.database.models

import androidx.annotation.WorkerThread
import androidx.compose.ui.util.fastMap
import com.carson_mccombs.fetch_exercise.customItem.models.CustomItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/*
    Purpose: To isolate interaction with the database as well as cache typically called flows in memory for quick access.
        Also acts as a conversion layer, converting the database entity version with the normal use version of the data.
 */
class ApplicationRepository(
    scope: CoroutineScope,
    private val database: CustomItemDatabase
) {
    //Used to check if data should be retrieved from the URL
    val isEmpty: StateFlow<Boolean?> = database.customItemDao().isEmpty().stateIn(
        scope = scope,
        started = SharingStarted.Eagerly,
        initialValue = null
    )


    val allValidSortedItems: StateFlow<List<CustomItem>> = database.customItemDao().getAllValidItems().map { customItemEntityList ->
        customItemEntityList.fastMap { customItemEntity ->
            customItemEntity.convertToModel()
        }
    }.stateIn(
        scope = scope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )



    @WorkerThread
    suspend fun insertItems(items: List<CustomItem>): List<Long> {
        val itemEntities = items.fastMap { model -> CustomItemEntity.convertFromModel(model) }
        database.customItemDao().insertItems(itemEntities)
        return emptyList()
    }

    @WorkerThread
    suspend fun clear() = database.customItemDao().clear()
}