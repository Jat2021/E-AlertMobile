package com.example.e_alert

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class User(
    val name : String,
    @DrawableRes val profilePhoto : Int,
)

@Preview
@Composable
fun PreviewReport () {
    Report(
        user = User(
            name = "Justin Glen Vasquez",
            profilePhoto = R.drawable.ic_launcher_foreground
        ),
        timestamp = "2m ago",
        reportPhotos = listOf(
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background
        ),
        reportType = "Flood",
        reportLocation = "Sta. Cruz",
        reportDescription = "Baha na po dito sa may Sta. Cruz, Ateneo Gate"
    )
}

@Composable
fun Report (
    user: User,
    timestamp : String,
    @DrawableRes reportPhotos : List<Int>,
    reportType : String,
    reportLocation : String,
    reportDescription : String = "",
    numberOfLikes : Int = 0,
    numberOfDislikes : Int = 0,
) {
    Card (
        modifier = Modifier.height(IntrinsicSize.Min),
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Column (Modifier.fillMaxWidth()
        ) {
            Header(user = user, timePosted = timestamp)
            ReportPhotos(images = reportPhotos)

            Column (Modifier.padding(
                start = 16.dp, top = 16.dp, end = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ReportTypeLabel(reportType)
                    ReportLocationLabel(reportLocation = reportLocation)
                }

                Spacer(modifier = Modifier.height(8.dp))
                ReportDescription(text = reportDescription)

                Spacer(modifier = Modifier.height(16.dp))
                LikesAndDislikes(numberOfLikes, numberOfDislikes)
            } //Column
        } //Column
    }
}

@Composable
fun Header (user : User, timePosted: String) {
    //TODO: Includes profile photo, Username (from DB)
    Row (modifier = Modifier
        .padding(16.dp, 16.dp, 8.dp, 16.dp)
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween) {
        Row {
            Image(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(color = colorResource(id = R.color.teal_700)),
                painter = painterResource(id = user.profilePhoto),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium,
                    text = user.name)
                Text(
                    color = MaterialTheme.colorScheme.outline,
                    style = MaterialTheme.typography.titleSmall,
                    text = timePosted)
            }
        } //Row [User]

        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Filled.MoreVert, contentDescription = null)
        }
    } //Row [Wrapper]
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReportPhotos (@DrawableRes images : List<Int>) {
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
            Image(
                painter = painterResource(id = images[index]),
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
                val pageCounter = "${pagerState.settledPage+1} / ${pagerState.pageCount}"

                Surface(
                    modifier = Modifier,
                    shape = MaterialTheme.shapes.small,
                    color = MaterialTheme.colorScheme.inverseSurface.copy(0.9f)
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp, 4.dp),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.inverseOnSurface,
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
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.secondary,
    ) {
        Text(
            modifier = Modifier.padding(8.dp, 4.dp),
            style = MaterialTheme.typography.labelMedium,
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
            color = MaterialTheme.colorScheme.outline,
            style = MaterialTheme.typography.labelMedium,
            text = reportLocation
        )
    }
}

@Composable
fun ReportDescription (text : String) {
    Column{
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 16.sp,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun LikesAndDislikesLabel (likes : Int, dislikes : Int) {
    Row {
        Row (verticalAlignment = Alignment.CenterVertically) {//Likes
            Icon(
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(18.dp),
                imageVector = Icons.Rounded.ThumbUp,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                color = MaterialTheme.colorScheme.outline,
                style = MaterialTheme.typography.labelMedium,
                text = likes.toString()
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Row (verticalAlignment = Alignment.CenterVertically){//Dislikes
            Icon(
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(18.dp),
                imageVector = Icons.Rounded.ThumbDown,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                color = MaterialTheme.colorScheme.outline,
                style = MaterialTheme.typography.labelMedium,
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
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    color = MaterialTheme.colorScheme.onBackground,
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
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    color = MaterialTheme.colorScheme.onBackground,
                    text = "Dislike"
                )
            }
        }
    }
}