package com.projectassyifa.cashier_pujasera.data.income.vm

import android.content.Context
import com.projectassyifa.cashier_pujasera.data.income.model.IncomeModel
import com.projectassyifa.cashier_pujasera.data.income.repo.IncomeRepo
import javax.inject.Inject

class IncomeVM @Inject constructor(var incomeRepo: IncomeRepo){
    var income_report = incomeRepo.income_report

    fun income(incomeModel: IncomeModel,context: Context){
        incomeRepo.income(incomeModel,context)
    }
}