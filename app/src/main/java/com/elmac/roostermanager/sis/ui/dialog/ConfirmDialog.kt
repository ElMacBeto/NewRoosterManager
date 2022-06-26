package com.elmac.roostermanager.sis.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.elmac.roostermanager.R
import com.elmac.roostermanager.data.datasource.entities.GalloEntity


class ConfirmDialog(
    private val gallo: GalloEntity,
    private val deleteItem: (gallo: GalloEntity) -> Unit
) : DialogFragment()  {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val myView = layoutInflater.inflate(R.layout.delete_dialog, null)
        val okBtn = myView.findViewById<ImageView>(R.id.btn_ok)
        val cancel = myView.findViewById<TextView>(R.id.cancel_btn)
        val tvMessage = myView.findViewById<TextView>(R.id.message)

        val message = SpannableString("Eliminar Gallo ${gallo.line!!.uppercase()}?")
        val boldSpan1 = StyleSpan(Typeface.BOLD)

        message.setSpan(boldSpan1, 15, message.length-1, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

        tvMessage.text = message



        okBtn.setOnClickListener {
            deleteItem(gallo)
            Toast.makeText(context, "proveedor eliminado ", Toast.LENGTH_LONG).show()
            dismiss()
        }

        cancel.setOnClickListener {
            dismiss()
        }

        val alert = AlertDialog.Builder(requireContext())
            .setView(myView)
            .create()
        alert.window!!.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT))
        return alert

    }

    companion object {
        const val TAG = "DeleteDialog"
    }
}