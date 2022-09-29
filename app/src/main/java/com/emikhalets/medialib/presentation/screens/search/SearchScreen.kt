package com.emikhalets.medialib.presentation.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.emikhalets.medialib.R
import com.emikhalets.medialib.presentation.theme.AppTheme

@Composable
fun SearchScreen(
    navController: NavHostController,
) {
    var query by remember { mutableStateOf("") }

    SearchScreen(
        query = query,
        movies = emptyList(),
        onQueryChange = { query = it },
        onMovieClick = {},
        onMovieSearchClick = {},
    )
}

@Composable
fun SearchScreen(
    query: String,
    movies: List<String>,
    onQueryChange: (String) -> Unit,
    onMovieClick: (String) -> Unit,
    onMovieSearchClick: (String) -> Unit,
) {
    Column(Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            leadingIcon = { Icon(Icons.Rounded.Search, "") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            items(movies) { movie ->
                MovieItem(movie, onMovieClick, onMovieSearchClick)
            }
        }
    }
}

@Composable
private fun MovieItem(
    movie: String,
    onMovieClick: (String) -> Unit,
    onMovieSearchClick: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onMovieClick(movie) }
            .padding(8.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = "",
                contentDescription = "",
                placeholder = painterResource(R.drawable.ph_movie),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)

            )
            Text(
                text = "6.5",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .background(MaterialTheme.colors.primary.copy(alpha = 0.35f))
                    .padding(horizontal = 4.dp)
            )
            Icon(
                imageVector = Icons.Rounded.Favorite,
                contentDescription = "",
                modifier = Modifier
                    .clickable { onMovieSearchClick(movie) }
                    .align(Alignment.TopEnd)
                    .padding(top = 4.dp)
            )
        }

        Text(
            text = "Some title",
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(top = 8.dp)
        )
        Text(
            text = "some genre",
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        SearchScreen(
            query = "Some movie",
            movies = listOf("123", "123", "123", "123", "123", "123", "123", "123", "123"),
            onQueryChange = {},
            onMovieClick = {},
            onMovieSearchClick = {},
        )
    }
}