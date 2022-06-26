package com.elmac.roostermanager.sis.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmac.roostermanager.data.datasource.entities.GalloEntity
import com.elmac.roostermanager.data.repository.GallosRepository
import kotlinx.coroutines.launch

class InfoViewModel:ViewModel() {

    val gallo = MutableLiveData<GalloEntity>()
    val repository = GallosRepository()

    fun getGalloById(id:Int){
        viewModelScope.launch {
            val currentGallo=repository.getGalloById(id)
            gallo.postValue(currentGallo)
        }
    }
}