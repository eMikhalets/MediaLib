package com.emikhalets.medialib.data.entity.views

interface ViewListItem {

    val id: Int
    val poster: String
    val title: String
    val releaseYear: Int
    val rating: Int

    fun getLocaleTitle(): String
}