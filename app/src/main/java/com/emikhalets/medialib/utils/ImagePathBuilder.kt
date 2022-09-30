package com.emikhalets.medialib.utils

class ImagePathBuilder {

    private val baseImageUrl: String = "https://image.tmdb.org/t/p"

    enum class BackdropSize(val value: String) {
        W300("w300"),
        W780("w780"),
        W1280("w1280"),
        ORIGINAL("original");
    }

    enum class PosterSize(val value: String) {
        W92("w92"),
        W154("w154"),
        W185("w185"),
        W342("w342"),
        W500("w500"),
        W780("w780"),
        ORIGINAL("original");
    }

    fun buildBackdropPath(path: String?, size: BackdropSize = BackdropSize.W780): String {
        return "$baseImageUrl/${size.value}/$path"
    }

    fun buildPosterPath(path: String?, size: PosterSize = PosterSize.W185): String {
        return "$baseImageUrl/${size.value}/$path"
    }
}