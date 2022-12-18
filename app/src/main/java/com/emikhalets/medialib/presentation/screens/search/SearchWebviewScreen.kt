package com.emikhalets.medialib.presentation.screens.search

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.emikhalets.medialib.utils.enums.SearchType

@Composable
fun SearchWebviewScreen(
    navController: NavHostController,
    type: SearchType,
    viewModel: SearchWebviewViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    var saveEnabled by remember { mutableStateOf(false) }

    LaunchedEffect(state.saved) {
        if (state.saved) navController.popBackStack()
    }

    LaunchedEffect(state.idParsed) {
        saveEnabled = state.idParsed != null
    }

    LaunchedEffect(state.errorCounter) {
        if (state.errorCounter == 0) return@LaunchedEffect
        Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
    }

    SearchMoviesScreen(
        navController = navController,
        link = stringResource(type.urlRes),
        saveEnabled = saveEnabled,
        onUrlLoaded = { viewModel.parseUrl(it, type) },
        onSaveClicked = { viewModel.searchItem(type) }
    )
}

@Composable
private fun SearchMoviesScreen(
    navController: NavHostController,
    link: String,
    saveEnabled: Boolean,
    onUrlLoaded: (String) -> Unit,
    onSaveClicked: () -> Unit,
) {

    AppScaffold(navController) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Webview(
                url = link,
                onPageLoaded = { onUrlLoaded(it) },
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            )
            Button(
                enabled = saveEnabled,
                onClick = { onSaveClicked() },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = stringResource(R.string.save))
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
            saveEnabled = false,
            link = stringResource(R.string.webview_imdb_link),
            onUrlLoaded = {},
            onSaveClicked = {}
        )
    }
}