package com.emikhalets.medialib.utils.enums

import android.content.Context
import com.emikhalets.medialib.R

enum class MovieStatus(val res: Int) {
    NONE(R.string.app_status_none),
    WATCH(R.string.app_will_watch),
    WATCHED(R.string.app_watched),
    DROPPED(R.string.app_dropped);

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