package com.emikhalets.medialib.data.entity.support

interface ViewListItem {

    val id: Int
    val poster: String
    val title: String
    val releaseYear: Int
    val rating: Int
    val genres: String

    fun getLocaleTitle(): String
}