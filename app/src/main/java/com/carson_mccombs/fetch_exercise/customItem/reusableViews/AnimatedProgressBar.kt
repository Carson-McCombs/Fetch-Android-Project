package com.carson_mccombs.fetch_exercise.customItem.reusableViews

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carson_mccombs.fetch_exercise.ui.theme.FetchExerciseTheme


@Composable
fun AnimatedProgressBar(modifier: Modifier = Modifier, percentage: Float) {
    val targetPercentage = remember{ mutableFloatStateOf(0.0f)}
    val animatedPercentage = animateFloatAsState(
        targetValue = targetPercentage.floatValue,
        label = "Current Percentage",
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
    )
    ProgressBar(modifier = modifier, percentage = animatedPercentage.value)
    LaunchedEffect(percentage) {
        targetPercentage.floatValue = percentage
    }
}

@Preview
@Composable
private fun AnimatedProgressBar_Preview() {
    FetchExerciseTheme {
        Surface(modifier = Modifier.fillMaxSize()){
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedProgressBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp),
                    percentage = 0.77f
                )
            }

        }
    }
}