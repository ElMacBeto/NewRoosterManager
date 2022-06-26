package com.elmac.roostermanager.sis.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.elmac.roostermanager.MainApplication
import com.elmac.roostermanager.R
import com.elmac.roostermanager.data.datasource.entities.GalloEntity
import java.io.File


class GallosAdapter:
    RecyclerView.Adapter<GallosAdapter.ViewHolder>(){

    private var gallosList= emptyList<GalloEntity>()
    private var onClickItem:((GalloEntity, View)-> Unit)? = null
    private var onDeleteItem:((GalloEntity)-> Unit)? = null


    @SuppressLint("NotifyDataSetChanged")
    fun setGallosList(products: List<GalloEntity>){
        this.gallosList = products
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (GalloEntity, View)->Unit){
        this.onClickItem = callback
    }

    fun setDeleteItem(callback: (GalloEntity)->Unit){
        this.onDeleteItem = callback
    }

    private var position:Int = 0

    private fun getItemPosition():Int{
        return position
    }

    private fun setItemPosition(position: Int){
        this.position=position
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemContainer:LinearLayout = view.findViewById(R.id.item_container)
        val name: TextView = view.findViewById(R.id.desc_gallo_element)
        val description: TextView = view.findViewById(R.id.mark_list)
        val roosterImage: ImageView = view.findViewById(R.id.gender_img)
//        val closeBtn:ImageView = view.findViewById(R.id.back_btn)
        val deleteBtn:ImageView = view.findViewById(R.id.delete_btn)
        val btns: LinearLayout = view.findViewById(R.id.btns)
        var lastPosition = -1


        fun setCardAnimation(viewToAnimate: View, position: Int) {
            // If the bound view wasn't previously displayed on screen, it's animated
            if (position > lastPosition) {
                val animation: Animation =
                    AnimationUtils.loadAnimation(MainApplication.appContext, android.R.anim.slide_in_left)
                viewToAnimate.startAnimation(animation)
                lastPosition = position
            }
        }

        fun setInfocard(gallo: GalloEntity) {
            name.text = gallo.line
            description.text = gallo.plaque.toString()
            roosterImage.setImageResource(R.drawable.ic_gallo)
            val file = File(gallo.img)

            if (file.exists()){
                val bitmap: Bitmap = BitmapFactory.decodeFile(gallo.img)
                roosterImage.setImageBitmap(bitmap)
            }
        }

        fun startEnterBtnAnimation():Boolean{
            btns.visibility=View.VISIBLE
            val animation: Animation =
                AnimationUtils.loadAnimation(MainApplication.appContext, R.anim.enter_from_right_btns)
            btns.startAnimation(animation)
            return true
        }

        fun startExitBtnAnimation(){
            val animation: Animation =
                AnimationUtils.loadAnimation(MainApplication.appContext, R.anim.out_from_right_btns)
            btns.startAnimation(animation)
            btns.visibility = View.GONE
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_info_card, viewGroup, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val gallo = gallosList[position]
        viewHolder.setInfocard(gallo)

        with(viewHolder){
            setCardAnimation(viewHolder.itemView,getItemPosition())

//            closeBtn.setOnClickListener{ startExitBtnAnimation() }
            deleteBtn.setOnClickListener{
                startExitBtnAnimation()

                onDeleteItem?.invoke(gallo)
            }

            itemContainer.setOnLongClickListener {
                setItemPosition(position)
                startEnterBtnAnimation()
            }

            itemContainer.setOnClickListener{ onClickItem?.invoke(gallo, roosterImage) }

        }

    }

    override fun getItemCount() = gallosList.size


}