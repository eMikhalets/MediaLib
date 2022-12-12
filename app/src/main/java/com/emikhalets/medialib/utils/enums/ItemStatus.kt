package com.emikhalets.medialib.utils.enums

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.ui.graphics.vector.ImageVector
import com.emikhalets.medialib.R

enum class ItemStatus(val res: Int, val icon: ImageVector?) {
    NONE(R.string.item_status_none, null),
    WATCH(R.string.item_status_will_watch, Icons.Default.Bookmark),
    WATCHED(R.string.item_status_watched, Icons.Default.Visibility),
    NO_MIND(R.string.item_status_no_mind, Icons.Default.QuestionMark),
    DROPPED(R.string.item_status_dropped, Icons.Default.Close);

    companion object {

        fun get(context: Context, value: String): ItemStatus {
            val map = values().associateBy { context.getString(it.res) }
            return map[value] ?: NONE
        }

        fun get(status: String): ItemStatus {
            val map = values().associateBy { it.toString() }
            return map[status] ?: NONE
        }

        fun statusList(context: Context): List<String> {
            return values().toList().map { context.getString(it.res) }
        }

        fun statusBookList(context: Context): List<String> {
            return values().toList().map { context.getString(it.stringForBooks()) }
        }

        private fun ItemStatus.stringForBooks(): Int {
            return when (this) {
                WATCH -> R.string.item_status_read
                WATCHED -> R.string.item_status_will_read
                else -> this.res
            }
        }
    }
}