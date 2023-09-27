package com.example.e_alert

data class BottomNavigationItem(
    val title : String,
    val selectedIcon : Int,
    val unselectedIcon : Int,
    val badgeCount : Int? = null
)

val NavItems = listOf(
    BottomNavigationItem(
        title = MainScreen.HomePage.route,
        selectedIcon = R.drawable.home_icon_filled_24dp,
        unselectedIcon = R.drawable.home_icon_outlined_24dp,
        badgeCount = null
    ),
    BottomNavigationItem(
        title = MainScreen.ReportsPage.route,
        selectedIcon = R.drawable.reports_icon_filled_24dp,
        unselectedIcon = R.drawable.reports_icon_outlined_24dp,
        badgeCount = null
    ),
    BottomNavigationItem(
        title = MainScreen.RoutesPage.route,
        selectedIcon = R.drawable.route_icon_filled_24dp,
        unselectedIcon = R.drawable.route_icon_outlined_24dp,
        badgeCount = null
    )
)
