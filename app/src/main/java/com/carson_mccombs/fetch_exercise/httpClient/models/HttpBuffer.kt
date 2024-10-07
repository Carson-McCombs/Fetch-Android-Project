package com.carson_mccombs.fetch_exercise.httpClient.models

import com.carson_mccombs.fetch_exercise.customItem.models.CustomItem
import com.carson_mccombs.fetch_exercise.customItem.models.ProcessResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.serialization.json.Json


/*
    Purpose: Acts as a separated storage buffer specifically for HTTP Requests.

    Note: This could instead be stripped down to the HTTP call and put inside the Application Repository,
        however that would mean that UI would have to wait for both the URL Request to finish, then the
        data to be inputted into the database, before finally displaying it. Although I will admit that
        this added a bit more complexity to the processing. The biggest downside to this method is that
        now the ViewModel has to be able to swap between the database flow and the URL flow ( on startup ),
        which doesn't feel very clean.
 */
class HttpBuffer(scope: CoroutineScope) {
    val urlAddress = "https://fetch-hiring.s3.amazonaws.com/hiring.json"
    private val rawJsonListText: Flow<ProcessResult<String>> = getURLBody(
        urlAddress = urlAddress,
    )
    private val parsedItemList: StateFlow<ProcessResult<List<CustomItem>>> = rawJsonListText.map { processResult ->

        if (processResult.error != null || processResult.result == null) return@map processResult.convertTo<List<CustomItem>>()
        try {
            val parsedItems = parseJsonList<CustomItem>(processResult.result)
            return@map ProcessResult<List<CustomItem>>(result = parsedItems, progress = processResult.progress)
        } catch (e: Error) {
            return@map ProcessResult<List<CustomItem>>(error = errorParsingItemsFromJson, errorMessage = e.message, progress = processResult.progress )
        }

    }.stateIn(
        scope = scope,
        started = SharingStarted.Eagerly,
        initialValue = ProcessResult(result = emptyList())
    )
    val sortedItemList: StateFlow<ProcessResult<List<CustomItem>>> = parsedItemList.map { processResult ->
        if (processResult.error != null || processResult.result == null) return@map processResult.convertTo<List<CustomItem>>()
        try {
            val sortedItems = processResult.result.sortedWith(compareBy({ it.listId }, { it.name }))
            return@map ProcessResult<List<CustomItem>>(result = sortedItems, progress = processResult.progress)
        } catch (e: Error) {
            return@map ProcessResult<List<CustomItem>>(error = errorSortingItems, errorMessage = e.message, progress = processResult.progress )
        }

    }.stateIn(
        scope = scope,
        started = SharingStarted.Eagerly,
        initialValue = ProcessResult()
    )

    private inline fun <reified T> parseJsonList(rawText: String): List<T> {
        val parsedList: List<T> = Json.decodeFromString(rawText)
        return parsedList
    }
}