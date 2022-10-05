package com.emikhalets.medialib.utils

import com.emikhalets.medialib.data.entity.support.GenreData
import javax.inject.Inject

class GenresHelper @Inject constructor() {

    private var movieGenres: List<GenreData> = emptyList()
    private var tvGenres: List<GenreData> = emptyList()

    fun setMoviesGenres(list: List<GenreData>?) {
        movieGenres = list ?: emptyList()
    }

    fun setTvGenres(list: List<GenreData>?) {
        tvGenres = list ?: emptyList()
    }

    fun getMovieGenre(id: Int?): String {
        id ?: return NO_GENRE
        return getGenre(id, movieGenres)
    }

    fun getMovieGenres(ids: List<Int>?): String {
        ids ?: return NO_GENRES
        return getGenres(ids, movieGenres)
    }

    fun getTvGenre(id: Int?): String {
        id ?: return NO_GENRE
        return getGenre(id, tvGenres)
    }

    fun getTvGenres(ids: List<Int>?): String {
        ids ?: return NO_GENRES
        return getGenres(ids, tvGenres)
    }

    private fun getGenre(id: Int, genres: List<GenreData>): String {
        return try {
            genres.find { it.id == id }?.name ?: NO_GENRE
        } catch (ex: IndexOutOfBoundsException) {
            NO_GENRE
        }
    }

    private fun getGenres(ids: List<Int>, genres: List<GenreData>): String {
        return try {
            val genresList = mutableListOf<String>()
            ids.forEach { id -> genres.find { it.id == id }?.name?.let { genresList.add(it) } }
            if (genresList.isEmpty()) {
                NO_GENRES
            } else {
                genresList.joinToString(", ")
            }
        } catch (ex: IndexOutOfBoundsException) {
            NO_GENRES
        }
    }

    companion object {
        const val NO_GENRE = "no genre"
        const val NO_GENRES = "no genres"

        fun remoteToString(genres: List<GenreData>?): String {
            return genres?.joinToString(", ") { it.name.toString() } ?: NO_GENRES
        }
    }
}