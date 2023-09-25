package com.example.e_alert.reports

import androidx.annotation.DrawableRes
import com.example.e_alert.R


data class ReportData (
    val user : User,
    val timestamp : String, //TODO: This should be a proper Timestamp datatype
    @DrawableRes val images : List<Int>?,
    val reportType : String,
    val reportLocation : String,
    val reportDescription : String = "",
    val numberOfLikes : Int = 0,
    val numberOfDislikes : Int = 0
)

//TODO: this receives data from db query
fun retrieveReports () : List<ReportData> {

    /* TODO: db query here*/

    //TODO: replace the following code below with the List of each Report data from db
    return listOf(
        ReportData(
            user = User(
                name = "Justin Glen Vasquez",
                profilePhoto = R.drawable.profile_photo_placeholder_foreground
            ),
            timestamp = "2 mins. ago",
            images = listOf(
                R.drawable.flooded_area_1,
                R.drawable.flooded_area_2,
                R.drawable.flooded_area_3,
            ),
            reportType = "Flood",
            reportLocation = "Sta. Cruz",
            reportDescription = "Baha na po dito sa may Sta. Cruz, Ateneo Gate"
        ),
        ReportData(
            user = User(
                name = "Justin Glen Vasquez",
                profilePhoto = R.drawable.profile_photo_placeholder_foreground
            ),
            timestamp = "2 mins. ago",
            images = listOf(
                R.drawable.flooded_area_1,
                R.drawable.flooded_area_2,
                R.drawable.flooded_area_3,
            ),
            reportType = "Flood",
            reportLocation = "Sta. Cruz",
            reportDescription = "Baha na po dito sa may Sta. Cruz, Ateneo Gate"
        ),
        ReportData(
            user = User(
                name = "Justin Glen Vasquez",
                profilePhoto = R.drawable.profile_photo_placeholder_foreground
            ),
            timestamp = "2 mins. ago",
            images = listOf(
                R.drawable.flooded_area_1,
                R.drawable.flooded_area_2,
                R.drawable.flooded_area_3,
            ),
            reportType = "Flood",
            reportLocation = "Sta. Cruz",
            reportDescription = "Baha na po dito sa may Sta. Cruz, Ateneo Gate"
        )
    )
}