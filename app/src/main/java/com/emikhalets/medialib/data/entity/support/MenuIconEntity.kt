package com.emikhalets.medialib.data.entity.support

import androidx.compose.ui.graphics.vector.ImageVector

data class MenuIconEntity(
    val icon: ImageVector,
    val onClick: () -> Unit,
)
