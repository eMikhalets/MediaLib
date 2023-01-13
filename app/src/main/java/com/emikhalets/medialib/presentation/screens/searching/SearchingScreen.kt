package com.emikhalets.medialib.presentation.screens.searching

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
import com.emikhalets.medialib.presentation.core.AppTopBar
import com.emikhalets.medialib.presentation.core.IconPrimary
import com.emikhalets.medialib.presentation.theme.AppTheme

@Composable
fun SearchMainScreen(
    navigateImdbSearching: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AppTopBar(title = stringResource(id = R.string.searching_title))
        Row(modifier = Modifier.fillMaxWidth()) {
            MenuItem(
                title = stringResource(id = R.string.searching_menu_item_imdb),
                icon = R.drawable.ic_round_local_movies_24,
                onItemClick = navigateImdbSearching
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
private fun MainScreenPreview() {
    AppTheme {
        SearchMainScreen(
            navigateImdbSearching = {},
        )
    }
}