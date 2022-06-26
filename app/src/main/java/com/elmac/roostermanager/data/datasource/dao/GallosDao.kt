package com.elmac.roostermanager.data.datasource.dao

import androidx.room.*
import com.elmac.roostermanager.data.datasource.entities.GalloEntity
import com.elmac.roostermanager.sis.Constantes
@Dao
interface GallosDao {
    @Query("SELECT * FROM gallos_table ORDER BY linea COLLATE NOCASE ASC")
    fun getAll(): MutableList<GalloEntity>

    @Query("SELECT * FROM gallos_table WHERE id IN (:galloId)")
    fun loadAllByIds(galloId: Int): GalloEntity

    @Insert
    fun add(gallo: GalloEntity):Long

    @Delete
    fun delete(gallo: GalloEntity)

    @Update
    suspend fun update(gallo: GalloEntity): Int

    @Query("SELECT * FROM gallos_table WHERE linea like :value OR año like :value")
    suspend fun search(value: String?):  MutableList<GalloEntity>?

    @Query("SELECT * FROM gallos_table WHERE linea = :value")
    suspend fun getByName(value: String?):  GalloEntity?

}