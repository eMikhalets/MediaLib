package com.emikhalets.medialib.domain.entities.movies

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.emikhalets.medialib.R

enum class MovieWatchStatus {
    NONE, WATCH, WATCH_AGAIN, WATCHED, DROPPED;

    companion object {

        fun MovieWatchStatus.getIconRes(): Int? {
            return when (this) {
                NONE -> null
                WATCH -> R.drawable.ic_round_bookmark_24
                WATCH_AGAIN -> R.drawable.ic_round_question_mark_24
                WATCHED -> R.drawable.ic_round_visibility_24
                DROPPED -> R.drawable.ic_round_close_24
            }
        }

        @Composable
        fun MovieWatchStatus.getText(): String {
            return when (this) {
                NONE -> stringResource(R.string.movie_status_none)
                WATCH -> stringResource(R.string.movie_status_watch)
                WATCH_AGAIN -> stringResource(R.string.movie_status_watch_again)
                WATCHED -> stringResource(R.string.movie_status_watched)
                DROPPED -> stringResource(R.string.movie_status_dropped)
            }
        }

        @Composable
        fun getStatus(text: String): MovieWatchStatus {
            return when (text) {
                stringResource(R.string.movie_status_watch) -> WATCH
                stringResource(R.string.movie_status_watch_again) -> WATCH_AGAIN
                stringResource(R.string.movie_status_watched) -> WATCHED
                stringResource(R.string.movie_status_dropped) -> DROPPED
                else -> NONE
            }
        }

        @Composable
        fun getTextList(): List<String> {
            return listOf(
                NONE.getText(),
                WATCH.getText(),
                WATCH_AGAIN.getText(),
                WATCHED.getText(),
                DROPPED.getText()
            )
        }
    }
}