package com.emikhalets.medialib.presentation.screens.saved_movie

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.emikhalets.medialib.presentation.theme.AppTheme

@Composable
fun SavedMovieScreen(
    navController: NavHostController,
    movieId: Int,
) {
    Text("Saved movie")
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
    }
}