package com.elmac.roostermanager.sis.di.components

import android.content.Context
import com.elmac.roostermanager.sis.viewmodel.MainActivityViewModel
import com.practica.ejemplodagger.sis.di.module.UtilModule
import dagger.Component

import javax.inject.Singleton


@Singleton
@Component(modules = [UtilModule::class])
interface UtilComponent {

    val appContext: Context?

}