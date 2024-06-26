package com.example.e_alert.main_screen.reports

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.ThumbDown
import androidx.compose.material.icons.rounded.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.e_alert.main_screen.reports.addReportForm.detailsSection.ReportStatusTag
import com.example.e_alert.main_screen.reports.addReportForm.detailsSection.ReportTypeLabel
import com.example.e_alert.main_screen.reports.addReportForm.detailsSection.ReportVehicleTypeTag
import com.example.e_alert.repository.AuthRepository
import com.example.e_alert.shared_viewModel.Location
import com.example.e_alert.shared_viewModel.ReportData
import com.example.e_alert.shared_viewModel.ReportImage
import com.example.e_alert.shared_viewModel.SharedViewModel
import com.example.e_alert.shared_viewModel.User
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat

@Composable
fun Report(
    sharedViewModel: SharedViewModel,
    data: ReportData
) {
    Card (
        modifier = Modifier.height(IntrinsicSize.Min),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surface
        )
    ) {
        Column (
            modifier = Modifier.fillMaxWidth()
        ) {
            Header(
                user = data.user,
                timePosted = data.timestamp,
                sharedViewModel = sharedViewModel
            ) { sharedViewModel.deleteReport(data.reportID) }


            ReportPhotos(images = sharedViewModel.photosFromDB.toList(), data.reportID)

            Column (Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ReportTypeLabel(data.reportType)
                    ReportStatusTag(reportStatus = data.hazardStatus)
                }

                if (data.reportType == "Road Accident") {
                    Spacer(modifier = Modifier.height(16.dp))

                    data.numberOfPersonsInvolved?.let {
                        InvolvedPersonsAndVehicles(
                            numberOfPersonsInvolved = it,
                            typesOfVehicleInvolved = data.typesOfVehicleInvolved
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                } else {
                    Spacer(modifier = Modifier.height(24.dp))
                }

                ReportLocationLabel(reportLocation = data.reportLocation)

                Spacer(modifier = Modifier.height(8.dp))

                ReportDescription(text = data.reportDescription)

                Spacer(modifier = Modifier.height(16.dp))
                LikesAndDislikes(data.numberOfLikes, data.numberOfDislikes)
            } //Column
        } //Column
    }
}

@Composable
fun Header(
    user: User,
    timePosted: Timestamp?,
    sharedViewModel: SharedViewModel,
    deleteReport: () -> Unit
) {
    Row (modifier = Modifier
        .padding(16.dp, 16.dp, 8.dp, 16.dp)
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween) {
        Row {
            Column {
                Text(
                    color = colorScheme.onBackground,
                    style = typography.titleMedium,
                    text = "${user.firstName} ${user.lastName}"
                )

                val formattedTimestamp = if (timePosted != null)
                    SimpleDateFormat("hh:mm aa", java.util.Locale.getDefault())
                        .format(timePosted.toDate()) else null

                Text(
                    color = colorScheme.onSurfaceVariant,
                    style = typography.titleSmall,
                    text = formattedTimestamp.toString()
                )
            }
        } //Row [User]

        var expanded by remember { mutableStateOf(false) }

        if (AuthRepository().getUserId() == user.userID) {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = null
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(text = "Delete") },
                        onClick = {
                            deleteReport.invoke()
                        }
                    )
                }
            }
        }
    } //Row [Wrapper]
}

@Composable
fun MoreButtonDropdown () {

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReportPhotos (images: List<ReportImage>, reportID : String) {
    val pagerState = rememberPagerState { images.size }
    Log.d("DISPLAY IMAGE", "images[index]: $images")
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .size(200.dp)
    ) {
        HorizontalPager(
            modifier = Modifier,
            state = pagerState
        ) { index ->
//            AsyncImage(
//                model = ImageRequest.Builder(LocalContext.current)
//                    .data(images[index]),
//                contentDescription = null,
//                contentScale = ContentScale.Crop
//            )
            Image(painter = rememberAsyncImagePainter
                (model = images.filter { it.reportID == reportID }[index].url), contentDescription = null)
        }

//        images.filter { it.reportID == reportID }.forEach { uri ->
//            Text(modifier = Modifier.clickable(true), text = uri)
//        }
        

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            Row {
                val pageCounter = "${ pagerState.settledPage + 1 } / ${ pagerState.pageCount }"

                Surface(
                    modifier = Modifier,
                    shape = shapes.small,
                    color = colorScheme.inverseSurface.copy(0.9f)
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp, 4.dp),
                        style = typography.labelMedium,
                        color = colorScheme.inverseOnSurface,
                        text = pageCounter
                    )
                }
            } //Row
        } //Box
    } //Box
}

@Composable
fun ReportLocationLabel (reportLocation : Location) {
    Row (verticalAlignment = Alignment.Top) {
        Icon(
            imageVector = Icons.Rounded.LocationOn,
            modifier = Modifier.size(18.dp),
            tint = colorScheme.secondary,
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            color = colorScheme.onSurfaceVariant,
            style = typography.labelMedium,
            overflow = TextOverflow.Ellipsis,
            softWrap = true,
            maxLines = 2,
            text = "${reportLocation.barangay} · ${reportLocation.streetOrLandmark}"
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InvolvedPersonsAndVehicles(
    numberOfPersonsInvolved: String,
    typesOfVehicleInvolved: List<String>?
) {
    Column (modifier = Modifier.fillMaxWidth()) {
        Text(
            style = typography.bodySmall,
            fontStyle = FontStyle.Italic,
            text = if (numberOfPersonsInvolved.isEmpty() || numberOfPersonsInvolved == "null")
                "No count of persons involved"
                    else numberOfPersonsInvolved
        )

        Spacer(modifier = Modifier.height(8.dp))
        
        FlowRow {
            typesOfVehicleInvolved?.forEach { vehicleType ->
                ReportVehicleTypeTag(vehicleType = vehicleType)
                Spacer(modifier = Modifier.width(4.dp))
            } //forEach
        } //FlowRow
    } //Column
}

@Composable
fun ReportDescription (text : String) {
    Column{
        Text(
            text = text,
            color = colorScheme.onSurface,
            fontSize = 16.sp,
            style = typography.bodyMedium
        )
    }
}

@Composable
fun LikesAndDislikesLabel (likes : Int, dislikes : Int) {
    Row {
        Row (verticalAlignment = Alignment.CenterVertically) {//Likes
            Icon(
                tint = colorScheme.onSurfaceVariant,
                modifier = Modifier.size(18.dp),
                imageVector = Icons.Rounded.ThumbUp,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                color = colorScheme.onSurfaceVariant,
                style = typography.labelMedium,
                text = likes.toString()
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Row (verticalAlignment = Alignment.CenterVertically){//Dislikes
            Icon(
                tint = colorScheme.onSurfaceVariant,
                modifier = Modifier.size(18.dp),
                imageVector = Icons.Rounded.ThumbDown,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                color = colorScheme.onSurfaceVariant,
                style = typography.labelMedium,
                text = dislikes.toString()
            )
        }
    }
}

@Composable
fun LikesAndDislikes (numberOfLikes : Int, numberOfDislikes : Int) {
    Column {
        LikesAndDislikesLabel(
            likes = numberOfLikes,
            dislikes = numberOfDislikes
        )

        Spacer(modifier = Modifier.height(8.dp))
        Divider(modifier = Modifier)

        Row (
            Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            Button(
                modifier = Modifier.weight(1f),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                enabled = true,
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = Icons.Rounded.ThumbUp,
                    contentDescription = null,
                    tint = colorScheme.onSurface,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    color = colorScheme.onSurface,
                    text = "Like"
                )
            }

            Row (Modifier.padding(4.dp)) {
                Divider(
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                )
            }

            Button(
                modifier = Modifier.weight(1f),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = Icons.Rounded.ThumbDown,
                    contentDescription = null,
                    tint = colorScheme.onSurface,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    color = colorScheme.onSurface,
                    text = "Dislike"
                )
            }
        }
    }
}