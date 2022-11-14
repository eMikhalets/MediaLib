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
import com.emikhalets.medialib.presentation.screens.serials.SerialsScreen

sealed class AppScreen(val route: String, @StringRes val titleRes: Int) {

    object Movies : AppScreen("movies", R.string.screen_name_movies)
    object Serials : AppScreen("serials", R.string.screen_name_serials)
    object Books : AppScreen("books", R.string.screen_name_books)
    object Music : AppScreen("music", R.string.screen_name_music)

    object MovieDetails : AppScreen("movie_details", R.string.screen_name_movies)

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
    val MOVIE_ROUTE = "${AppScreen.MovieDetails.route}/{${NavArgs.MOVIE_ID}}";
    val MOVIE_ARGS = listOf(navArgument(NavArgs.MOVIE_ID) { type = NavType.IntType });
}

object NavArgs {
    const val MOVIE_ID = "nav_arg_movie_id";
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

    }
}

fun NavHostController.navToMovieDetails(id: Int) {
    navigate("${AppScreen.MovieDetails.route}/$id")
}