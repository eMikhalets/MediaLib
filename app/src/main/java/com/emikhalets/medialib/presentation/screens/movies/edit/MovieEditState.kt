package com.emikhalets.medialib.presentation.screens.movies.edit

import com.emikhalets.medialib.domain.entities.movies.MovieFullEntity
import com.emikhalets.medialib.utils.State
import com.emikhalets.medialib.utils.UiString

data class MovieEditState(
    val entity: MovieFullEntity? = null,
    val loading: Boolean = false,
    val saved: Boolean = false,
    val error: UiString? = null,
) : State
