package com.emikhalets.medialib.data.network.entities

enum class RatingType(val source: String) {
    IMDB("Internet Movie Database"),
    ROTTEN("Rotten Tomatoes"),
    META("Metacritic");

    companion object {

        fun getType(source: String): RatingType? {
            val map = values().associateBy { it.source }
            return map[source]
        }
    }
}