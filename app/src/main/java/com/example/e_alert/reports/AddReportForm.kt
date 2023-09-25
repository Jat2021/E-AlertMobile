package com.example.e_alert.reports


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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.test.espresso.base.Default


@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Form(){
    Column( modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Column {
            var description by remember { mutableStateOf("") }

            Text(
                style = MaterialTheme.typography.titleLarge,
                text = "Details")

            OutlinedTextField(
                value = description,
                onValueChange = { newDescription ->
                    description = newDescription
                },
                label = {
                    Text(
                        text = "Description")
                },
                maxLines = 4,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Row {
                RadioButtons()
            }
            SelectPhoto()
        }
        Spacer(modifier = Modifier.height(24.dp))

        Column {
            var street by remember { mutableStateOf("") }
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    style = MaterialTheme.typography.titleLarge,
                    text = "Location")
                Row (verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        style = MaterialTheme.typography.bodyMedium,
                        text = "Use Current Location")
                    IconButton(onClick = {/*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "currentLoc"
                        )
                    }
                }
            }
            OutlinedTextField(
                value = street,
                onValueChange = { newStreet ->
                    street = newStreet
                },
                label = {
                    Text(text = "Street")
                },
                maxLines = 2,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Row {
                LocDropdown()
            }
        }
        Button(
            modifier =Modifier.fillMaxWidth(),
            onClick = { /*TODO*/ }
        ) {
            Text(text = "Done")
        }
        Button(
            modifier =Modifier.fillMaxWidth(),
            onClick = { /*TODO*/ }
        ) {
            Text(text = "Cancel")
        }
    }
}

@Composable
fun SelectPhoto() {
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri = uri}
    )

    LazyColumn{
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ){
                Button(
                    modifier =Modifier.fillMaxWidth(),
                    onClick = {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                ) {
                    Text(text = "Pick one photo")
                }
            }
        }
        item {
            AsyncImage(model = selectedImageUri,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }

    }
}

data class ToggleableInfo(
    val isChecked: Boolean,
    val text: String
)


@Composable
private fun RadioButtons() {
    val radioButtons = remember {
        mutableStateListOf(
            ToggleableInfo(
                isChecked = true,
                text = "Flood"
            ),
            ToggleableInfo(
                isChecked = false,
                text = "Road Accident"
            )
        )
    }

    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        radioButtons.forEachIndexed{ index, info ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable {
                        radioButtons.replaceAll {
                            it.copy(
                                isChecked = it.text == info.text
                            )
                        }
                    }
                    .padding(end = 16.dp)
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
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocDropdown(){
    var isExpanded by remember {
        mutableStateOf(false)
    }

    var location by remember {
        mutableStateOf("")
    }

    ExposedDropdownMenuBox(
        expanded = isExpanded ,
        onExpandedChange = {isExpanded = it},

        ) {
        OutlinedTextField(
            value = location,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            label = {
                Text(text = "Barangay")
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
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
                    location = "Abella"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Bagumbayan Norte")
                },
                onClick = {
                    location = "Bagumbayan Norte"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Bagumbayan Sur")
                },
                onClick = {
                    location = "Bagumbayan Sur"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Balatas")
                },
                onClick = {
                    location = "Balatas"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Calauag")
                },
                onClick = {
                    location = "Calauag"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Cararayan")
                },
                onClick = {
                    location = "Cararayan"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Carolina")
                },
                onClick = {
                    location = "Carolina"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Concepcion Grande")
                },
                onClick = {
                    location = "Concepcion Grande"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Concepcion Pequeña")
                },
                onClick = {
                    location = "Concepcion Pequeña"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Dayangdang")
                },
                onClick = {
                    location = "Dayangdang"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Del Rosario")
                },
                onClick = {
                    location = "Del Rosario"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Dinaga")
                },
                onClick = {
                    location = "Dinaga"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Igualdad Interior")
                },
                onClick = {
                    location = "Igualdad Interior"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Lerma")
                },
                onClick = {
                    location = "Lerma"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Liboton")
                },
                onClick = {
                    location = "Liboton"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Mabolo")
                },
                onClick = {
                    location = "Mabolo"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Pacol")
                },
                onClick = {
                    location = "Pacol"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Panicuason")
                },
                onClick = {
                    location = "Panicuason"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Peñafrancia")
                },
                onClick = {
                    location = "Peñafrancia"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Sabang")
                },
                onClick = {
                    location = "Sabang"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "San Felipe")
                },
                onClick = {
                    location = "San Felipe"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "San Francisco")
                },
                onClick = {
                    location = "San Francisco"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "San Isidro")
                },
                onClick = {
                    location = "San Isidro"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Santa Cruz")
                },
                onClick = {
                    location = "Santa Cruz"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Tabuco")
                },
                onClick = {
                    location = "Tabuco"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Tinago")
                },
                onClick = {
                    location = "Tinago"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Triangulo")
                },
                onClick = {
                    location = "Triangulo"
                    isExpanded = false
                }
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    TopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors
            (titleContentColor = MaterialTheme.colorScheme.primary),
        title = {
            Text(
                style = MaterialTheme.typography.displaySmall,
                text = "Add Report")
        },
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

/**************[ PREVIEW ]****************************/
/*

@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
    RepFormTheme {
        Form()
    }
}

*/