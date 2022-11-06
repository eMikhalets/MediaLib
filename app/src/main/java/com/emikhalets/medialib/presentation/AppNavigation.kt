package com.emikhalets.medialib.presentation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emikhalets.medialib.R
import com.emikhalets.medialib.presentation.screens.books.BooksScreen
import com.emikhalets.medialib.presentation.screens.movies.MoviesScreen
import com.emikhalets.medialib.presentation.screens.music.MusicScreen
import com.emikhalets.medialib.presentation.screens.serials.SerialsScreen

sealed class AppScreen(val route: String, @StringRes val titleRes: Int) {

    object Movies : AppScreen("movies", R.string.screen_name_movies)
    object Serials : AppScreen("serials", R.string.screen_name_serials)
    object Books : AppScreen("books", R.string.screen_name_books)
    object Music : AppScreen("music", R.string.screen_name_music)

    companion object {
        @Composable
        fun getTitle(entry: NavBackStackEntry?): String {
            return when (entry?.destination?.route) {
                Movies.route -> stringResource(Movies.titleRes)
                Serials.route -> stringResource(Serials.titleRes)
                Books.route -> stringResource(Books.titleRes)
                Music.route -> stringResource(Music.titleRes)
                else -> ""
            }
        }

        fun getScreen(entry: NavBackStackEntry?): AppScreen {
            return when (entry?.destination?.route) {
                Movies.route -> Movies
                Serials.route -> Serials
                Books.route -> Books
                Music.route -> Music
                else -> Movies
            }
        }
    }

//    object Main : AppScreen("main", R.string.screen_name_main)
//    object Search : AppScreen("search", R.string.screen_name_search)
//    object SearchedMovie : AppScreen("searched_movie", R.string.screen_name_search)
//    object SavedMovie : AppScreen("saved_movie", R.string.screen_name_saved_movie)
}

enum class NavArgs(val key: String) {
    MOVIE_ID("movie_id");
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, AppScreen.Movies.route) {

        composable(AppScreen.Movies.route) {
            MoviesScreen(navController)
        }

        composable(AppScreen.Serials.route) {
            SerialsScreen(navController)
        }

        composable(AppScreen.Books.route) {
            BooksScreen(navController)
        }

        composable(AppScreen.Music.route) {
            MusicScreen(navController)
        }

//        composable(
//            "${AppScreen.SearchedMovie.route}/{${Args.MOVIE_ID.arg}}",
//            arguments = listOf(navArgument(Args.MOVIE_ID.arg) { type = NavType.IntType })
//        ) {
//            SearchedMovieScreen(
//                navController = navController,
//                movieId = it.arguments?.getInt(Args.MOVIE_ID.arg) ?: -1
//            )
//        }
//
//        composable(
//            "${AppScreen.SearchedMovie.route}/{${Args.MOVIE_ID.arg}}",
//            arguments = listOf(navArgument(Args.MOVIE_ID.arg) { type = NavType.IntType })
//        ) {
//            SearchedMovieScreen(
//                navController = navController,
//                movieId = it.arguments?.getInt(Args.MOVIE_ID.arg) ?: -1
//            )
//        }
//
//        composable(
//            "${AppScreen.SavedMovie.route}/{${Args.MOVIE_ID.arg}}",
//            arguments = listOf(navArgument(Args.MOVIE_ID.arg) { type = NavType.IntType })
//        ) {
//            SavedMovieScreen(
//                navController = navController,
//                movieId = it.arguments?.getInt(Args.MOVIE_ID.arg) ?: -1
//            )
//        }

    }
}

//fun NavHostController.navToSearchedMovie(id: Int) {
//    navigate("${AppScreen.SearchedMovie.route}/$id")
//}
//
//fun NavHostController.navToSavedMovie(id: Int) {
//    navigate("${AppScreen.SavedMovie.route}/$id")
//}