package com.emikhalets.medialib.presentation.screens.books

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emikhalets.medialib.presentation.theme.AppTheme

@Composable
fun BooksScreen(
    navController: NavHostController,
) {
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        BooksScreen(rememberNavController())
    }
}