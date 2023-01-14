package com.emikhalets.medialib.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = AppColors.Purple200,
    primaryVariant = AppColors.Purple700,
    secondary = AppColors.Teal200
)

private val LightColorPalette = lightColors(
    primary = AppColors.Purple500,
    primaryVariant = AppColors.Purple700,
    secondary = AppColors.Teal200,
    secondaryVariant = AppColors.Cyan,
    background = Color.White,
    surface = Color.White,
    error = AppColors.Red500,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.White
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}