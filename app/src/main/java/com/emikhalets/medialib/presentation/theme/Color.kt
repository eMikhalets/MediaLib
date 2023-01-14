package com.emikhalets.medialib.presentation.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

object AppColors {

    val Purple200 = Color(0xFFBB86FC)
    val Purple500 = Color(0xFF6200EE)
    val Purple700 = Color(0xFF3700B3)
    val Teal200 = Color(0xFF03DAC5)
    val Cyan = Color(0xFF018786)
    val Red500 = Color(0xFFF44336)
    val Blue700 = Color(0xFF1976D2)

    val Colors.textSecondary get() = if (isLight) Color.Gray else Color.Black
    val Colors.textLink get() = if (isLight) Blue700 else Color.Black
    val Colors.disabled get() = if (isLight) Color.LightGray else Color.Black
}