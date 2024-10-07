package com.carson_mccombs.fetch_exercise.customItem.models

import kotlin.math.min

enum class ProgressState {
    UNKNOWN, //The default progress state; If this is ever seen or outputted, that means that for whatever reason, the process wasn't created correctly or the progress wasn't intended to be tracked.
    STARTED, //Should be set once the process starts and while it is active.
    COMPLETED, //Should be the final call if the process is successful ( assuming that the progress is being tracked )
    ERROR //Should be the final call if the process is unsuccessful ( assuming that the progress is being tracked )
}

/*
    Purpose: To keep track of the progress of the corresponding process. Mainly intended for tracking the progress on asynchronous tasks, coroutines, flows, etc.
*/

data class ProcessProgress(
    val progressState: ProgressState = ProgressState.UNKNOWN,
    val progressCurrent: Long? = null,
    val progressTotal: Long? = null,
) {
    fun getProgressPercent(): Float {
        if (progressState == ProgressState.COMPLETED) return 100f
        return min(100 * (progressCurrent?.toFloat() ?: 0f) / (progressTotal?.toFloat() ?: 1f), 100f)
    }
}