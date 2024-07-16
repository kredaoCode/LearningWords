package ru.kredao.learningwords.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import ru.kredao.learningwords.R

// Set of Material typography styles to start with
val myFontFamily = FontFamily(
    Font(R.font.montserrat, FontWeight.Normal),
    Font(R.font.montserrat_semibold, FontWeight.SemiBold)
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = myFontFamily,
        fontWeight = FontWeight.SemiBold,
    ),
    displayMedium = TextStyle(
        fontFamily = myFontFamily,
        fontWeight = FontWeight.Normal,
    ),
)