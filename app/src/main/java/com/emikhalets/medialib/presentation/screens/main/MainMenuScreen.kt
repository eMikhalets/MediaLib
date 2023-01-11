package com.emikhalets.medialib.presentation.screens.main

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.emikhalets.medialib.R
import com.emikhalets.medialib.presentation.core.IconPrimary
import com.emikhalets.medialib.presentation.theme.AppTheme

@Composable
fun MainMenuScreen(
    navigateToMovies: () -> Unit,
    navigateToSerials: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            MenuItem(
                title = stringResource(id = R.string.main_menu_item_movies),
                icon = R.drawable.ic_round_local_movies_24,
                onItemClick = navigateToMovies
            )
            MenuItem(
                title = stringResource(id = R.string.main_menu_item_serials),
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
        modifier = modifier
            .fillMaxWidth()
            .weight(1f)
            .clickable { onItemClick() }
    ) {
        IconPrimary(
            drawableRes = icon,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f, true)
        )
        Text(text = title)
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        MainMenuScreen(
            navigateToMovies = {},
            navigateToSerials = {}
        )
    }
}