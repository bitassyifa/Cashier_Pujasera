package com.projectassyifa.cashier_pujasera.data.member.vm

import android.content.Context
import com.projectassyifa.cashier_pujasera.data.member.model.MemberModel
import com.projectassyifa.cashier_pujasera.data.member.repo.CekPinRepo
import com.projectassyifa.cashier_pujasera.data.member.repo.MemberRepo
import javax.inject.Inject

class CekPinVM @Inject constructor(var memberRepo: CekPinRepo) {
    //    var dataMember = memberRepo.memberData
    var responseData = memberRepo.memberResponse

    fun member(memberModel: MemberModel,context: Context){
        memberRepo.member(memberModel,context)


    }
}