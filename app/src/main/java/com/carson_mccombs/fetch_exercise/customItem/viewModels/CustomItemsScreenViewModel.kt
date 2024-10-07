package com.carson_mccombs.fetch_exercise.customItem.viewModels


import android.util.Log
import androidx.compose.animation.core.MutableTransitionState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.carson_mccombs.fetch_exercise.customItem.models.CustomItem
import com.carson_mccombs.fetch_exercise.customItem.models.ProcessProgress
import com.carson_mccombs.fetch_exercise.customItem.models.ProgressState
import com.carson_mccombs.fetch_exercise.database.models.ApplicationRepository
import com.carson_mccombs.fetch_exercise.httpClient.models.HttpBuffer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/*
    Purpose: To act as the intermediary connection between the CustomItemsScreenView with the Database and HTTP Buffer.

    Note: I purposefully unpack the ProcessResult objects into separate flows. This is because its intended purpose is purely backend and is not meant to display data.
 */

class CustomItemsScreenViewModel(
    val repository: ApplicationRepository
): ViewModel() {
    //class that deals with the http requests and acts as a data buffer for downloaded data
    private var httpBuffer: HttpBuffer? = null

    private val _sortedItemsList: MutableStateFlow<List<CustomItem>> = MutableStateFlow(emptyList())
    val sortedItemsList: StateFlow<List<CustomItem>> = _sortedItemsList.asStateFlow()

    private val _processProgress: MutableStateFlow<ProcessProgress> = MutableStateFlow(
        ProcessProgress()
    )
    val processProgress: StateFlow<ProcessProgress> = _processProgress.asStateFlow()

    private val _errorMessage: MutableStateFlow<String> = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage.asStateFlow()

    val showErrorMessage: MutableTransitionState<Boolean> = MutableTransitionState(false)
    val showProgress: MutableTransitionState<Boolean> = MutableTransitionState(false)

    private fun save() {
        if (sortedItemsList.value.isEmpty()) return
        viewModelScope.launch(Dispatchers.IO) {
            repository.clear()
            repository.insertItems(sortedItemsList.value)
        }

    }

    /*
        Retrieves data from the URL, then populates the UI with the data, then saves the data to the database
     */
    fun retrieveDataFromURL() {
        viewModelScope.launch(Dispatchers.IO) {

            /* Creates a new instance of the HttpBuffer, because we are creating a new instance everytime we refresh. I would check here first for memory leaks.
               The httpBuffer starts immediately after creation, so we just need to subscribe to its flows to receive updates.
             */
            httpBuffer = HttpBuffer(viewModelScope)
            showProgress.targetState = true

            httpBuffer!!.sortedItemList.collect{ processResult -> //Subscribe to the httpBuffer flow detect updates. Please see the HttpBuffer class for some of the pros and cons of this methods.
                _processProgress.value = processResult.progress
                if (processResult.progress.progressState == ProgressState.ERROR){ //If an error is found, display the error dialog
                    showErrorMessage.targetState = true
                    _errorMessage.value = (processResult.errorMessage ?: "" ) + " I recommend making sure that you make sure your device is connected to internet before restarting the app :)"
                    Log.d("FetchExercise-VM", "Error: ${processResult.error?.message ?: ""}")
                }
                else if (processResult.progress.progressState == ProgressState.COMPLETED) { //If it is completed, then populate the data before saving it to the database
                    Log.d("FetchExercise-VM", "Updating Database")
                    _sortedItemsList.value = processResult.result ?: emptyList()
                    save() //saves to database
                    delay(500L) //a small delay to let the user see the 100% finished progress bar
                    showProgress.targetState = false //closes progress dialog
                }
            }
        }
    }

    init {
        runBlocking { //to combat the occasional race condition
            while (repository.isEmpty.value == null) {
                delay(50)
            }
        }
        if (repository.isEmpty.value!!) { //if there is nothing in the database, retrieve data from the URL
            Log.d("FetchExercise-VM", "Repository is Empty")
            retrieveDataFromURL()
        }
        else {
            Log.d("FetchExercise-VM", "Repository loading from DB")
            viewModelScope.launch(Dispatchers.IO) {
                repository.allValidSortedItems.collect{ items ->
                    _sortedItemsList.value = items
                }
            }
        }
    }



}

@Suppress("UNCHECKED_CAST")
class CustomItemsScreenViewModelFactory(
    val repository: ApplicationRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CustomItemsScreenViewModel(
            repository = repository
        ) as T
    }
}
