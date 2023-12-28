package com.example.e_alert.topbar.profile_page_topbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.example.e_alert.main_screen.profile.ProfilePageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePageTopBar(
    profilePageViewModel: ProfilePageViewModel,
    onNavigateBackToMain: () -> Unit
) {
    TopAppBar(
        title = { Text(text = "Profile") },
        navigationIcon = {
            IconButton(onClick = { onNavigateBackToMain.invoke() }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "Close"
                )
            }
        },
        actions = {
            if (!profilePageViewModel.editModeState()) {
                IconButton(onClick = { profilePageViewModel.startEditMode() }) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = "Edit"
                    )
                }
            } else {
                IconButton(onClick = { profilePageViewModel.doneEditingMode() }) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = "Done Editing"
                    )
                }
            }
        } //actions
    ) //TopAppBar
} //ProfilePageTopBar