package com.emikhalets.medialib.presentation.screens.serials.list

import com.emikhalets.medialib.domain.entities.serials.SerialFullEntity
import com.emikhalets.medialib.utils.State
import com.emikhalets.medialib.utils.UiString

data class SerialsState(
    val serials: List<SerialFullEntity> = emptyList(),
    val showedSerials: List<SerialFullEntity> = emptyList(),
    val loading: Boolean = false,
    val error: UiString? = null,
) : State