package com.emikhalets.medialib.presentation.screens.main

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emikhalets.medialib.presentation.theme.AppTheme

@Composable
fun MainScreen(navController: NavHostController) {
    Text(text = "Saved movies, serials, and books")
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        MainScreen(rememberNavController())
    }
}