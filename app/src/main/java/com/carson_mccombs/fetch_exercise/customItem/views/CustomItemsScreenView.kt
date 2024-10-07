package com.carson_mccombs.fetch_exercise.customItem.views

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.carson_mccombs.fetch_exercise.customItem.models.CustomItem
import com.carson_mccombs.fetch_exercise.customItem.models.ProcessProgress
import com.carson_mccombs.fetch_exercise.customItem.views.reusableViews.ErrorDialog
import com.carson_mccombs.fetch_exercise.customItem.viewModels.CustomItemsScreenViewModel
import com.carson_mccombs.fetch_exercise.ui.theme.FetchExerciseTheme

@Composable
fun CustomItemsScreenView(
    viewModel: CustomItemsScreenViewModel,
    exitApplication: () -> Unit
) {
    CustomItemsScreenView(
        sortedItemsState= viewModel.sortedItemsList.collectAsState(),
        processProgress = viewModel.processProgress.collectAsState(),
        errorMessage = viewModel.errorMessage.collectAsState(),
        showProgress = viewModel.showProgress,
        showErrorMessage = viewModel.showErrorMessage,
        retrieveData = { viewModel.retrieveDataFromURL() },
        exitApplication = exitApplication
    )
}

@Composable

fun CustomItemsScreenView(
    sortedItemsState: State<List<CustomItem>>,
    processProgress: State<ProcessProgress>,
    errorMessage: State<String>,
    showProgress: MutableTransitionState<Boolean>,
    showErrorMessage: MutableTransitionState<Boolean>,
    retrieveData: () -> Unit,
    exitApplication: () -> Unit
){
    val topBarHeight = 64.dp
    val iconSize = 32.dp
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface),
        topBar = {
            Row(
               modifier = Modifier
                   .height(topBarHeight)
                   .fillMaxWidth()
                   .background(color = MaterialTheme.colorScheme.primary),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(iconSize))
                Row(
                    modifier = Modifier.wrapContentSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Items",
                        style = MaterialTheme.typography.headlineLarge.copy(color = MaterialTheme.colorScheme.onPrimary)
                    )

                }
                IconButton(
                    modifier = Modifier.wrapContentSize(),
                    onClick = retrieveData
                ) {
                    Icon(
                        modifier = Modifier
                            .size(iconSize),
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reload data from URL",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

        },
        
    ) { innerPadding ->
        AnimatedVisibility(
            modifier = Modifier.zIndex(3f),
            visibleState = showProgress,
            enter = slideInHorizontally(initialOffsetX = {width -> -width}),
            exit = slideOutHorizontally(targetOffsetX = {width -> width})
        ) {
            RetrievingDataDialog(progressState = processProgress)
        }
        AnimatedVisibility(
            modifier = Modifier.zIndex(4f),
            visibleState = showErrorMessage,
            enter = slideInHorizontally(initialOffsetX = {width -> -width}),
            exit = slideOutHorizontally(targetOffsetX = {width -> width})
        ) {
            ErrorDialog(
                errorMessage = errorMessage.value,
                onDismiss = exitApplication
            )
        }
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ){
                items(items = sortedItemsState.value) { item ->
                    if (item.isValid) {
                        CustomItemView(item = item)
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                }
            }
        }


    }
}
@Preview(name = "Dynamic")
@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CustomItemsScreenView_Preview(){
    FetchExerciseTheme {
        Surface{
            CustomItemsScreenView(
                sortedItemsState = remember {
                    mutableStateOf(
                        listOf(
                            CustomItem(id = 1, listId = 2, name = "Item 1"),
                            CustomItem(id = 2, listId = 5, name = "Item 2"),
                            CustomItem(id = 3, listId = 3, name = "Item 3")
                        )
                    )

                },
                errorMessage = remember {
                    mutableStateOf("")
                },
                showProgress = MutableTransitionState(false),
                showErrorMessage = MutableTransitionState(false),
                processProgress = remember{ mutableStateOf(ProcessProgress()) },
                retrieveData = {},
                exitApplication = {}
            )
        }
    }
}