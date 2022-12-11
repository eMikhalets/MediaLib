package com.emikhalets.medialib.presentation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.emikhalets.medialib.R
import com.emikhalets.medialib.presentation.screens.books.BooksScreen
import com.emikhalets.medialib.presentation.screens.main.MainScreen
import com.emikhalets.medialib.presentation.screens.movies.MovieDetailsScreen
import com.emikhalets.medialib.presentation.screens.movies.MovieEditsScreen
import com.emikhalets.medialib.presentation.screens.search.SearchMoviesScreen

sealed class AppScreen(
    val route: String,
    @StringRes val titleRes: Int,
    val icon: ImageVector? = null,
) {

    object Main : AppScreen(
        route = "main",
        titleRes = R.string.screen_name_main,
        icon = Icons.Rounded.Home
    )

    object Search : AppScreen(
        route = "search",
        titleRes = R.string.screen_name_search,
        icon = Icons.Rounded.Search
    )

    object Details : AppScreen(
        route = "details/{${NavArgs.ITEM_ID}}/{${NavArgs.ITEM_TYPE}}",
        titleRes = R.string.screen_name_details
    )

    object Edit : AppScreen(
        route = "edit/{${NavArgs.ITEM_ID}}/{${NavArgs.ITEM_TYPE}}",
        titleRes = R.string.screen_name_edit
    )

    object Movies : AppScreen("movies", R.string.screen_name_movies)
    object Serials : AppScreen("serials", R.string.screen_name_serials)
    object Books : AppScreen("books", R.string.screen_name_books)
    object Music : AppScreen("music", R.string.screen_name_music)
    object SearchMovies : AppScreen("search_movies", R.string.search_movies_title)

    object MovieEdit : AppScreen("movie_edit", R.string.screen_name_movies)
    object SerialEdit : AppScreen("serial_edit", R.string.screen_name_serials)
    object BookEdit : AppScreen("book_edit", R.string.screen_name_books)
    object MusicEdit : AppScreen("music_edit", R.string.screen_name_music)

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
                SearchMovies.route -> stringResource(SearchMovies.titleRes)
                else -> ""
            }
        }

        fun isShowDrawer(entry: NavBackStackEntry?): Boolean {
            val route = entry?.destination?.route
            return route == Main.route || route == Search.route
        }
    }
}

object NavRoutes {
    val MOVIE_ROUTE = "${AppScreen.MovieDetails.route}/{${NavArgs.MOVIE_ID}}"
    val MOVIE_EDIT_ROUTE = "${AppScreen.MovieEdit.route}/{${NavArgs.MOVIE_ID}}"
    val MOVIE_ARGS = listOf(navArgument(NavArgs.MOVIE_ID) { type = NavType.IntType })
}

object NavArgs {
    const val ITEM_ID = "nav_arg_item_id"
    const val ITEM_TYPE = "nav_arg_item_type"

    const val MOVIE_ID = "nav_arg_movie_id"
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, AppScreen.Main.route) {

        composable(AppScreen.Main.route) {
            MainScreen(navController)
        }

        composable(AppScreen.Search.route) {
            SearchMoviesScreen(navController)
        }

        composable(AppScreen.Details.route) {
            BooksScreen(navController)
        }

        composable(AppScreen.Edit.route) {
            BooksScreen(navController)
        }

        composable(NavRoutes.MOVIE_ROUTE, NavRoutes.MOVIE_ARGS) {
            MovieDetailsScreen(
                navController = navController,
                movieId = it.arguments?.getInt(NavArgs.MOVIE_ID) ?: -1
            )
        }

        composable(NavRoutes.MOVIE_EDIT_ROUTE, NavRoutes.MOVIE_ARGS) {
            MovieEditsScreen(
                navController = navController,
                movieId = it.arguments?.getInt(NavArgs.MOVIE_ID) ?: -1
            )
        }
    }
}

fun NavHostController.navToMovieDetails(id: Int) {
    navigate("${AppScreen.MovieDetails.route}/$id")
}

fun NavHostController.navToMovieEdit(id: Int?) {
    navigate("${AppScreen.MovieEdit.route}/${id ?: 0}")
}

fun NavHostController.navToSerialDetails(id: Int) {
    navigate("${AppScreen.SerialDetails.route}/$id")
}

fun NavHostController.navToSerialEdit(id: Int?) {
    navigate("${AppScreen.SerialEdit.route}/${id ?: 0}")
}

fun NavHostController.navToBookDetails(id: Int) {
    navigate("${AppScreen.BookDetails.route}/$id")
}

fun NavHostController.navToBookEdit(id: Int?) {
    navigate("${AppScreen.BookEdit.route}/${id ?: 0}")
}

fun NavHostController.navToMusicDetails(id: Int) {
    navigate("${AppScreen.MusicDetails.route}/$id")
}

fun NavHostController.navToMusicEdit(id: Int?) {
    navigate("${AppScreen.MusicEdit.route}/$id")
}