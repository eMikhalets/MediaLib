package com.emikhalets.medialib.utils.enums

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.ui.graphics.vector.ImageVector
import com.emikhalets.medialib.R

enum class MovieStatus(val res: Int, val icon: ImageVector?) {
    NONE(R.string.app_status_none, null),
    WATCH(R.string.app_will_watch, Icons.Default.Bookmark),
    WATCHED(R.string.app_watched, Icons.Default.Visibility),
    NO_MIND(R.string.app_no_mind, Icons.Default.QuestionMark),
    DROPPED(R.string.app_dropped, Icons.Default.Close);

    companion object {

        fun get(context: Context, value: String): MovieStatus {
            val map = values().associateBy { context.getString(it.res) }
            return map[value] ?: NONE
        }

        fun get(status: String): MovieStatus {
            val map = values().associateBy { it.toString() }
            return map[status] ?: NONE
        }
    }
}

enum class BookStatus(val res: Int) {
    NONE(R.string.app_status_none),
    NEED_READ(R.string.app_dropped),
    READ(R.string.app_dropped),
    DROPPED(R.string.app_dropped);

    companion object {

        fun get(context: Context, value: String): BookStatus {
            val map = values().associateBy { context.getString(it.res) }
            return map[value] ?: NONE
        }

        fun get(status: String): BookStatus {
            val map = values().associateBy { it.toString() }
            return map[status] ?: NONE
        }
    }
}