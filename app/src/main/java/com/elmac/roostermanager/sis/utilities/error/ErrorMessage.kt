package com.elmac.roostermanager.sis.utilities.error


data class ErrorMessage(
    var line:String?=null,
    var year:String? =null,
    var plaque:String? =null,
    var ring:String? = null,
    var gender:String?=null,
    var leftLeg:String?=null,
    var rightLeg:String?=null,
    var noise:String?=null,
    var img:String?=null
)
