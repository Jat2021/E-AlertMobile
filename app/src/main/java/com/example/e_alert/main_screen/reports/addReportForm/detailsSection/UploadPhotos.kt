package com.example.e_alert.main_screen.reports.addReportForm.detailsSection

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddPhotoAlternate
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.e_alert.main_screen.reports.addReportForm.AddReportFormViewModel

@SuppressLint("MutableCollectionMutableState")
@Composable
fun UploadPhotos(addReportFormViewModel: AddReportFormViewModel) {
    var selectedImageUris by rememberSaveable {
        mutableStateOf(mutableListOf<Uri>())
    }

    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris ->
            addReportFormViewModel.addPhotosToList(uris)
            selectedImageUris = addReportFormViewModel.getSelectedPhotos().toMutableList()
        }
    )

    Column {
        if (addReportFormViewModel.getSelectedPhotos().isNotEmpty()) {
            Text(
                style = MaterialTheme.typography.titleMedium,
                text = "Photos"
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        selectedImageUris.forEach { uri ->
            Box(modifier = Modifier.clip(MaterialTheme.shapes.small)) {
                AsyncImage(
                    model = uri,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillBounds
                )

                Box(
                    modifier = Modifier.fillMaxSize().padding(4.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    IconButton(
                        onClick = {
                            addReportFormViewModel.removePhotoFromTheList(uri)
                            selectedImageUris = addReportFormViewModel
                                .getSelectedPhotos().toMutableList()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.surface
                        )
                    }
                } //Box
            } //Box

            Spacer(modifier = Modifier.height(4.dp))
        } //selectedImageUris.forEach

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
                } //onClick
            ) {
                Icon(
                    imageVector = Icons.Rounded.AddPhotoAlternate,
                    contentDescription = null
                )

                Text(text = "Add photos")
            }
        } //Row
    } //Column
} //UploadPhotos()