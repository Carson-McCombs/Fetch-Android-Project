package com.carson_mccombs.fetch_exercise.customItem.model

/*
    Purpose: To acts as a generic interface to bundle the result, errors, error context, and progress updates.
 */
data class ProcessResult<T>(
    val result: T? = null,
    val error: Error? = null,
    val errorMessage: String? = null,
    val progress: ProcessProgress = ProcessProgress() // not required to be set if progress is not being tracked
) {

    /*
        This function is mainly used for data transformations ( such as within flows ) where if an error occurs,
        the error successfully propagates through each transformation
     */
    inline fun <reified A> convertTo(): ProcessResult<A> {
        val convertedResult: A? = if (result != null && result is A) result else null
        return ProcessResult(
            result = convertedResult,
            error = error,
            errorMessage = errorMessage,
            progress = progress
        )
    }

}