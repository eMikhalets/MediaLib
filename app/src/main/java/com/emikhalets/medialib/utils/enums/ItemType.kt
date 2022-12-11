package com.emikhalets.medialib.utils.enums

enum class ItemType(val id: Int) {
    MOVIE(0),
    SERIAL(1),
    BOOK(2),
    MUSIC(3);

    companion object {
        fun getById(id: Int?): ItemType {
            val map = values().associateBy { it.id }
            return map[id] ?: MOVIE
        }
    }
}