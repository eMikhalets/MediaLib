package com.emikhalets.medialib.presentation.screens.searching

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.emikhalets.medialib.R
import com.emikhalets.medialib.presentation.core.AppLoader
import com.emikhalets.medialib.presentation.core.AppTopBar
import com.emikhalets.medialib.presentation.core.ButtonPrimary
import com.emikhalets.medialib.presentation.core.Webview
import com.emikhalets.medialib.presentation.theme.AppTheme
import com.emikhalets.medialib.utils.toast

@Composable
fun SearchWithImdbScreen(
    navigateBack: () -> Unit,
    viewModel: SearchWithImdbViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    var isLoading by remember { mutableStateOf(false) }
    var saveEnabled by remember { mutableStateOf(false) }

    LaunchedEffect(state.saved) {
        if (state.saved) {
            navigateBack()
        }
    }

    LaunchedEffect(state.imdbId) {
        saveEnabled = state.imdbId != null
    }

    LaunchedEffect(state.error) {
        val error = state.error
        if (error != null) {
            val message = error.asString(context)
            message.toast(context)
            viewModel.resetError()
        }
    }

    LaunchedEffect(state.saved) {
        if (state.saved) {
            navigateBack()
        }
    }

    if (state.loading || isLoading) {
        AppLoader()
    } else {
        SearchWithImdbScreen(
            link = stringResource(R.string.searching_imdb_link),
            saveEnabled = saveEnabled,
            onUrlLoaded = { viewModel.parseImdbId(it) },
            onSaveClicked = { viewModel.searchAndSaveMovie() },
            onBackClick = navigateBack
        )
    }
}

@Composable
private fun SearchWithImdbScreen(
    link: String,
    saveEnabled: Boolean,
    onUrlLoaded: (String) -> Unit,
    onSaveClicked: () -> Unit,
    onBackClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        AppTopBar(
            title = stringResource(id = R.string.screen_title_searching),
            onNavigateBack = onBackClick
        )
        Webview(
            url = link,
            onPageFinished = onUrlLoaded,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        )
        ButtonPrimary(
            text = stringResource(R.string.searching_save),
            enabled = saveEnabled,
            onClick = { onSaveClicked() },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        SearchWithImdbScreen(
            saveEnabled = false,
            link = "",
            onUrlLoaded = {},
            onSaveClicked = {},
            onBackClick = {}
        )
    }
}