package com.example.e_alert.main_screen.reports

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.e_alert.shared_viewModel.ReportData
import com.example.e_alert.shared_viewModel.User
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat

@Composable
fun Report (data : ReportData) {
    Card (
        modifier = Modifier.height(IntrinsicSize.Min),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surface
        )
    ) {
        Column (Modifier.fillMaxWidth()
        ) {
            Header(
                user = data.user,
                timePosted = data.timestamp
            )
            if (data.images != null) ReportPhotos(images = data.images)

            Column (Modifier.padding(
                start = 16.dp, top = 16.dp, end = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ReportTypeLabel(data.reportType)
                    ReportLocationLabel(
                        reportLocation = "${data.reportLocation.baranggay}, " +
                                data.reportLocation.street)
                }

                Spacer(modifier = Modifier.height(8.dp))
                ReportDescription(text = data.reportDescription)

                Spacer(modifier = Modifier.height(16.dp))
                LikesAndDislikes(data.numberOfLikes, data.numberOfDislikes)
            } //Column
        } //Column
    }
}

@Composable
fun Header (user: User, timePosted: Timestamp) {
    //TODO: Includes profile photo, Username (from DB)
    Row (modifier = Modifier
        .padding(16.dp, 16.dp, 8.dp, 16.dp)
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween) {
        Row {
            AsyncImage(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(color = colorScheme.secondary),
                model = user.profilePhoto,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    color = colorScheme.onBackground,
                    style = typography.titleMedium,
                    text = "${user.firstName} ${user.lastName}"
                )

                val formattedTimestamp = SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
                    .format(timePosted.toDate())

                Text(
                    color = colorScheme.onSurfaceVariant,
                    style = typography.titleSmall,
                    text = formattedTimestamp)
            }
        } //Row [User]

        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = null
            )
        }
    } //Row [Wrapper]
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReportPhotos (images : List<Uri>) {
    //TODO: Includes photos coming from the the user (from DB)

    val pagerState = rememberPagerState { images.size }

    Box(modifier = Modifier
        .fillMaxWidth()
        .size(200.dp)
    ) {
        HorizontalPager(
            modifier = Modifier,
            state = pagerState
        ) { index ->
            AsyncImage(
                model = images[index],
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

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
            }
        } //Row
    } //Box
}

@Composable
fun ReportTypeLabel (reportType : String) {
    Surface (
        modifier = Modifier,
        shape = shapes.small,
        color = colorScheme.secondaryContainer,
    ) {
        Text(
            modifier = Modifier.padding(8.dp, 4.dp),
            color = colorScheme.onSecondaryContainer,
            style = typography.labelMedium,
            text = reportType.uppercase(),
        )
    }
}

@Composable
fun ReportLocationLabel (reportLocation : String) {
    Row (verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Rounded.LocationOn,
            modifier = Modifier.size(18.dp),
            tint = MaterialTheme.colorScheme.secondary,
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            color = colorScheme.onSurfaceVariant,
            style = typography.labelMedium,
            text = reportLocation
        )
    }
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
                style = MaterialTheme.typography.labelMedium,
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
                    imageVector = Icons.Rounded.ThumbUp,
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