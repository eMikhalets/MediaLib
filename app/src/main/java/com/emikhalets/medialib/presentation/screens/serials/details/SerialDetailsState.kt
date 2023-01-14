package com.emikhalets.medialib.presentation.screens.serials.details

import com.emikhalets.medialib.domain.entities.serials.SerialFullEntity
import com.emikhalets.medialib.utils.State
import com.emikhalets.medialib.utils.UiString

data class SerialDetailsState(
    val entity: SerialFullEntity? = null,
    val loading: Boolean = false,
    val deleted: Boolean = false,
    val error: UiString? = null,
) : State
