package com.emikhalets.medialib.presentation.screens.library

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.medialib.R
import com.emikhalets.medialib.presentation.core.AppTopBar
import com.emikhalets.medialib.presentation.core.IconSecondary
import com.emikhalets.medialib.presentation.core.TextTitle
import com.emikhalets.medialib.presentation.theme.AppTheme

@Composable
fun LibraryScreen(
    navigateToMovies: () -> Unit,
    navigateToSerials: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AppTopBar(title = stringResource(id = R.string.screen_title_library))
        Row(modifier = Modifier.fillMaxWidth()) {
            MenuItem(
                title = stringResource(id = R.string.library_menu_item_movies),
                icon = R.drawable.ic_round_local_movies_24,
                onItemClick = navigateToMovies
            )
            MenuItem(
                title = stringResource(id = R.string.library_menu_item_serials),
                icon = R.drawable.ic_round_tv_24,
                onItemClick = navigateToSerials
            )
        }
    }
}

@Composable
private fun RowScope.MenuItem(
    title: String,
    @DrawableRes icon: Int,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .weight(1f)
            .clickable { onItemClick() }
            .padding(32.dp)
    ) {
        IconSecondary(
            drawableRes = icon,
            modifier = Modifier
                .aspectRatio(1f, true)
        )
        TextTitle(
            text = title,
            fontSize = 18.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        LibraryScreen(
            navigateToMovies = {},
            navigateToSerials = {}
        )
    }
}