package com.emikhalets.medialib.utils.navigation

import com.emikhalets.medialib.R

enum class AppScreen(val route: String) {

    Library("library"),
    Movies("movies"),
    MovieDetails("movie_details"),
    MovieEdit("movie_edit"),
    Serials("serials"),
    SerialDetails("serial_details"),
    SerialEdit("serial_edit"),
    Searching("searching"),
    SearchImdb("search_imdb");

    companion object {

        fun getBottomBarItems(): List<AppScreen> {
            return listOf(Library, Searching)
        }

        fun AppScreen.getBottomBarIconRes(): Int {
            return when (this) {
                Library -> R.drawable.ic_round_home_24
                Searching -> R.drawable.ic_round_search_24
                else -> 0
            }
        }

        fun AppScreen.getBottomBarTextRes(): Int {
            return when (this) {
                Library -> R.string.screen_title_library
                Searching -> R.string.screen_title_searching
                else -> 0
            }
        }
    }
}