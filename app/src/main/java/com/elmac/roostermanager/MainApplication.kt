package com.elmac.roostermanager

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.elmac.roostermanager.data.db.GallosDataBase
import com.elmac.roostermanager.sis.di.components.DaggerUtilComponent
import com.elmac.roostermanager.sis.di.components.UtilComponent
import com.practica.ejemplodagger.sis.di.module.ContextModule

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        utilComponent = DaggerUtilComponent.builder().contextModule(
            ContextModule(
                applicationContext
            )
        ).build()

        database =  Room.databaseBuilder(
            this, GallosDataBase::class.java,
            "productos-db")
            .build()

    }

    companion object {
        lateinit var database: GallosDataBase

        private var utilComponent: UtilComponent? = null
        val appContext: Context?
            get() = utilComponent!!.appContext
    }

}