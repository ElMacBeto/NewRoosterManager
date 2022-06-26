package com.elmac.roostermanager.data.datasource.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.elmac.roostermanager.sis.Constantes

@Entity(tableName = Constantes.TABLE_NAME)
data class GalloEntity(
    @PrimaryKey(autoGenerate = true) var id:Int,
    @ColumnInfo(name = Constantes.LINE_FIELD) var line:String?="",
    @ColumnInfo(name = Constantes.YEAR_FIELD) var year:String? ="",
    @ColumnInfo(name = Constantes.PLAQUE_FIELD) var plaque:String? ="",
    @ColumnInfo(name = Constantes.RING_FIELD) var ring:String? = "",
    @ColumnInfo(name = Constantes.GENDER_FIELD) var gender:String?="",
    @ColumnInfo(name = Constantes.LEFTLEG_FIELD) var leftLeg:String?="",
    @ColumnInfo(name = Constantes.RIGHTLEG_FIELD) var rightLeg:String?="",
    @ColumnInfo(name = Constantes.NOISE_FIELD) var noise:String?="",
    @ColumnInfo(name = Constantes.IMAGE_FIELD) var img:String?=""
    )