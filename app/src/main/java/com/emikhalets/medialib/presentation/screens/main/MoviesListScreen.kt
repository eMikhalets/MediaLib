package com.emikhalets.medialib.presentation.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.medialib.presentation.theme.MediaLibTheme

@Composable
fun MoviesListScreen() {
    var searchQuery by remember { mutableStateOf("") }

    MoviesListScreen(
        searchQuery = searchQuery,
        onSearchQueryChange = { searchQuery = it },
        moviesList = emptyList()
    )
}

@Composable
fun MoviesListScreen(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    moviesList: List<String>,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            label = { Text("Поиск просмотренных фильмов") },
            leadingIcon = { Icon(Icons.Default.Search, "") },
            maxLines = 1
        )
        Spacer(Modifier.height(16.dp))
        LazyColumn {
            items(moviesList) { movie ->
                Text("$movie")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MoviesListScreenPreview() {
    MediaLibTheme {
        MoviesListScreen(
            searchQuery = "123qwr",
            onSearchQueryChange = {},
            moviesList = emptyList()
        )
    }
}