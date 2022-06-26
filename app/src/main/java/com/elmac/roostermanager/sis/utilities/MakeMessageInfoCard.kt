package com.elmac.roostermanager.sis.utilities

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import com.elmac.roostermanager.data.datasource.entities.GalloEntity

class MakeMessageInfoCard {

    fun setMessage(gallo:GalloEntity):List<String>{
        val messages = mutableListOf<String>()

        val message1 = SpannableString("Año: ${gallo.year!!}")
        val message2 = SpannableString("Linea: ${gallo.line!!.uppercase()}")
        val message3 = SpannableString("-Pata izquierda ${gallo.leftLeg!!.uppercase()} \n" +
                        "-Pata derecha ${gallo.rightLeg!!.uppercase()} \n-Nariz ${gallo.noise!!.uppercase()}")

        val boldSpan1 = StyleSpan(Typeface.BOLD)
        val boldSpan2 = StyleSpan(Typeface.BOLD)
        val boldSpan3 = StyleSpan(Typeface.BOLD)

        message1.setSpan(boldSpan1, 5, message1.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        message2.setSpan(boldSpan2, 6, message2.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        message3.setSpan(boldSpan3, 6, message3.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

        messages.add(message1.toString())
        messages.add(message2.toString())
        messages.add(message3.toString())

        return messages
    }
}