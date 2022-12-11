package com.emikhalets.medialib.presentation.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.emikhalets.medialib.data.entity.support.MenuIconEntity
import com.emikhalets.medialib.presentation.AppScreen
import com.emikhalets.medialib.utils.ifNullOrEmpty

@Composable
fun AppScaffold(
    navController: NavHostController,
    title: String? = "",
    actions: List<MenuIconEntity> = emptyList(),
    content: @Composable () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val rootScreens = remember { listOf(AppScreen.Main, AppScreen.Search) }
    val isShowDrawer = AppScreen.isShowDrawer(navBackStackEntry)

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { AppToolbar(title, isShowDrawer, navController, actions) },
        bottomBar = { AppBottomBar(navController, rootScreens) },
        content = { Box(modifier = Modifier.padding(it)) { content() } }
    )
}

@Composable
private fun AppToolbar(
    title: String?,
    isCurrentScreenRoot: Boolean,
    navController: NavHostController,
    actions: List<MenuIconEntity>,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val toolbarTitle = title.ifNullOrEmpty { AppScreen.getTitle(navBackStackEntry) }

    TopAppBar(
        title = {
            Text(
                text = toolbarTitle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        elevation = 0.dp,
        navigationIcon = if (isCurrentScreenRoot) {
            null
        } else {
            {
                AppIcon(
                    imageVector = Icons.Rounded.ArrowBack,
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.padding(20.dp, 16.dp)
                )
            }
        },
        actions = {
            actions.forEach { menuItem ->
                AppIcon(
                    imageVector = menuItem.icon,
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.padding(10.dp, 16.dp)
                )
            }
        }
    )
}

@Composable
private fun AppBottomBar(
    navController: NavHostController,
    rootScreens: List<AppScreen>,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation {
        rootScreens.forEach { screen ->
            BottomNavigationItem(
                icon = { AppIcon(screen.icon) },
                label = { Text(stringResource(screen.titleRes)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}