package com.emikhalets.medialib.data.entity.views

interface ViewListItem {

    val id: Int
    val poster: String
    val title: String
    val originalTitle: String
    val releaseDate: Long
    val rating: Int
}