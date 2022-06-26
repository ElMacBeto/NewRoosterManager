package com.elmac.roostermanager.sis.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ImageExistAlertDialog(
    val updateImage: () -> Unit)
    : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = AlertDialog.Builder(requireContext())
            .setMessage("existe una imagen seleccionada, desea reemplazarla?")
            .setPositiveButton("si") { _, _ ->
                updateImage()
            }
            .setNegativeButton("no"){ view, _ ->
                view.dismiss()
            }
            .create()
        return dialog
    }
    companion object {
        const val TAG = "ImageExistAlertDialog"
    }
}