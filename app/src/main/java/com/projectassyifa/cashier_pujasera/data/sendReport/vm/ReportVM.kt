package com.projectassyifa.cashier_pujasera.data.sendReport.vm

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.projectassyifa.cashier_pujasera.data.sendReport.model.ReportModel
import com.projectassyifa.cashier_pujasera.data.sendReport.repo.ReportRepo
import javax.inject.Inject

class ReportVM @Inject constructor(var reportRepo: ReportRepo) {
    var report_server : MutableLiveData<List<ReportModel>>? = reportRepo.report_server
    var not : String = reportRepo.not_found.toString()
    fun report(reportModel: ReportModel,context : Context){
        reportRepo.report(reportModel,context)
    }
}