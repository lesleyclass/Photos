package com.example.photos.adapter

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.photo.model.Photo

class PhotoAdapter(
    private val context: Context,
    private val photoList: MutableList<Photo>,
): ArrayAdapter<Photo>(context, R.layout.simple_list_item_1, photoList) {
    private data class PhotoHolder(val photoTitleTextView: TextView)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val productView = convertView ?: LayoutInflater.from(context).inflate(R.layout.simple_list_item_1, parent, false).apply {
            tag = PhotoHolder(findViewById(R.id.text1))
        }

        (productView.tag as PhotoHolder).photoTitleTextView.text = photoList[position].title

        return productView
    }
}