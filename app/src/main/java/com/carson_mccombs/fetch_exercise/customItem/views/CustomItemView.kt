package com.carson_mccombs.fetch_exercise.customItem.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carson_mccombs.fetch_exercise.customItem.models.CustomItem
import com.carson_mccombs.fetch_exercise.ui.theme.FetchExerciseTheme

@Composable
fun CustomItemView(modifier: Modifier = Modifier, item: CustomItem) {
    Card(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "ID: ${item.id}")
            Text(text = "List ID: ${item.listId}")
            Text(text = "Name: ${item.name ?: "EMPTY NAME"}")
        }
    }

}

@Preview
@Composable
fun CustomItemView_Preview(){
    FetchExerciseTheme {
        Column(
            modifier = Modifier.width(256.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            CustomItemView(
                item = CustomItem(
                    id = 684,
                    listId = 1,
                    name = "Item 684"
                )
            )
            CustomItemView(
                item = CustomItem(
                    id = 736,
                    listId = 3,
                    name = null
                )
            )
        }


    }
}