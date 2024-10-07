package com.carson_mccombs.fetch_exercise.customItem.views.reusableViews

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carson_mccombs.fetch_exercise.ui.theme.FetchExerciseTheme
import kotlin.math.min


@Composable
fun ProgressBar(modifier: Modifier = Modifier, percentage: Float) {
    Column(modifier = Modifier.wrapContentSize(), horizontalAlignment = Alignment.CenterHorizontally){
        Text("Progress: $percentage%")
        LinearProgressIndicator(modifier = modifier, progress = { percentage })
    }

}

@Composable
fun ProgressBar(modifier: Modifier = Modifier, current: Long?, total: Long?) {
    val currentPercentage = remember { mutableFloatStateOf( min(100 * (current?.toFloat() ?: 0f) / (total?.toFloat() ?: 1f), 100f)) }
    Column(modifier = Modifier.wrapContentSize(), horizontalAlignment = Alignment.CenterHorizontally){
        Text("Progress: ${currentPercentage.floatValue}% ( $current / $total )")
        LinearProgressIndicator(modifier = modifier, progress = { currentPercentage.floatValue })
    }

}

@Preview
@Composable
private fun ProgressBar_Preview() {
    FetchExerciseTheme {
        Surface(modifier = Modifier.fillMaxSize()){
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProgressBar(
                    modifier = Modifier.fillMaxWidth().height(32.dp),
                    current = 77,
                    total = 100
                )
            }

        }
    }
}