package com.elmac.roostermanager.sis.utilities

import com.elmac.roostermanager.sis.utilities.error.ErrorMessage

data class ValidateGalloModel(
    var status:Boolean?=false,
    var errorMessage:ErrorMessage? = ErrorMessage()
)