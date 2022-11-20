package com.emikhalets.medialib.data.entity.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.emikhalets.medialib.data.entity.views.ViewListItem
import com.emikhalets.medialib.utils.enums.ItemStatus
import java.util.*

@Entity(tableName = "movies")
data class MovieDB(
    @PrimaryKey @ColumnInfo(name = "id") override val id: Int = 0,
    @ColumnInfo(name = "title") override val title: String,
    @ColumnInfo(name = "title_ru") val titleRu: String = "",
    @ColumnInfo(name = "genres") val genres: String = "",
    @ColumnInfo(name = "overview") val overview: String = "",
    @ColumnInfo(name = "poster_path") override val poster: String = "",
    @ColumnInfo(name = "release_year") override val releaseYear: Int =
        Calendar.getInstance().get(Calendar.YEAR),
    @ColumnInfo(name = "runtime") val runtime: Int = 0,
    @ColumnInfo(name = "budget") val budget: Long = 0,
    @ColumnInfo(name = "revenue") val revenue: Long = 0,
    @ColumnInfo(name = "vote_average") val voteAverage: Double = 0.0,
    @ColumnInfo(name = "save_date") val saveDate: Long = Date().time,
    @ColumnInfo(name = "comment") val comment: String = "",
    @ColumnInfo(name = "rating") override val rating: Int = 0,
    @ColumnInfo(name = "tags") val tags: String = "",
    @ColumnInfo(name = "status") val status: ItemStatus = ItemStatus.NONE,
) : ViewListItem {

    override fun getLocaleTitle(): String {
        return when (Locale.getDefault().language) {
            "ru" -> titleRu
            else -> title
        }
    }
}