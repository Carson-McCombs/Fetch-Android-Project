package com.carson_mccombs.fetch_exercise.httpClient

import android.util.Log
import com.carson_mccombs.fetch_exercise.customItem.model.ProcessProgress
import com.carson_mccombs.fetch_exercise.customItem.model.ProcessResult
import com.carson_mccombs.fetch_exercise.customItem.model.ProgressState
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.onDownload
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.withContext

/*
    Purpose: To retrieve the body text of the URL asynchronously and sending progress updates as the data is downloaded.

    Note: If a more responsive loading of items was desired, this class could be altered to parse each line of the JSON being read, such that the items are loaded
    while they are being downloaded. Currently, this outputs progress updates for the files downloaded, but does not output any of the downloaded text until it is complete.
 */

fun getURLBody(urlAddress: String):Flow<ProcessResult<String>> {

    return channelFlow {
        try {
            val output = withContext(Dispatchers.IO) {

                Log.d("FetchExercise-GetURLBody", "Making GET Request to $urlAddress")
                val client = HttpClient(CIO)

                val response = client.get(urlAddress) {

                    onDownload { bytesSentTotal, contentLength ->
                        Log.d("FetchExercise-GetURLBody", "Received $bytesSentTotal bytes from $contentLength")
                        if (contentLength != null) withContext(Dispatchers.IO){
                            send(
                                ProcessResult(
                                    progress = ProcessProgress(
                                    progressState = ProgressState.STARTED,
                                    progressCurrent = bytesSentTotal,
                                    progressTotal = contentLength
                                    )
                                )
                            )
                        }

                    }
                }
                val bodyText = response.bodyAsText()
                client.close()


                if (response.status.value != 200) {
                    return@withContext ProcessResult(
                        result = bodyText,
                        error = errorHttpStatusCode(statusCode=response.status.value),
                        errorMessage = response.status.description,
                        progress = ProcessProgress(progressState = ProgressState.ERROR)
                    )
                }

                return@withContext ProcessResult(
                    result = bodyText,
                    progress = ProcessProgress(progressState = ProgressState.COMPLETED)
                )
            }
            send(output)
        }
        catch (error: Exception) {
            send(
                ProcessResult(
                    error = errorGettingJsonFromURL,
                    errorMessage = error.message,
                    progress = ProcessProgress(progressState = ProgressState.ERROR)
                )
            )
        }

    }
}

