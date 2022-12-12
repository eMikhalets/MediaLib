package com.emikhalets.medialib.presentation.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emikhalets.medialib.R
import com.emikhalets.medialib.presentation.core.AppButton
import com.emikhalets.medialib.presentation.core.AppScaffold
import com.emikhalets.medialib.presentation.navToSearching
import com.emikhalets.medialib.presentation.theme.AppTheme
import com.emikhalets.medialib.utils.enums.SearchType

@Composable
fun SearchMainScreen(
    navController: NavHostController,
) {
    AppScaffold(navController) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            AppButton(
                text = stringResource(id = R.string.search_movies),
                onClick = { navController.navToSearching(SearchType.MOVIE) }
            )

            AppButton(
                text = stringResource(id = R.string.search_books),
//            onClick = { navController.navToSearching(context.getString(R.string.webview_imdb_link)) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    AppTheme {
        SearchMainScreen(
            navController = rememberNavController(),
        )
    }
}