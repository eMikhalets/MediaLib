package com.emikhalets.medialib.presentation.screens.search

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emikhalets.medialib.R
import com.emikhalets.medialib.presentation.core.AppScaffold
import com.emikhalets.medialib.presentation.core.Webview
import com.emikhalets.medialib.presentation.theme.AppTheme

@Composable
fun SearchMoviesScreen(
    navController: NavHostController,
    viewModel: SearchMovieViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    var url by remember { mutableStateOf("") }

    LaunchedEffect(viewModel.state.imdbId) {
        // TODO: invoke omdb request
        Toast.makeText(context, viewModel.state.imdbId, Toast.LENGTH_SHORT).show()
    }

    SearchMoviesScreen(
        navController = navController,
        url = url,
        onUrlChange = { url = it },
        onParseId = { viewModel.parseUrl(it) },
    )
}

@Composable
private fun SearchMoviesScreen(
    navController: NavHostController,
    url: String,
    onUrlChange: (String) -> Unit,
    onParseId: (String) -> Unit,
) {
    AppScaffold(navController) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Webview(
                url = stringResource(R.string.webview_imdb_link),
                onPageLoaded = { onUrlChange(it) },
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            )
            Button(
                onClick = { onParseId(url) },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = stringResource(R.string.app_parse))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        SearchMoviesScreen(
            navController = rememberNavController(),
            url = stringResource(R.string.webview_imdb_link),
            onUrlChange = {},
            onParseId = {},
        )
    }
}