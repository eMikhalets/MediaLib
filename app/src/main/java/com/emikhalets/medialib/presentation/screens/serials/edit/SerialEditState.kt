package com.emikhalets.medialib.presentation.screens.serials.edit

import com.emikhalets.medialib.domain.entities.serials.SerialFullEntity
import com.emikhalets.medialib.utils.State
import com.emikhalets.medialib.utils.UiString

data class SerialEditState(
    val entity: SerialFullEntity? = null,
    val loading: Boolean = false,
    val saved: Boolean = false,
    val error: UiString? = null,
) : State
