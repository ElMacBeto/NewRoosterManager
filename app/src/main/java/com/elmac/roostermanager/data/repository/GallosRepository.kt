package com.elmac.roostermanager.data.repository

import com.elmac.roostermanager.MainApplication
import com.elmac.roostermanager.data.datasource.entities.GalloEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GallosRepository {


    private val db = MainApplication.database.gallosDao()

    suspend fun getAll():MutableList<GalloEntity> =
        withContext(Dispatchers.IO) {
            db.getAll()
        }

    suspend fun add(gallo:GalloEntity) =
        withContext(Dispatchers.IO) {
            db.add(gallo)
        }

    suspend fun delete(gallo:GalloEntity) =
        withContext(Dispatchers.IO) {
            db.delete(gallo)
        }

    suspend fun update(gallo:GalloEntity) =
        withContext(Dispatchers.IO) {
            db.update(gallo)
        }

    suspend fun getGalloById(id:Int):GalloEntity =
        withContext(Dispatchers.IO) {
            db.loadAllByIds(id)
        }

}