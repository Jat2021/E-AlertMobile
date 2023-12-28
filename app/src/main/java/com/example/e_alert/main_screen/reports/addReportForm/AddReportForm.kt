package com.example.e_alert.main_screen.reports.addReportForm

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.e_alert.main_screen.reports.addReportForm.detailsSection.DetailsSection
import com.example.e_alert.main_screen.reports.addReportForm.locationSection.LocationSection
import com.example.e_alert.navigation.MainScreen
import com.example.e_alert.navigation.Navigation
import com.example.e_alert.shared_viewModel.SharedViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.rememberCameraPositionState

@SuppressLint("RememberReturnType")
@Composable
fun AddReportForm(
    addReportFormViewModel: AddReportFormViewModel,
    sharedViewModel: SharedViewModel,
    navController : NavHostController
) {
    val pinnedLocation = addReportFormViewModel.pinnedLocationState

    addReportFormViewModel.cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(pinnedLocation, 14f)
    }

    LaunchedEffect(addReportFormViewModel.cameraPositionState.isMoving) {
        if(addReportFormViewModel.cameraPositionState.isMoving)
            addReportFormViewModel.isScrollEnabled = true
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .verticalScroll(rememberScrollState(), addReportFormViewModel.isScrollEnabled),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DetailsSection(addReportFormViewModel)

        Spacer(modifier = Modifier.height(16.dp))

        Divider(thickness = 1.dp)

        Spacer(modifier = Modifier.height(16.dp))

        LocationSection(
            addReportFormViewModel,
            sharedViewModel.getUserCurrentLocation(context = LocalContext.current)
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            shape = shapes.small,
            onClick = { navController.navigate(MainScreen.ReportsPage.route) }
        ) { Text(text = "Cancel") }

        val context = LocalContext.current

        LaunchedEffect(addReportFormViewModel.createPostState.value) {
            if(addReportFormViewModel.createPostState.value is CreatePostState.Successful) {
                Toast.makeText(context, addReportFormViewModel.createPostState.value.message,
                    Toast.LENGTH_LONG).show()

                navController.navigate(Navigation.REPORTS_PAGE) {
                    launchSingleTop = true
                    popUpTo(MainScreen.ReportsPage.route)
                }
            }

            if (addReportFormViewModel.createPostState.value is CreatePostState.Failure) {
                Toast.makeText(context, addReportFormViewModel.createPostState.value.message,
                    Toast.LENGTH_LONG).show()
            }
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = shapes.small,
            onClick = {
                addReportFormViewModel.createPostState.value = CreatePostState.Loading
                addReportFormViewModel.createPost()
            } //onClick
        ) {
            if (addReportFormViewModel.createPostState.value == CreatePostState.Loading)
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = colorScheme.onPrimary,
                    strokeCap = StrokeCap.Round
                )
            else
                Text(text = "Submit")
        } //Button
    }
} //AddReportForm()