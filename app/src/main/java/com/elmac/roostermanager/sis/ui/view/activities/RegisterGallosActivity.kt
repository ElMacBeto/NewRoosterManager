package com.elmac.roostermanager.sis.ui.view.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.AutoCompleteTextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.Observer
import com.elmac.roostermanager.R
import com.elmac.roostermanager.data.datasource.entities.GalloEntity
import com.elmac.roostermanager.databinding.ActivityRegisterGallosBinding
import com.elmac.roostermanager.sis.ui.adapter.SpinnerAdapter
import com.elmac.roostermanager.sis.ui.dialog.ImageExistAlertDialog
import com.elmac.roostermanager.sis.ui.dialog.SelectSourcePicDialog
import com.elmac.roostermanager.sis.viewmodel.RegisterGallosViewModel
import kotlinx.coroutines.selects.select
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.util.*


class RegisterGallosActivity : AppCompatActivity() {

    companion object{
        const val ID = "id"
    }

    lateinit var binding: ActivityRegisterGallosBinding
    private val registerGallosViewModel:RegisterGallosViewModel by viewModels()
    var dataGallo= GalloEntity(0)
    var PhotoPath=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterGallosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setObservables()
        setInitDataGallo()
        initValuesSpinner()

        binding.cameraBtn.setOnClickListener{ selectImage()}
        binding.guardarBtn.setOnClickListener { saveGallo()}

    }

    private fun setInitDataGallo(){
        val id = intent.getIntExtra(ID,0)
        if(id!=0){
            registerGallosViewModel.setInitValues(id)
        }
    }

    private fun saveGallo(){
        getNewGalloData()
        registerGallosViewModel.validarData(dataGallo)
    }

    private fun setObservables(){
        registerGallosViewModel.gallo.observe(this, Observer {
            dataGallo=it
            setFormWithInitValues()
        })

        registerGallosViewModel.fragment.observe(this, Observer {
            finish()
        })
    }

    private fun getNewGalloData(){
        dataGallo.line = binding.lienaValue.text.toString()
        dataGallo.year = binding.yearValue.text.toString().toInt()
        dataGallo.plaque = binding.placaValue.text.toString()
        dataGallo.ring = binding.ringValue.text.toString()
        dataGallo.leftLeg = binding.spPataIzquierda.selectedItem.toString()
        dataGallo.rightLeg = binding.spPataDerecha.selectedItem.toString()
        dataGallo.noise = binding.spNariz.selectedItem.toString()
        dataGallo.gender = binding.spGenero.selectedItem.toString()
        dataGallo.id=0
    }

    private fun setFormWithInitValues(){
        binding.lienaValue.setText(dataGallo.line)
        binding.yearValue.setText(dataGallo.year.toString())
        binding.placaValue.setText(dataGallo.plaque)
        binding.ringValue.setText(dataGallo.ring)
    }

    private fun initValuesSpinner(){
        val legImages = listOf(R.drawable.pata_ninguna_, R.drawable.pata_afuera_,
            R.drawable.pata_adentro_, R.drawable.pata_ambas_)
        val noiseImage= listOf(R.drawable.nariz_ninguna_, R.drawable.nariz_derecha_,
            R.drawable.nariz_izquierda_, R.drawable.nariz_ambas_)
        val genderImage= listOf(R.drawable.ic_gallo, R.drawable.ic_gallina_mini)


        binding.spPataIzquierda.apply {
            adapter= SpinnerAdapter(applicationContext, legImages)
            setSelection(1,false)
        }
        binding.spPataDerecha.apply {
            adapter= SpinnerAdapter(applicationContext, legImages)
            setSelection(1,false)
        }
        binding.spNariz.apply {
            adapter= SpinnerAdapter(applicationContext, noiseImage)
            setSelection(1,false)
        }
        binding.spGenero.apply {
            adapter= SpinnerAdapter(applicationContext, genderImage)
            setSelection(1,false)
        }
    }

    private fun selectImage(){
        val file = File(dataGallo.img)
        if(file.exists()){
            val alert = ImageExistAlertDialog{ -> showSelectPicSourceAlert() }
            alert.show(supportFragmentManager, ImageExistAlertDialog.TAG)
        }else {
            val uri = PhotoPath.toUri()
            val sourceFile = DocumentFile.fromSingleUri(this, uri)
            if (sourceFile!!.exists()) {
                val alert = ImageExistAlertDialog{ -> showSelectPicSourceAlert() }
                alert.show(supportFragmentManager, ImageExistAlertDialog.TAG)
            }else{
                showSelectPicSourceAlert()
            }
        }
    }

    val REQUEST_IMAGE_CAPTURE = 1
    val PICK_IMAGE = 2

    @RequiresApi(Build.VERSION_CODES.N)
    fun showSelectPicSourceAlert(){
        val alert = SelectSourcePicDialog({-> selectPictureGalery()},
            {-> dispatchTakePictureIntent()})
        alert.show(supportFragmentManager, "imagen")
    }

    fun selectPictureGalery(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            this.let {
                takePictureIntent.resolveActivity(it.packageManager)?.also {
                    // Create the File where the photo should go
                    val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        // Error occurred while creating the File
                        null
                    }
                    // Continue only if the File was successfully created
                    photoFile?.also {
                        val photoURI: Uri? = this.let { it1 ->
                            FileProvider.getUriForFile(
                                it1,
                                "com.elmac.roostermanager.android.fileprovider",
                                it
                            )
                        }
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                    }
                }
            }
        }
    }

    /**crea la ruta de la imagen tomada en la intent de la camara*/
    @RequiresApi(Build.VERSION_CODES.N)
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val picture = File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            if (absolutePath != null){
                PhotoPath = absolutePath
            }
        }
        return picture
    }

    /**atiende las acciones de los intent de seleccion de imagen de galeria
     * o tomar fotografia con la camara*/
    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("UseRequireInsteadOfGet")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val bitmap: Bitmap = BitmapFactory.decodeFile(PhotoPath)
            val imageScaled = Bitmap.createScaledBitmap(bitmap, 550, 400, false)
            binding.cameraBtn.setImageBitmap(imageScaled)
            dataGallo.img = PhotoPath
        }

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null){
            val imageUri : Uri? = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
            val file = createImageFile()
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos)
            val bArray = bos.toByteArray()
            file.writeBytes(bArray)
            binding.cameraBtn.setImageBitmap(bitmap)
            dataGallo.img = PhotoPath
        }
    }

}