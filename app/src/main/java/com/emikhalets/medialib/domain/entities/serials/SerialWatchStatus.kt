package com.emikhalets.medialib.domain.entities.serials

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.emikhalets.medialib.R

enum class SerialWatchStatus {
    NONE, WATCH, WATCH_AGAIN, WATCHED, DROPPED;

    companion object {

        fun SerialWatchStatus.getIconRes(): Int? {
            return when (this) {
                NONE -> null
                WATCH -> R.drawable.ic_round_bookmark_24
                WATCH_AGAIN -> R.drawable.ic_round_question_mark_24
                WATCHED -> R.drawable.ic_round_visibility_24
                DROPPED -> R.drawable.ic_round_close_24
            }
        }

        @Composable
        fun SerialWatchStatus.getText(): String {
            return when (this) {
                NONE -> stringResource(R.string.serial_status_none)
                WATCH -> stringResource(R.string.serial_status_watch)
                WATCH_AGAIN -> stringResource(R.string.serial_status_watch_again)
                WATCHED -> stringResource(R.string.serial_status_watched)
                DROPPED -> stringResource(R.string.serial_status_dropped)
            }
        }

        fun getStatus(context: Context, text: String): SerialWatchStatus {
            return when (text) {
                context.getString(R.string.movie_status_watch) -> WATCH
                context.getString(R.string.movie_status_watch_again) -> WATCH_AGAIN
                context.getString(R.string.movie_status_watched) -> WATCHED
                context.getString(R.string.movie_status_dropped) -> DROPPED
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