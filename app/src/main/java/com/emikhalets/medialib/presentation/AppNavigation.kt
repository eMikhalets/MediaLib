package com.emikhalets.medialib.presentation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.emikhalets.medialib.R
import com.emikhalets.medialib.presentation.screens.books.BooksScreen
import com.emikhalets.medialib.presentation.screens.movies.MovieDetailsScreen
import com.emikhalets.medialib.presentation.screens.movies.MoviesScreen
import com.emikhalets.medialib.presentation.screens.music.MusicScreen
import com.emikhalets.medialib.presentation.screens.serials.SerialDetailsScreen
import com.emikhalets.medialib.presentation.screens.serials.SerialsScreen

sealed class AppScreen(val route: String, @StringRes val titleRes: Int) {

    object Movies : AppScreen("movies", R.string.screen_name_movies)
    object Serials : AppScreen("serials", R.string.screen_name_serials)
    object Books : AppScreen("books", R.string.screen_name_books)
    object Music : AppScreen("music", R.string.screen_name_music)

    object MovieDetails : AppScreen("movie_details", R.string.screen_name_movies)
    object SerialDetails : AppScreen("serial_details", R.string.screen_name_serials)
    object BookDetails : AppScreen("book_details", R.string.screen_name_books)
    object MusicDetails : AppScreen("music_details", R.string.screen_name_music)

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

        fun isShowDrawer(entry: NavBackStackEntry?): Boolean {
            val route = entry?.destination?.route
            return route == Movies.route ||
                    route == Serials.route ||
                    route == Books.route ||
                    route == Music.route
        }
    }
}

object NavRoutes {
    val MOVIE_ROUTE = "${AppScreen.MovieDetails.route}/{${NavArgs.MOVIE_ID}}"
    val MOVIE_ARGS = listOf(navArgument(NavArgs.MOVIE_ID) { type = NavType.IntType })

    val SERIAL_ROUTE = "${AppScreen.SerialDetails.route}/{${NavArgs.SERIAL_ID}}"
    val SERIAL_ARGS = listOf(navArgument(NavArgs.SERIAL_ID) { type = NavType.IntType })

    val BOOK_ROUTE = "${AppScreen.BookDetails.route}/{${NavArgs.BOOK_ID}}"
    val BOOK_ARGS = listOf(navArgument(NavArgs.BOOK_ID) { type = NavType.IntType })

    val MUSIC_ROUTE = "${AppScreen.MusicDetails.route}/{${NavArgs.MUSIC_ID}}"
    val MUSIC_ARGS = listOf(navArgument(NavArgs.MUSIC_ID) { type = NavType.IntType })
}

object NavArgs {
    const val MOVIE_ID = "nav_arg_movie_id"
    const val SERIAL_ID = "nav_arg_serial_id"
    const val BOOK_ID = "nav_arg_book_id"
    const val MUSIC_ID = "nav_arg_music_id"
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

        composable(NavRoutes.MOVIE_ROUTE, NavRoutes.MOVIE_ARGS) {
            MovieDetailsScreen(
                navController = navController,
                movieId = it.arguments?.getInt(NavArgs.MOVIE_ID) ?: -1
            )
        }

        composable(NavRoutes.SERIAL_ROUTE, NavRoutes.SERIAL_ARGS) {
            SerialDetailsScreen(
                navController = navController,
                serialId = it.arguments?.getInt(NavArgs.SERIAL_ID) ?: -1
            )
        }

        composable(NavRoutes.BOOK_ROUTE, NavRoutes.BOOK_ARGS) {
//            BookDetailsScreen(
//                navController = navController,
//                bookId = it.arguments?.getInt(NavArgs.BOOK_ID) ?: -1
//            )
        }

        composable(NavRoutes.MUSIC_ROUTE, NavRoutes.MUSIC_ARGS) {
//            MusicDetailsScreen(
//                navController = navController,
//                musicId = it.arguments?.getInt(NavArgs.MUSIC_ID) ?: -1
//            )
        }
    }
}

fun NavHostController.navToMovieDetails(id: Int) {
    navigate("${AppScreen.MovieDetails.route}/$id")
}

fun NavHostController.navToSerialDetails(id: Int) {
    navigate("${AppScreen.SerialDetails.route}/$id")
}

fun NavHostController.navToBookDetails(id: Int) {
    navigate("${AppScreen.BookDetails.route}/$id")
}

fun NavHostController.navToMusicDetails(id: Int) {
    navigate("${AppScreen.MusicDetails.route}/$id")
}