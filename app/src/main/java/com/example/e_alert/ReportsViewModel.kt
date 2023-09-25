package com.example.e_alert

import androidx.lifecycle.ViewModel
import com.example.e_alert.reports.ReportData
import com.example.e_alert.reports.User
import java.sql.Timestamp

class ReportsViewModel {
    fun retrieveReportsFromDB () : List<ReportData> {
        return emptyList() //TODO: query from db
    }

    fun createPost (
        description : String,
        photos : List</*TODO: Format of Photos*/>,
        reportType : String,
        baranggay : String,
        street : String,
        timestamp: Timestamp
    ) {
        /* TODO: Report entry to DB */
    }
}