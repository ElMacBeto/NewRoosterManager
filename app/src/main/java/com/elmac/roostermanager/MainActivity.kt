package com.elmac.roostermanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.elmac.roostermanager.databinding.ActivityMainBinding
import com.elmac.roostermanager.sis.ui.view.fragments.GallosHomeFragment
import com.elmac.roostermanager.sis.viewmodel.MainActivityViewModel
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    lateinit var binding: ActivityMainBinding
    private val mainActivityViewModel:MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.background = null
        setFragment()
        binding.bottomNavigation.setOnItemSelectedListener(this)
    }

    private fun setFragment(){
        val transition= supportFragmentManager.beginTransaction()
        transition.replace(R.id.fragment_container, GallosHomeFragment())
        transition.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        mainActivityViewModel.setOnItemSelectedNavigationMenu(item, supportFragmentManager)
        return true
    }


}