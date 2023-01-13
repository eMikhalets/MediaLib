package com.emikhalets.medialib.domain.entities.compose

import androidx.annotation.DrawableRes

data class MenuIconEntity(
    @DrawableRes val iconRes: Int,
    val onClick: () -> Unit,
)
