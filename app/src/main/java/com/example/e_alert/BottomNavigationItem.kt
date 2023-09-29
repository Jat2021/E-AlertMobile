package com.example.e_alert

import com.example.e_alert.navigation.MainScreen

data class BottomNavigationItem(
    val title : String,
    val route : String,
    val selectedIcon : Int,
    val unselectedIcon : Int,
    val badgeCount : Int? = null
)

val NavItems = listOf(
    BottomNavigationItem(
        title = "Home",
        route = MainScreen.HomePage.route,
        selectedIcon = R.drawable.home_icon_filled_24dp,
        unselectedIcon = R.drawable.home_icon_outlined_24dp,
        badgeCount = null
    ),
    BottomNavigationItem(
        title = "Reports",
        route = MainScreen.ReportsPage.route,
        selectedIcon = R.drawable.reports_icon_filled_24dp,
        unselectedIcon = R.drawable.reports_icon_outlined_24dp,
        badgeCount = null
    ),
    BottomNavigationItem(
        title = "Routes",
        route = MainScreen.RoutesPage.route,
        selectedIcon = R.drawable.route_icon_filled_24dp,
        unselectedIcon = R.drawable.route_icon_outlined_24dp,
        badgeCount = null
    )
)
