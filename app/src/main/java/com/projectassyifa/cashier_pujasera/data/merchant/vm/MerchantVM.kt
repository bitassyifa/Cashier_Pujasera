package com.projectassyifa.cashier_pujasera.data.merchant.vm

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.projectassyifa.cashier_pujasera.data.merchant.model.MerchantModel
import com.projectassyifa.cashier_pujasera.data.merchant.repo.MerchantRepo
import javax.inject.Inject

class MerchantVM @Inject constructor(val merchantRepo: MerchantRepo){
    val data_merchant : MutableLiveData<List<MerchantModel>>? = merchantRepo.data_merchant

    fun merchant(context: Context){
        merchantRepo.merchant(context)
    }
}