package com.example.e_alert.main_screen.reports

import androidx.lifecycle.ViewModel
import com.example.e_alert.shared_viewModel.ReportData
import com.example.e_alert.shared_viewModel.SharedViewModel

class ReportsPageViewModel(sharedViewModel : SharedViewModel) : ViewModel() {
    val reportsListState = sharedViewModel.reportsListState

    fun getReportsList() : MutableList<ReportData> {
        return reportsListState
    }
} //class ReportsPageViewModel