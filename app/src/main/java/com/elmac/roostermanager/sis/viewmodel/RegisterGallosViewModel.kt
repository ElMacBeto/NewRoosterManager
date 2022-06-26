package com.elmac.roostermanager.sis.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmac.roostermanager.data.datasource.entities.GalloEntity
import com.elmac.roostermanager.data.repository.GallosRepository
import kotlinx.coroutines.launch

class RegisterGallosViewModel: ViewModel() {

    private val repository = GallosRepository()
    val gallo = MutableLiveData<GalloEntity>()
    val fragment = MutableLiveData<Boolean>()

    fun setInitValues(id:Int){
        viewModelScope.launch {
            repository.getGalloById(id)
        }
    }

    fun validarData(gallo:GalloEntity){
        viewModelScope.launch {
            repository.add(gallo)
            fragment.postValue(true)
        }
    }


}