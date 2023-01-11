package com.emikhalets.medialib.domain.entities.movies

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.emikhalets.medialib.R

enum class MovieWatchStatus {
    NONE, WATCH, WATCH_AGAIN, WATCHED, DROPPED;
}

fun MovieWatchStatus.getIconRes(): Int? {
    return when (this) {
        MovieWatchStatus.NONE -> null
        MovieWatchStatus.WATCH -> R.drawable.ic_round_bookmark_24
        MovieWatchStatus.WATCH_AGAIN -> R.drawable.ic_round_question_mark_24
        MovieWatchStatus.WATCHED -> R.drawable.ic_round_visibility_24
        MovieWatchStatus.DROPPED -> R.drawable.ic_round_close_24
    }
}

@Composable
fun MovieWatchStatus.getText(): String {
    return when (this) {
        MovieWatchStatus.NONE -> stringResource(R.string.movie_status_none)
        MovieWatchStatus.WATCH -> stringResource(R.string.movie_status_watch)
        MovieWatchStatus.WATCH_AGAIN -> stringResource(R.string.movie_status_watch_again)
        MovieWatchStatus.WATCHED -> stringResource(R.string.movie_status_watched)
        MovieWatchStatus.DROPPED -> stringResource(R.string.movie_status_dropped)
    }
}