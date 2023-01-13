package com.emikhalets.medialib.domain.entities.utils

import androidx.annotation.DrawableRes

data class MenuIconEntity(
    @DrawableRes val iconRes: Int,
    val onClick: () -> Unit,
)
