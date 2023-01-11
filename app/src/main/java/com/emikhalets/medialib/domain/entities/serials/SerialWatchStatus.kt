package com.emikhalets.medialib.domain.entities.serials

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.emikhalets.medialib.R

enum class SerialWatchStatus {
    NONE, WATCH, WATCH_AGAIN, WATCHED, DROPPED;
}

fun SerialWatchStatus.getIconRes(): Int? {
    return when (this) {
        SerialWatchStatus.NONE -> null
        SerialWatchStatus.WATCH -> R.drawable.ic_round_bookmark_24
        SerialWatchStatus.WATCH_AGAIN -> R.drawable.ic_round_question_mark_24
        SerialWatchStatus.WATCHED -> R.drawable.ic_round_visibility_24
        SerialWatchStatus.DROPPED -> R.drawable.ic_round_close_24
    }
}

@Composable
fun SerialWatchStatus.getText(): String {
    return when (this) {
        SerialWatchStatus.NONE -> stringResource(R.string.serial_status_none)
        SerialWatchStatus.WATCH -> stringResource(R.string.serial_status_watch)
        SerialWatchStatus.WATCH_AGAIN -> stringResource(R.string.serial_status_watch_again)
        SerialWatchStatus.WATCHED -> stringResource(R.string.serial_status_watched)
        SerialWatchStatus.DROPPED -> stringResource(R.string.serial_status_dropped)
    }
}