package com.example.e_alert.main_screen.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BrokenImage
import androidx.compose.material.icons.rounded.CarCrash
import androidx.compose.material.icons.rounded.Flood
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.e_alert.R
import com.example.e_alert.repository.AuthRepository
import com.example.e_alert.topbar.profile_page_topbar.ProfilePageTopBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfilePage(
    profilePageViewModel : ProfilePageViewModel,
    onNavigateBackToMain : () -> Unit
) {
    Scaffold(
        topBar = { ProfilePageTopBar(profilePageViewModel, onNavigateBackToMain) }
    ) { paddingValues ->
        Column (modifier = Modifier
            .padding(paddingValues)
            .padding(vertical = 16.dp)
            .scrollable(rememberScrollState(), Orientation.Vertical, enabled = true)
        ) {
            ProfilePageHeader(
                firstName = profilePageViewModel.userProfileState.firstName,
                lastName = profilePageViewModel.userProfileState.lastName,
                street = profilePageViewModel.userProfileState.street,
                barangay = profilePageViewModel.userProfileState.barangay,
                email = profilePageViewModel.userProfileState.email,
                phoneNumber = profilePageViewModel.userProfileState.phoneNumber
            )

            Divider(modifier = Modifier
                .padding(vertical = 24.dp)
                .padding(horizontal = 16.dp))

            ReportsPosted(profilePageViewModel.reportsList())
        }
    } //Scaffold
} //ProfilePage

@Composable
fun ProfilePageHeader (
    firstName : String,
    lastName : String,
    street : String,
    barangay : String,
    email : String,
    phoneNumber : String
) {
    Column (modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            text = "$firstName $lastName"
        )
        Text(
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.outlineVariant,
            text = email
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            style = MaterialTheme.typography.bodyLarge,
            text = "Date Created: ${AuthRepository().getDateCreated()}"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            style = MaterialTheme.typography.bodyLarge,
            text = if (street == "-not set-" || barangay == "-not set-") "-Address not set-"
                    else "$street, $barangay"
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            style = MaterialTheme.typography.bodyLarge,
            fontStyle = FontStyle.Italic,
            text = if (phoneNumber.isEmpty()) "-Phone Number not set-"
            else "(+63) $phoneNumber"
        )
    }
} //ProfilePageHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfile (
    profilePageViewModel: ProfilePageViewModel,
    firstName : String,
    lastName : String,
    street : String,
    barangay : String,
    phoneNumber : String
) {
    //First Name
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = MaterialTheme.shapes.small,
        enabled = profilePageViewModel.editModeState(),
        value = firstName,
        onValueChange = { /*loginViewModel.onFirstNameChange(it)*/ },
        label = { Text(text = "First Name") },
        supportingText = { Text(text = "*Required") }
    )

    //Last Name
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = MaterialTheme.shapes.small,
        enabled = profilePageViewModel.editModeState(),
        value = lastName,
        onValueChange = { /*loginViewModel.onLastNameChange(it)*/ },
        label = { Text(text = "Last Name") },
        supportingText = { Text(text = "*Required") }
    )

    //Street
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = MaterialTheme.shapes.small,
        enabled = profilePageViewModel.editModeState(),
        value = street,
        onValueChange = { /*loginViewModel.onStreetChange(it)*/ },
        label = { Text(text = "Street") }
    )

    var isExpanded by remember {
        mutableStateOf(false)
    }

    var barangaySelect by remember {
        mutableStateOf("")
    }

    //Baranggay
    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = it }
    ) {
        OutlinedTextField(
            value = barangay,
            onValueChange = { /*loginViewModel.onBarangayChange(barangay)*/ },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults
                    .TrailingIcon(expanded = isExpanded)
            },
            label = { Text(text = "Barangay") },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.small
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            profilePageViewModel.listOfBarangayState.forEach { barangayItem ->
                DropdownMenuItem(
                    text = { Text(text = barangayItem) },
                    onClick = {
                        barangaySelect = barangayItem
                        /*loginViewModel.onBarangayChange(barangay)*/
                        isExpanded = false
                    }
                )
            }
        }
    } //ExposedDropdownMenuBox

    //Phone Number
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = MaterialTheme.shapes.small,
        enabled = profilePageViewModel.editModeState(),
        value = phoneNumber,
        onValueChange = { /*loginViewModel.onPhoneNumberChange(it)*/ },
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Phone,
                contentDescription = null
            )
        },
        prefix = { Text(text = "(+63)") },
        label = { Text(text = "Phone") }
    )

    Spacer(modifier = Modifier.height(32.dp))

    Button(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        onClick = {  }
    ) {
        if (profilePageViewModel.profileChangeState == ProfileChangeState.SavingChanges) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colorScheme.onPrimary,
                strokeCap = StrokeCap.Round
            )
        } else {
            Text(text = "Save Changes")
        }
    }
} //EditProfile

@Composable
fun ReportsPosted (reportsList: List<Report>) {
    Row (modifier = Modifier.padding(horizontal = 16.dp)) {
        Icon(
            painter = painterResource(id = R.drawable.reports_icon_filled_24dp),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            style = MaterialTheme.typography.titleMedium,
            text = "My Reports Posted")
    }

    Spacer(modifier = Modifier
        .height(16.dp)
        .padding(16.dp))

    
    if (reportsList.isNotEmpty()) {
        reportsList.forEach { report ->
            ReportsListItem(report)
        }
    } else
        Box (
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                text = "No reports posted today"
            )
        }
}

@Composable
fun ReportsListItem (report : Report) {
    val icon = when (report.reportType) {
        "Flood" -> Icons.Rounded.Flood
        "Road Accident" -> Icons.Rounded.CarCrash
        else -> { Icons.Rounded.BrokenImage }
    }

    val reportID = report.reportID
    val streetOrLandmark = report.streetOrLandmark
    val barangay = report.barangay

    ListItem(
        overlineContent = { Text(reportID) },
        headlineContent = { Text("$streetOrLandmark, $barangay") },
        leadingContent = {
            Icon(
                imageVector = icon,
                contentDescription = null,
            )
        }
    )
} //fun ReportListItem