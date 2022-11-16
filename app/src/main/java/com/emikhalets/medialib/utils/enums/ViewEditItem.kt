package com.emikhalets.medialib.utils.enums

sealed class ViewEditItem {
    data class Comment(val comment: String) : ViewEditItem()
    data class Rating(val rating: Int) : ViewEditItem()
}