package com.elmac.roostermanager.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.elmac.roostermanager.data.datasource.dao.GallosDao
import com.elmac.roostermanager.data.datasource.entities.GalloEntity

@Database(entities = [GalloEntity::class], version = 1)
abstract class GallosDataBase : RoomDatabase() {
    abstract fun gallosDao(): GallosDao

}

