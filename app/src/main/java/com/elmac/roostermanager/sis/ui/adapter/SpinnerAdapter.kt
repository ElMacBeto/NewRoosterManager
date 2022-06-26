package com.elmac.roostermanager.sis.ui.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import com.elmac.roostermanager.R
import com.elmac.roostermanager.sis.ui.view.activities.SpinnerValueModel


class SpinnerAdapter(context: Context, imageList: MutableList<SpinnerValueModel>):ArrayAdapter<SpinnerValueModel>(context, 0, imageList){


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    fun initView(position: Int, convertView: View?, parent: ViewGroup):View{

        val data = getItem(position)
        val view = LayoutInflater.from(context).inflate(R.layout.list_item,parent, false)

        val myImage = view.findViewById<ImageView>(R.id.imageView)
        myImage.setImageResource(data!!.image!!)
        myImage.contentDescription = data.description

        return view
    }

}