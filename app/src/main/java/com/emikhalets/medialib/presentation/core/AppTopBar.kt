package com.emikhalets.medialib.presentation.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.emikhalets.medialib.R
import com.emikhalets.medialib.domain.entities.compose.MenuIconEntity

@Composable
fun AppTopBar(
    title: String,
    onNavigateBack: (() -> Unit)? = null,
    actions: List<MenuIconEntity> = emptyList(),
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        elevation = 0.dp,
        navigationIcon = if (onNavigateBack == null) {
            null
        } else {
            {
                Icon(
                    painter = painterResource(id = R.drawable.ic_round_arrow_back_24),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { onNavigateBack() }
                        .padding(20.dp, 16.dp)

                )
            }
        },
        actions = {
            actions.forEach { menuItem ->
                Icon(
                    painter = painterResource(id = menuItem.iconRes),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { menuItem.onClick() }
                        .padding(10.dp, 16.dp)

                )
            }
        }
    )
}