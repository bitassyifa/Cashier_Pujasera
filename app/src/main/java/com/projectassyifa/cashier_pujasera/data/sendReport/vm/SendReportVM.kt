package com.projectassyifa.cashier_pujasera.data.sendReport.vm

import android.content.Context
import com.projectassyifa.cashier_pujasera.data.sendReport.model.SendReportModel
import com.projectassyifa.cashier_pujasera.data.sendReport.repo.SendReportRepo
import javax.inject.Inject

class SendReportVM @Inject constructor(var sendReportRepo: SendReportRepo){

    var data_response = sendReportRepo.reportResponse
    fun reporting (sendReportModel : SendReportModel, context: Context){
        sendReportRepo.reporting(sendReportModel,context)
    }

}