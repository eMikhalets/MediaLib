package com.emikhalets.medialib.utils.enums

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.emikhalets.medialib.R

enum class ItemStatus {
    NONE,
    DONE,
    WILL_DO,
    DROPPED;

    companion object {
        fun getFromString(context: Context, value: String, itemType: ItemType): ItemStatus {
            return when (itemType) {
                ItemType.MOVIE, ItemType.SERIAL -> {
                    when (value) {
                        context.getString(R.string.app_watched) -> DONE
                        context.getString(R.string.app_will_watch) -> WILL_DO
                        context.getString(R.string.app_dropped) -> DROPPED
                        else -> NONE
                    }
                }
                ItemType.BOOK -> {
                    when (value) {
                        context.getString(R.string.app_read) -> DONE
                        context.getString(R.string.app_will_read) -> WILL_DO
                        context.getString(R.string.app_dropped) -> DROPPED
                        else -> NONE
                    }
                }
                else -> NONE
            }
        }
    }
}

enum class ItemType {
    MOVIE,
    SERIAL,
    BOOK,
    MUSIC;
}

@Composable
fun Array<ItemStatus>.toString(type: ItemType): List<String> {
    return when (type) {
        ItemType.MOVIE, ItemType.SERIAL -> {
            listOf(
                stringResource(R.string.app_status_none),
                stringResource(R.string.app_watched),
                stringResource(R.string.app_will_watch),
                stringResource(R.string.app_dropped)
            )
        }
        ItemType.BOOK -> {
            listOf(
                stringResource(R.string.app_status_none),
                stringResource(R.string.app_read),
                stringResource(R.string.app_will_read),
                stringResource(R.string.app_dropped)
            )
        }
        else -> emptyList()
    }
}