package com.elmac.roostermanager.sis.ui.view.fragments

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.elmac.roostermanager.R
import com.elmac.roostermanager.data.datasource.entities.GalloEntity
import com.elmac.roostermanager.databinding.FragmentGallosHomeBinding
import com.elmac.roostermanager.sis.ui.dialog.ConfirmDialog
import com.elmac.roostermanager.sis.ui.adapter.GallosAdapter
import com.elmac.roostermanager.sis.ui.view.activities.GALLO_ID
import com.elmac.roostermanager.sis.ui.view.activities.InfoCardActivity
import com.elmac.roostermanager.sis.viewmodel.GallosHomeViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class GallosHomeFragment : Fragment() {

    private var _binding: FragmentGallosHomeBinding? = null
    private val binding get() = _binding!!
    private val gallosHomeViewModel:GallosHomeViewModel by viewModels()
    private var gallos= mutableListOf<GalloEntity>()
    private lateinit var adapter: GallosAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGallosHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        setObservables()
        gallosHomeViewModel.getAllGallos()

        activity?.findViewById<FloatingActionButton>(R.id.add_btn)!!.setOnClickListener{
            gallosHomeViewModel.changeToRegisterActivity(requireContext())
        }
    }

    private fun initRecyclerView(){
        adapter = GallosAdapter()
        adapter.setGallosList(gallos)
        adapter.setOnClickItem { gallo, infoCard -> changeDetailActivity(gallo, infoCard) }
        adapter.setDeleteItem { gallo-> confirmDelete(gallo)}
        binding.recyclerViewList.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewList.adapter = adapter
    }

    private fun setObservables(){
        gallosHomeViewModel.gallosList.observe(viewLifecycleOwner) { currentGallosList->
            gallos.clear()
            gallos = currentGallosList
            adapter.setGallosList(gallos)
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun changeDetailActivity(galloEntity: GalloEntity, infoCard: View) {
        val intent = Intent(context, InfoCardActivity::class.java)
        intent.putExtra(GALLO_ID, galloEntity.id)

        val options = ActivityOptions.makeSceneTransitionAnimation(activity, infoCard, ViewCompat.getTransitionName(infoCard) )
        // Check if we're running on Android 5.0 or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent,options.toBundle())
        } else {
            // Swap without transition
        }
    }

    private fun confirmDelete(gallo:GalloEntity){
        val alert = ConfirmDialog(gallo){ deleteGallo -> gallosHomeViewModel.deleteGallo(deleteGallo) }
        alert.show(parentFragmentManager, ConfirmDialog.TAG)
    }

    override fun onResume() {
        super.onResume()
        gallosHomeViewModel.getAllGallos()
    }


}