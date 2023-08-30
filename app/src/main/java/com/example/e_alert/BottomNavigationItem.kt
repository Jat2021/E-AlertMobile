package com.example.e_alert

data class BottomNavigationItem(
    val title : String,
    val selectedIcon : Int,
    val unselectedIcon : Int,
    val badgeCount : Int? = null
)

val NavItems = listOf(
    BottomNavigationItem(
        title = "Home",
        selectedIcon = R.drawable.home_icon_filled_24dp,
        unselectedIcon = R.drawable.home_icon_outlined_24dp,
        badgeCount = null
    ),
    BottomNavigationItem(
        title = "Reports",
        selectedIcon = R.drawable.reports_icon_filled_24dp,
        unselectedIcon = R.drawable.reports_icon_outlined_24dp,
        badgeCount = null
    ),
    BottomNavigationItem(
        title = "Routes",
        selectedIcon = R.drawable.route_icon_filled_24dp,
        unselectedIcon = R.drawable.route_icon_outlined_24dp,
        badgeCount = null
    )
)
