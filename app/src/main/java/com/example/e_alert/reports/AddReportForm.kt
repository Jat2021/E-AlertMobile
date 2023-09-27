package com.example.e_alert.reports

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddPhotoAlternate
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.MyLocation
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.MaterialTheme.shapes
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.e_alert.ui.theme.EAlertTheme

/**************[ PREVIEW ]****************************/

@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
    EAlertTheme {
        Scaffold (
            topBar = { TopBarReports() }
        ) { paddingValues ->
            Column (modifier = Modifier.padding(paddingValues)) {
                AddReport()
            }
        }
    }
}

/*****************************************************/

@SuppressLint("RememberReturnType")
@Composable
fun AddReport() {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DetailsSection()

        Spacer(modifier = Modifier.height(24.dp))

        ReportTypeSection()

        Divider(
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        LocationSection()

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedButton(
            modifier =Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            onClick = { /*TODO*/ }
        ) { Text(text = "Cancel") }

        Button(
            modifier =Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            onClick = { /*TODO*/ }
        ) { Text(text = "Submit") }
    }
} //AddReport()

@Composable
fun DetailsSection() {
    Column {
        var description by remember { mutableStateOf("") }

        Text(
            style = MaterialTheme.typography.titleMedium,
            text = "Details"
        )

        OutlinedTextField(
            value = description,
            onValueChange = { newDescription ->
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
fun ReportTypeSection() {
    Column {
        Text(
            style = MaterialTheme.typography.titleMedium,
            text = "Report Type"
        )
        RadioButtons()
    }
} //ReportTypeSection()

@Composable
fun LocationSection() {
    Column {
        var streetText by remember { mutableStateOf("") }

        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                style = MaterialTheme.typography.titleMedium,
                text = "Location")

            FilledTonalIconButton(
                shape = MaterialTheme.shapes.small,
                onClick = {/*TODO*/ },
                colors = IconButtonDefaults
                    .filledIconButtonColors(
                        containerColor = colorScheme.tertiaryContainer)
            ) {
                Icon(
                    imageVector = Icons.Rounded.MyLocation,
                    contentDescription = "Use my current location"
                )
            }
        }
        OutlinedTextField(
            value = streetText,
            onValueChange = { newStreet ->
                streetText = newStreet
            },
            label = { Text(text = "Street") },
            maxLines = 2,
            modifier = Modifier
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
        )

        BaranggayDropdownMenu()
    } //Column
} //LocationSection()

@Composable
fun SelectPhoto() {
    var selectedImageUris by rememberSaveable {
        mutableStateOf<List<Uri>>(emptyList())
    }

    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris -> selectedImageUris = uris}
    )

    LazyColumn {
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
private fun RadioButtons() {
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
                    }
                )
                Text(text = info.text )
            }
        }
    } //Row
} //RadioButtons()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaranggayDropdownMenu() {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    var baranggay by remember {
        mutableStateOf("")
    }

    ExposedDropdownMenuBox(
        expanded = isExpanded ,
        onExpandedChange = {isExpanded = it}
    ) {
        OutlinedTextField(
            value = baranggay,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults
                    .TrailingIcon(expanded = isExpanded)
            },
            label = { Text(text = "Baranggay") },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = "Abella")
                },
                onClick = {
                    baranggay = "Abella"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Bagumbayan Norte")
                },
                onClick = {
                    baranggay = "Bagumbayan Norte"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Bagumbayan Sur")
                },
                onClick = {
                    baranggay = "Bagumbayan Sur"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Balatas")
                },
                onClick = {
                    baranggay = "Balatas"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Calauag")
                },
                onClick = {
                    baranggay = "Calauag"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Cararayan")
                },
                onClick = {
                    baranggay = "Cararayan"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Carolina")
                },
                onClick = {
                    baranggay = "Carolina"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Concepcion Grande")
                },
                onClick = {
                    baranggay = "Concepcion Grande"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Concepcion Pequeña")
                },
                onClick = {
                    baranggay = "Concepcion Pequeña"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Dayangdang")
                },
                onClick = {
                    baranggay = "Dayangdang"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Del Rosario")
                },
                onClick = {
                    baranggay = "Del Rosario"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Dinaga")
                },
                onClick = {
                    baranggay = "Dinaga"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Igualdad Interior")
                },
                onClick = {
                    baranggay = "Igualdad Interior"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Lerma")
                },
                onClick = {
                    baranggay = "Lerma"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Liboton")
                },
                onClick = {
                    baranggay = "Liboton"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Mabolo")
                },
                onClick = {
                    baranggay = "Mabolo"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Pacol")
                },
                onClick = {
                    baranggay = "Pacol"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Panicuason")
                },
                onClick = {
                    baranggay = "Panicuason"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Peñafrancia")
                },
                onClick = {
                    baranggay = "Peñafrancia"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Sabang")
                },
                onClick = {
                    baranggay = "Sabang"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "San Felipe")
                },
                onClick = {
                    baranggay = "San Felipe"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "San Francisco")
                },
                onClick = {
                    baranggay = "San Francisco"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "San Isidro")
                },
                onClick = {
                    baranggay = "San Isidro"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Santa Cruz")
                },
                onClick = {
                    baranggay = "Santa Cruz"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Tabuco")
                },
                onClick = {
                    baranggay = "Tabuco"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Tinago")
                },
                onClick = {
                    baranggay = "Tinago"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Triangulo")
                },
                onClick = {
                    baranggay = "Triangulo"
                    isExpanded = false
                }
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarReports() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    TopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors
            (titleContentColor = MaterialTheme.colorScheme.primary),
        title = { Text(text = "Add Report") },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "cancel"
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

