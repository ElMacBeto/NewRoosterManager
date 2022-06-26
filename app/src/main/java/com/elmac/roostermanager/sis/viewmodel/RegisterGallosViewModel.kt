package com.elmac.roostermanager.sis.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmac.roostermanager.data.datasource.entities.GalloEntity
import com.elmac.roostermanager.data.repository.GallosRepository
import com.elmac.roostermanager.sis.utilities.ValidateGallos
import com.elmac.roostermanager.sis.utilities.error.ErrorMessage
import kotlinx.coroutines.launch

class RegisterGallosViewModel: ViewModel() {

    private val repository = GallosRepository()
    val gallo = MutableLiveData<GalloEntity>()
    val fragment = MutableLiveData<Boolean>()
    val errorMessage=MutableLiveData<ErrorMessage>()

    fun setInitValues(id:Int){
        viewModelScope.launch {
            val currentGallo = repository.getGalloById(id)
            gallo.postValue(currentGallo)
        }
    }

    fun validarData(gallo:GalloEntity, edit:Boolean){
        val validateGallos = ValidateGallos()
        val errors = validateGallos.validate(gallo)
            if (errors.status!!){
                viewModelScope.launch {
                    if(!edit){
                        repository.add(gallo)
                    }else{
                        repository.update(gallo)
                    }
                    fragment.postValue(true)
                }
            }else{
                errorMessage.postValue(errors.errorMessage)
            }
    }


}