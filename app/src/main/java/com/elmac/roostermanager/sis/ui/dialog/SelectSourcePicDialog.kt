package com.elmac.roostermanager.sis.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment


class SelectSourcePicDialog
    (val selectPicFromGalery: () -> Unit,
     val takePhoto: () -> Unit)
    : DialogFragment(){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Tomar foto nueva o buscaren galeria?")
            .setPositiveButton("tomar foto") { _, _ ->
                takePhoto()
            }
            .setNegativeButton("galeria"){_,_->
                selectPicFromGalery()
            }
            .create()

        return dialog
    }


}
