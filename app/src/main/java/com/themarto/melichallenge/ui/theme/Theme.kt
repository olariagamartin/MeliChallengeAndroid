package com.themarto.melichallenge.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.themarto.core.ui.theme.HighlightBlue
import com.themarto.core.ui.theme.Pink40
import com.themarto.core.ui.theme.PurpleGrey40

private val LightColorScheme = lightColorScheme(
    primary = HighlightBlue,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Composable
fun MeliChallengeTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
