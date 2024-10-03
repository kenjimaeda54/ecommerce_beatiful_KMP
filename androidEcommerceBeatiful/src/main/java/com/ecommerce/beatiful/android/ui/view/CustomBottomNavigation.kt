package com.ecommerce.beatiful.android.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Colors
import androidx.compose.material3.Icon
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import com.ecommerce.beatiful.android.util.BottomBarScreen
import com.ecommerce.beatiful.android.util.BottomScreens


@Composable
fun CustomBottomNavigation(navHostController: NavHostController, navDestination: NavDestination) {

    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primary
    ) {
        BottomScreens.screens().forEach {
            AddItem(
                navController = navHostController,
                screen = it,
                currentDestination = navDestination
            )
        }
    }

}


@Composable
fun RowScope.AddItem(
    navController: NavController,
    screen: BottomBarScreen,
    currentDestination: NavDestination
) {
    BottomNavigationItem(
        selected = currentDestination.hierarchy.any {
            it.route == screen.route
        },
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        icon = {
            Icon(
                modifier = Modifier.size(20.dp), contentDescription = "Icon Navigation",
                painter = if(
                    currentDestination.hierarchy.any {
                        it.route == screen.route
                    }
                ) painterResource(id = screen.iconSelected) else painterResource(id = screen.iconSelectedNotSelected),
                tint =  if(
                    currentDestination.hierarchy.any {
                        it.route == screen.route
                    }
                ) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.tertiary
            )
        },
    )
}