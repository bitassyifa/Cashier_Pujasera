package com.projectassyifa.cashier_pujasera.container



import com.projectassyifa.cashier_pujasera.screen.fadipay.FadipayActivity
import com.projectassyifa.cashier_pujasera.screen.login.LoginFragment
import com.projectassyifa.cashier_pujasera.screen.report.DialogClosingReport
import com.projectassyifa.cashier_pujasera.screen.report.ReportActivity
import com.projectassyifa.cashier_pujasera.screen.report.ReportAdapter
import dagger.Component

@Component(modules = [NetworkModul::class])
interface ApplicationComponent {

fun inject(loginFragment: LoginFragment)
fun inject(fadipayActivity: FadipayActivity)
fun inject(reportAdapter: ReportAdapter)
fun inject(dialogClosingReport: DialogClosingReport)
fun inject(reportActivity: ReportActivity)
}