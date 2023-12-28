package com.example.e_alert.topbar.main_screen_topbar

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.e_alert.navigation.MainScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun TopBarAccountMenu (navController: NavController) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(
            colors = IconButtonDefaults
                .iconButtonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            onClick = { expanded = true }
        ) {
            Icon(
                imageVector = Icons.Rounded.AccountCircle,
                contentDescription = "Profile"
            )
        } //IconButton

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text(text = "Profile") },
                onClick = { navController.navigate(MainScreen.ProfilePage.route) }
            )
            DropdownMenuItem(
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Logout,
                        contentDescription = "Profile"
                    ) },
                text = { Text(text = "Logout") },
                colors = MenuDefaults.itemColors(
                    leadingIconColor = colorScheme.error,
                    textColor = colorScheme.error
                ),
                onClick = { FirebaseAuth.getInstance().signOut() }
            )
        }
    } //Box
} //TopBarAccountMenu