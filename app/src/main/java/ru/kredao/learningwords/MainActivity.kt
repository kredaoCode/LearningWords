package ru.kredao.learningwords

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.kredao.learningwords.screens.EditDictionaries
import ru.kredao.learningwords.screens.ListDictionaries
import ru.kredao.learningwords.ui.theme.LearningWordsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LearningWordsTheme {
                val navController = rememberNavController()
                val dictionaries = remember {
                    mutableStateListOf(
                        mutableStateMapOf(
                            "name" to "English words",
                            "dictionary" to mutableStateListOf(
                                mutableStateListOf(
                                    "Box", "Коробка"
                                ),
                                mutableStateListOf(
                                    "Fox", "Лиса"
                                )
                            )
                        )
                    )
                }
                val editIndex = remember {
                    mutableStateOf<Int?>(null)
                }
                NavHost(navController = navController, startDestination = "ListDictionaries") {
                    composable("ListDictionaries") {
                        ListDictionaries(
                            navController, dictionaries, editIndex
                        )
                    }
                    composable("EditDictionaries") {
                        EditDictionaries(
                            navController, dictionaries, editIndex
                        )
                    }
                }
            }
        }
    }
}
