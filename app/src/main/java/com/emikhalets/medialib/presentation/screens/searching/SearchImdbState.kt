package com.emikhalets.medialib.presentation.screens.searching

import com.emikhalets.medialib.utils.State
import com.emikhalets.medialib.utils.UiString

data class SearchImdbState(
    val imdbId: String? = null,
    val loading: Boolean = false,
    val saved: Boolean = false,
    val error: UiString? = null,
) : State