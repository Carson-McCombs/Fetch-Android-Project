package com.carson_mccombs.fetch_exercise

import android.os.Bundle
import android.os.StrictMode
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.room.Room
import com.carson_mccombs.fetch_exercise.customItem.view.CustomItemsScreenView
import com.carson_mccombs.fetch_exercise.customItem.viewModel.CustomItemsScreenViewModel
import com.carson_mccombs.fetch_exercise.customItem.viewModel.CustomItemsScreenViewModelFactory
import com.carson_mccombs.fetch_exercise.database.CustomItemDatabase
import com.carson_mccombs.fetch_exercise.database.ApplicationRepository
import com.carson_mccombs.fetch_exercise.ui.theme.FetchExerciseTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.system.exitProcess


class MainActivity : ComponentActivity() {

    private val database by lazy {
        Room.databaseBuilder(
            context = applicationContext,
            klass = CustomItemDatabase::class.java,
            name = "app_database"
        ).build()
    }

    private val repository: ApplicationRepository by lazy {
        ApplicationRepository(scope = CoroutineScope(Dispatchers.IO), database)
    }

    private val screenViewModel: CustomItemsScreenViewModel by viewModels {
        CustomItemsScreenViewModelFactory(repository = repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StrictMode.enableDefaults()
        enableEdgeToEdge()
        setContent {

            FetchExerciseTheme {
                CustomItemsScreenView(
                    viewModel = screenViewModel,
                    exitApplication = {
                        exitApplication()
                    }
                )
            }
        }
    }
    private fun exitApplication(){
        finish()
        exitProcess(0)
    }

}
