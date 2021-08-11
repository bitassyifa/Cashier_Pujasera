package com.projectassyifa.cashier_pujasera.container

import android.app.Application

class MyApplication :Application() {
    val applicationComponent : ApplicationComponent = DaggerApplicationComponent.create()
}