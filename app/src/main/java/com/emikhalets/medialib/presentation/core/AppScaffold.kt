package com.emikhalets.medialib.presentation.core

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.DrawerState
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.emikhalets.medialib.R
import com.emikhalets.medialib.data.entity.support.MenuIconEntity
import com.emikhalets.medialib.presentation.AppScreen
import com.emikhalets.medialib.utils.ifNullOrEmpty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AppScaffold(
    navController: NavHostController,
    title: String? = "",
    actions: List<MenuIconEntity> = emptyList(),
    content: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val rootScreens = remember {
        listOf(AppScreen.Movies, AppScreen.Serials, AppScreen.Books, AppScreen.Music)
    }

    val isShowDrawer = AppScreen.isShowDrawer(navBackStackEntry)
    val toolbarTitle = title.ifNullOrEmpty { AppScreen.getTitle(navBackStackEntry) }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppToolbar(
                toolbarTitle,
                isShowDrawer,
                scaffoldState.drawerState,
                scope,
                navController,
                actions
            )
        },
        drawerContent = if (isShowDrawer) {
            {
                AppDrawer(
                    scaffoldState.drawerState,
                    scope,
                    navController,
                    navBackStackEntry,
                    rootScreens
                )
            }
        } else {
            null
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            content()
        }
    }
}

@Composable
private fun AppToolbar(
    title: String,
    isCurrentScreenRoot: Boolean,
    drawerState: DrawerState,
    scope: CoroutineScope,
    navController: NavHostController,
    actions: List<MenuIconEntity>,
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        elevation = 0.dp,
        navigationIcon = {
            if (isCurrentScreenRoot) {
                Icon(
                    imageVector = Icons.Rounded.Menu,
                    contentDescription = "",
                    modifier = Modifier
                        .clickable { scope.launch { drawerState.open() } }
                        .padding(20.dp, 16.dp)
                )
            } else {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "",
                    modifier = Modifier
                        .clickable { navController.popBackStack() }
                        .padding(20.dp, 16.dp)
                )
            }
        },
        actions = {
            actions.forEach { menuItem ->
                Icon(
                    imageVector = menuItem.icon,
                    contentDescription = "",
                    modifier = Modifier
                        .clickable { menuItem.onClick() }
                        .padding(10.dp, 16.dp)
                )
            }
        }
    )
}

@Composable
private fun AppDrawer(
    drawerState: DrawerState,
    scope: CoroutineScope,
    navController: NavHostController,
    navBackStackEntry: NavBackStackEntry?,
    rootScreens: List<AppScreen>,
) {
    Text(
        text = stringResource(R.string.app_name),
        fontSize = 24.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(16.dp)
    )
    Divider()
    rootScreens.forEach { screen ->
        Text(
            text = stringResource(screen.titleRes),
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (navBackStackEntry?.destination?.route == screen.route) {
                        MaterialTheme.colors.primary.copy(alpha = 0.2f)
                    } else {
                        Color.Transparent
                    }
                )
                .clickable {
                    if (navBackStackEntry?.destination?.route != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                    scope.launch { drawerState.close() }
                }
                .padding(16.dp)
        )
    }
}