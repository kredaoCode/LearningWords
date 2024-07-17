package ru.kredao.learningwords

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.kredao.learningwords.screens.EditDictionaries
import ru.kredao.learningwords.screens.ListDictionaries
import ru.kredao.learningwords.ui.theme.LearningWordsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LearningWordsTheme {
                //ListDictionaries()
                EditDictionaries()
            }
        }
    }
}
