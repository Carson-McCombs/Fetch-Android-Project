package com.carson_mccombs.fetch_exercise.customItem.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.carson_mccombs.fetch_exercise.customItem.model.ProcessProgress
import com.carson_mccombs.fetch_exercise.customItem.reusableViews.AnimatedProgressBar
import com.carson_mccombs.fetch_exercise.ui.theme.FetchExerciseTheme

@Composable
fun RetrievingDataDialog(progressState: State<ProcessProgress>) {
    val progressPercent = progressState.value.getProgressPercent()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(3f)
    ) {
        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
                .fillMaxWidth(0.8f)
                .height(128.dp),
            colors = CardDefaults.cardColors(),
            border = BorderStroke(3.dp, MaterialTheme.colorScheme.outline)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text="Retrieving Data ...",
                    style = MaterialTheme.typography.titleLarge
                )
                AnimatedProgressBar(modifier = Modifier.height(32.dp), percentage = progressPercent)
            }
        }
    }
}

@Preview
@Composable
fun RetrievingDataDialog_Preview() {
    FetchExerciseTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ){
            RetrievingDataDialog(progressState = remember{ mutableStateOf(ProcessProgress(progressCurrent = 100, progressTotal = 300))})
        }
    }
}