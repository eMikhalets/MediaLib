package com.emikhalets.medialib.presentation.screens.search

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.emikhalets.medialib.presentation.theme.AppTheme

@Composable
fun SearchScreen() {
    Text(text = "Search movies, serials, and books")
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        SearchScreen()
    }
}