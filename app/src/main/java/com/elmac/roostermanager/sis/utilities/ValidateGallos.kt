package com.elmac.roostermanager.sis.utilities

import com.elmac.roostermanager.MainApplication.Companion.appContext
import com.elmac.roostermanager.R
import com.elmac.roostermanager.data.datasource.entities.GalloEntity
import com.elmac.roostermanager.sis.utilities.error.ErrorMessage

class ValidateGallos {

    fun validate(newGallo:GalloEntity):ValidateGalloModel{
        val errorMessage = ErrorMessage()
        var status = true

        println(newGallo)
        if(!validateLine(newGallo.line!!)){
            status = false
            errorMessage.line = appContext!!.resources.getString(R.string.error_line)
        }
        if(!validateYear(newGallo.year!!)){
            status=false
            errorMessage.year = appContext!!.resources.getString(R.string.error_year)
        }

        return ValidateGalloModel(status, errorMessage)
    }


    private fun validateLine(line:String): Boolean {
        return line.isNotEmpty()
//        return line.length in 1..4
    }

    private fun validateYear(year:String): Boolean {
        return year.length == 4
    }

}

