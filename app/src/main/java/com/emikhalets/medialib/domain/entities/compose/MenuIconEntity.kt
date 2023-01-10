package com.emikhalets.medialib.domain.entities.compose

import androidx.compose.ui.graphics.vector.ImageVector

data class MenuIconEntity(
    val icon: ImageVector,
    val onClick: () -> Unit,
)
