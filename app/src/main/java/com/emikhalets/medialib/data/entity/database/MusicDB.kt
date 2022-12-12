package com.emikhalets.medialib.data.entity.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.emikhalets.medialib.data.entity.support.ViewListItem
import com.emikhalets.medialib.utils.enums.ItemStatus
import java.util.*

@Entity(tableName = "musics")
data class MusicDB(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") override val id: Int = 0,
    @ColumnInfo(name = "title") override val title: String = "",
    @ColumnInfo(name = "title_ru", defaultValue = "") override val titleRu: String = "",
    @ColumnInfo(name = "author") val author: String = "",
    @ColumnInfo(name = "genres") override val genres: String = "",
    @ColumnInfo(name = "overview") override val overview: String = "",
    @ColumnInfo(name = "poster_path") override val poster: String = "",
    @ColumnInfo(name = "release_year") override val releaseYear: Int =
        Calendar.getInstance().get(Calendar.YEAR),
    @ColumnInfo(name = "vote_average") val voteAverage: Double = 0.0,
    @ColumnInfo(name = "save_date") val saveDate: Long = Date().time,
    @ColumnInfo(name = "comment") override val comment: String = "",
    @ColumnInfo(name = "rating") override val rating: Int = 0,
    @ColumnInfo(name = "tags") val tags: String = "",
    @ColumnInfo(name = "status") override val status: ItemStatus = ItemStatus.NONE,
) : ViewListItem {

    override fun getLocaleTitle(): String {
        return title
    }
}