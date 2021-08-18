package com.projectassyifa.cashier_pujasera.container


import com.projectassyifa.cashier_pujasera.config.Connect
import com.projectassyifa.cashier_pujasera.data.income.api.IncomeAPI
import com.projectassyifa.cashier_pujasera.data.login.api.UserLoginAPI
import com.projectassyifa.cashier_pujasera.data.member.api.CekPinAPI
import com.projectassyifa.cashier_pujasera.data.member.api.MemberAPI
import com.projectassyifa.cashier_pujasera.data.sendReport.api.ReportAPI
import com.projectassyifa.cashier_pujasera.data.sendReport.api.SendReportAPI
import dagger.Module
import dagger.Provides

@Module
class NetworkModul {
    @Provides
    fun provideUserLoginAPI(): UserLoginAPI {
        return Connect.urlLogin().create(UserLoginAPI::class.java)
    }

    @Provides
    fun provideMemberAPI(): MemberAPI {
        return Connect.urlMember().create(MemberAPI::class.java)
    }
    @Provides
    fun provideCekPinAPI(): CekPinAPI {
        return Connect.urlMember().create(CekPinAPI::class.java)
    }
    @Provides
    fun provideSendReportAPI(): SendReportAPI {
        return Connect.urlMember().create(SendReportAPI::class.java)
    }
    @Provides
    fun provideReportAPI(): ReportAPI {
        return Connect.urlMember().create(ReportAPI::class.java)
    }
    @Provides
    fun provideIncomeAPI(): IncomeAPI {
        return Connect.urlMember().create(IncomeAPI::class.java)
    }
}