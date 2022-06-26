package com.elmac.roostermanager.sis.ui.view.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import com.elmac.roostermanager.data.datasource.entities.GalloEntity
import com.elmac.roostermanager.databinding.ActivityInfoCardBinding
import com.elmac.roostermanager.sis.utilities.MakeMessageInfoCard
import com.elmac.roostermanager.sis.viewmodel.InfoViewModel
import java.io.File

const val GALLO_ID = "galloId"

class InfoCardActivity : AppCompatActivity(){


    lateinit var binding: ActivityInfoCardBinding
    private val infoViewModel: InfoViewModel by viewModels()
    private var idGallo = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idGallo = intent.getIntExtra(GALLO_ID,0)

        infoViewModel.gallo.observe(this){ infoGallo-> setInfoCard(infoGallo) }

        infoViewModel.getGalloById(idGallo)

        binding.editBtn.setOnClickListener{changeEditActivity()}

    }

    private fun setInfoCard(infoGallo: GalloEntity?) {

        val makeMessages = MakeMessageInfoCard()
        val messages = makeMessages.setMessage(infoGallo!!)

        binding.galloYear.text = messages[0]
        binding.topAppBar.title = "Placa: "+ infoGallo.plaque
        binding.galloLine.text = messages[1]
        binding.galloMark.text = messages[2]

        val file = File(infoGallo.img)

        if (file.exists()){
            val bitmap: Bitmap = BitmapFactory.decodeFile(infoGallo.img)
            binding.galloImage.setImageBitmap(bitmap)
        }

    }

    private fun changeEditActivity(){
        val intent = Intent(this, RegisterGallosActivity::class.java )
        intent.putExtra(RegisterGallosActivity.ID, idGallo)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        infoViewModel.getGalloById(idGallo)
    }

}