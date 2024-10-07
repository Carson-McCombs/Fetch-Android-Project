package com.carson_mccombs.fetch_exercise.customItem.views.reusableViews

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.carson_mccombs.fetch_exercise.ui.theme.FetchExerciseTheme

@Composable
fun ErrorDialog(errorMessage: String, onDismiss: () -> Unit) {
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
                .wrapContentHeight(),
            colors = CardDefaults.cardColors(),
            border = BorderStroke(3.dp, MaterialTheme.colorScheme.outline)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text="Error Retrieving Data ...",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(text=errorMessage, style=MaterialTheme.typography.bodyMedium, softWrap = true)
                Button(onClick = onDismiss) {
                    Text(text = "Exit App")
                }
            }
        }
    }
}

@Preview
@Composable
private fun ErrorDialog_Preview(){
    FetchExerciseTheme {
        Surface(modifier = Modifier.fillMaxSize()){
            ErrorDialog(errorMessage = "java.lang.Error: Error retrieving JSON from url") {

            }
        }
    }

}