package com.elmac.roostermanager.domain

import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import com.elmac.roostermanager.R
import com.elmac.roostermanager.sis.ui.view.fragments.GallosHomeFragment
import com.elmac.roostermanager.sis.ui.view.fragments.MarcasHomeFragment

class ItemNavigationSeletedUseCase {

    fun setAction(menuItem: MenuItem, fragmentManager: FragmentManager){
        when(menuItem.itemId){
            R.id.gallos_home -> {
                fragmentManager.beginTransaction().replace(R.id.fragment_container, GallosHomeFragment()).commit()
            }
            R.id.marcas_home-> {
                fragmentManager.beginTransaction().replace(R.id.fragment_container, MarcasHomeFragment()).commit()
            }
            else ->  println("no se selecciono")
        }
    }
}