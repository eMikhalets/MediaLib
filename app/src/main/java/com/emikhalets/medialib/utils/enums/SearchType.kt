package com.emikhalets.medialib.utils.enums

import com.emikhalets.medialib.R

enum class SearchType(val urlRes: Int) {
    MOVIE(R.string.webview_imdb_link),
    SERIAL(R.string.webview_imdb_link),
    BOOK(2),
    MUSIC(3);
}