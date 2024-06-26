package com.example.e_alert

import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavBar (navController : NavHostController) {
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val isMainScreen = NavItems.any { it.route == currentDestination?.route }

    if (isMainScreen) {
        NavigationBar(
            containerColor = colorScheme.surface,
            tonalElevation = 3.dp
        ) {
            NavItems.forEach { item ->
                NavigationBarItem(
                    selected = currentDestination?.hierarchy
                        ?.any { it.route == item.route } == true,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                        }
                    },
                    label = { Text(text = item.title) },
                    icon = {
                        BadgedBox(
                            badge = {
                                if (item.badgeCount != null) {
                                    Badge {
                                        Text(text = item.badgeCount.toString())
                                    }
                                }
                            }
                        ) {
                            Icon(
                                painterResource(
                                    id = if (currentDestination?.hierarchy
                                            ?.any { it.route == item.route } == true
                                    )
                                        item.selectedIcon
                                    else item.unselectedIcon),
                                contentDescription = item.title
                            )
                        }
                    }
                )
            }
        } //NavigationBar
    } //if statement
} //BottomNavBar