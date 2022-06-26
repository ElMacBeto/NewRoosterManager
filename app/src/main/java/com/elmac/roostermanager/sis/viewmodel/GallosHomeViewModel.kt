package com.elmac.roostermanager.sis.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmac.roostermanager.data.datasource.entities.GalloEntity
import com.elmac.roostermanager.data.repository.GallosRepository
import com.elmac.roostermanager.sis.ui.view.activities.RegisterGallosActivity
import kotlinx.coroutines.launch

class GallosHomeViewModel: ViewModel() {
    lateinit var repository:GallosRepository


    val gallosList = MutableLiveData<MutableList<GalloEntity>>()


    fun getAllGallos(){
        viewModelScope.launch {
            repository = GallosRepository()
            val currentGallosList = repository.getAll()
            gallosList.postValue(currentGallosList)
        }
    }

    fun deleteGallo(gallo:GalloEntity){
        viewModelScope.launch {
            repository = GallosRepository()
            repository.delete(gallo)
            getAllGallos()
        }
    }

    fun changeToRegisterActivity(context:Context){
        val intent = Intent(context, RegisterGallosActivity::class.java)
        context.startActivity(intent)
    }

}