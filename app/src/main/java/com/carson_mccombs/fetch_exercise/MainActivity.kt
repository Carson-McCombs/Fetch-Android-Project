package com.carson_mccombs.fetch_exercise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.room.Room
import com.carson_mccombs.fetch_exercise.customItem.views.CustomItemsScreenView
import com.carson_mccombs.fetch_exercise.customItem.viewModels.CustomItemsScreenViewModel
import com.carson_mccombs.fetch_exercise.customItem.viewModels.CustomItemsScreenViewModelFactory
import com.carson_mccombs.fetch_exercise.database.models.CustomItemDatabase
import com.carson_mccombs.fetch_exercise.database.models.ApplicationRepository
import com.carson_mccombs.fetch_exercise.ui.theme.FetchExerciseTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.system.exitProcess


class MainActivity : ComponentActivity() {

    private val database by lazy {
        Room.databaseBuilder(
            context = applicationContext,
            klass = CustomItemDatabase::class.java,
            name = "cm_fetch_database"
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
        //StrictMode.enableDefaults()
        //enableEdgeToEdge()
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
