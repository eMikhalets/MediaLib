package com.emikhalets.medialib.presentation.screens.movies.details

import com.emikhalets.medialib.domain.entities.movies.MovieFullEntity
import com.emikhalets.medialib.utils.State
import com.emikhalets.medialib.utils.UiString

data class MovieDetailsState(
    val entity: MovieFullEntity? = null,
    val loading: Boolean = false,
    val deleted: Boolean = false,
    val error: UiString? = null,
) : State
