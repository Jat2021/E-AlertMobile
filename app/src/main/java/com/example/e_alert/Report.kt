package com.example.e_alert

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.rounded.ThumbDown
import androidx.compose.material.icons.rounded.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class User(
    val name : String,
    @DrawableRes val profilePhoto : Int,
)

//@Preview
@Composable
fun PreviewHeader () {
    Header(user = User(
        "Justin Glen P. Vasquez",
        profilePhoto = R.drawable.ic_launcher_foreground),
        timePosted = "2m ago"
    )
}

@Preview
@Composable
fun PreviewReport () {
    Report(
        modifier = Modifier,
        user = User(
            name = "Justin Glen Vasquez",
            profilePhoto = R.drawable.ic_launcher_foreground
        ),
        timestamp = "2m ago",
        reportDescription = "Baha na po dito sa may Sta. Cruz, Ateneo Gate",
        reportPhotos = R.drawable.ic_launcher_background,
        reportType = "Flood"
    )
}


@Composable
fun Report (
    modifier : Modifier,
    user: User,
    timestamp : String,
    @DrawableRes reportPhotos : Int,
    reportDescription : String = "",
    reportType : String,
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
        Column (Modifier.fillMaxWidth()) {
            Header(user = user, timePosted = timestamp)
            ReportPhotos(image = reportPhotos)

            Spacer(modifier = Modifier.height(16.dp))
            ReportDescription(text = reportDescription)

            Spacer(modifier = Modifier.height(16.dp))
            NumberOfLikesAndDislikes(
                likes = numberOfLikes,
                dislikes = numberOfDislikes
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
            LikeAndDislikeButtons()
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

@Composable
fun ReportPhotos (@DrawableRes image : Int) {
    //TODO: Includes photos coming from the the user (from DB)
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        painter = painterResource(id = image),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}

@Composable
fun ReportDescription (text : String) {
    Column (Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 16.sp,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun NumberOfLikesAndDislikes (likes : Int, dislikes : Int) {
    Row (Modifier.padding(horizontal = 16.dp)) {
        Row {//Likes
            Icon(
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Rounded.ThumbUp,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                color = MaterialTheme.colorScheme.outline,
                text = likes.toString()
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Row {//Dislikes
            Icon(
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Rounded.ThumbDown,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                color = MaterialTheme.colorScheme.outline,
                text = dislikes.toString()
            )
        }
    }
}

@Composable
fun LikeAndDislikeButtons () {
    Row (
        Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(horizontal = 16.dp)
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