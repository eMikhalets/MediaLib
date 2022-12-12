package com.emikhalets.medialib.data.entity.support

import com.emikhalets.medialib.utils.enums.ItemStatus

interface ViewListItem {

    val id: Int
    val poster: String
    val title: String
    val titleRu: String
    val releaseYear: Int
    val rating: Int
    val genres: String
    val comment: String
    val status: ItemStatus
    val overview: String

    fun getLocaleTitle(): String
}