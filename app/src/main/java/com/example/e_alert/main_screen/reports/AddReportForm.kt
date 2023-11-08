package com.example.e_alert.main_screen.reports

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddPhotoAlternate
import androidx.compose.material.icons.rounded.MyLocation
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.e_alert.baranggayList
import com.example.e_alert.navigation.MainScreen
import com.example.e_alert.navigation.Navigation
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@SuppressLint("RememberReturnType")
@Composable
fun AddReportForm(addReportFormViewModel: AddReportFormViewModel? = null,
      navController : NavHostController) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DetailsSection(addReportFormViewModel)

        Spacer(modifier = Modifier.height(24.dp))

        ReportTypeSection(addReportFormViewModel)

        Divider(
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        LocationSection(addReportFormViewModel)

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedButton(
            modifier =Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            onClick = { /*TODO*/ }
        ) { Text(text = "Cancel") }

        Button(
            modifier =Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            onClick = {
                addReportFormViewModel?.createPost()
                navController.navigate(Navigation.REPORTS_PAGE) {
                    launchSingleTop = true
                    popUpTo(MainScreen.ReportsPage.route)
                }
            }
        ) { Text(text = "Submit") }
    }
} //AddReportForm()

@Composable
fun DetailsSection(addReportFormViewModel: AddReportFormViewModel? = null) {
    Column {
        var description by remember { mutableStateOf("") }

        Text(
            style = MaterialTheme.typography.titleMedium,
            text = "Details"
        )

        OutlinedTextField(
            value = description,
            onValueChange = { newDescription ->
                addReportFormViewModel?.onDescriptionFieldChange(newDescription)
                description = newDescription
            },
            label = { Text(text = "Description") },
            maxLines = 4,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small
        )

        SelectPhoto()
    } //Column
} //DetailsSection

@Composable
fun ReportTypeSection(addReportFormViewModel: AddReportFormViewModel? = null) {
    Column {
        Text(
            style = MaterialTheme.typography.titleMedium,
            text = "Report Type"
        )
        RadioButtons(addReportFormViewModel)
    }
} //ReportTypeSection()

@Composable
fun LocationSection(addReportFormViewModel: AddReportFormViewModel? = null) {
    Column {
        var streetText by remember { mutableStateOf("") }

        LocationOnMap()

        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                style = MaterialTheme.typography.titleMedium,
                text = "Location"
            )

            TextButton(
                shape = MaterialTheme.shapes.small,
                onClick = { /*TODO: Get Current Location coordinates and equivalent address*/ },
                colors = ButtonDefaults.buttonColors(contentColor = colorScheme.tertiary)
            ) {
                Text(text = "Use my current location")
                Icon(
                    imageVector = Icons.Rounded.MyLocation,
                    contentDescription = "Use my current location"
                )
            }
        }
        OutlinedTextField(
            value = streetText,
            onValueChange = { newStreet ->
                addReportFormViewModel?.onStreetFieldChange(newStreet)
                streetText = newStreet
            },
            label = { Text(text = "Street") },
            maxLines = 2,
            modifier = Modifier
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
        )

        BaranggayDropdownMenu(addReportFormViewModel)
    } //Column
} //LocationSection()

@Composable
fun LocationOnMap () {
    val nagaCity = LatLng(13.621775, 123.194824)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(nagaCity, 14f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = cameraPositionState.position.target)
        )
    }
}

@Composable
fun SelectPhoto() {
    var selectedImageUris by rememberSaveable {
        mutableStateOf<List<Uri>>(emptyList())
    }

    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris -> selectedImageUris = uris}
    )

    LazyColumn (
        modifier = Modifier.height(64.dp),
        state = rememberLazyListState()
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                FilledTonalButton(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.small,
                    onClick = {
                        multiplePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.AddPhotoAlternate,
                        contentDescription = null)
                    Text(text = "Add photos")
                }
            }
        }

        items(selectedImageUris) { uri ->
            AsyncImage(
                model = uri,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }
    } //LazyColumn
} //SelectPhoto()

data class ToggleableInfo(
    val isChecked: Boolean,
    val text: String
)

@Composable
private fun RadioButtons(addReportFormViewModel: AddReportFormViewModel? = null) {
    val radioButtons = remember {
        mutableStateListOf(
            ToggleableInfo(
                isChecked = false,
                text = "Flood"
            ),
            ToggleableInfo(
                isChecked = false,
                text = "Road Accident"
            )
        )
    } //remember

    Row (
        modifier = Modifier.fillMaxWidth()
    ) {
        radioButtons.forEach { info ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .clickable {
                        radioButtons.replaceAll {
                            it.copy(
                                isChecked = it.text == info.text
                            )
                        }
                        addReportFormViewModel?.onHazardTypeToggleChange(info.text)
                    }
            ) {
                RadioButton(
                    selected = info.isChecked,
                    onClick = {
                        radioButtons.replaceAll{
                            it.copy(
                                isChecked = it.text == info.text
                            )
                        }
                        addReportFormViewModel?.onHazardTypeToggleChange(info.text)
                    }
                )
                Text(text = info.text)
            }
        }
    } //Row
} //RadioButtons()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaranggayDropdownMenu(addReportFormViewModel : AddReportFormViewModel? = null) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    var baranggay by remember {
        mutableStateOf("")
    }

    ExposedDropdownMenuBox(
        expanded = isExpanded ,
        onExpandedChange = { isExpanded = it }
    ) {
        OutlinedTextField(
            value = baranggay,
            onValueChange = { addReportFormViewModel?.onSelectBaranggay(baranggay) },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults
                    .TrailingIcon(expanded = isExpanded)
            },
            label = { Text(text = "Baranggay") },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.small
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            baranggayList.forEach { baranggayItem ->
                DropdownMenuItem(
                    text = { Text(text = baranggayItem) },
                    onClick = {
                        baranggay = baranggayItem
                        addReportFormViewModel?.onSelectBaranggay(baranggay)
                        isExpanded = false
                    }
                )
            }
        } //ExposedDropdownMenu
    } //ExposedDropdownMenuBox
}