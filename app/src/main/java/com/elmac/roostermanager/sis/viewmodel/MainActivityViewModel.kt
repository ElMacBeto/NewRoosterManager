package com.elmac.roostermanager.sis.viewmodel

import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.elmac.roostermanager.domain.ItemNavigationSeletedUseCase

class MainActivityViewModel: ViewModel() {

    fun setOnItemSelectedNavigationMenu(menuItem: MenuItem, fragmentManager: FragmentManager){
        val itemNavigationSeletedUseCase= ItemNavigationSeletedUseCase()
        itemNavigationSeletedUseCase.setAction(menuItem, fragmentManager)
    }

}