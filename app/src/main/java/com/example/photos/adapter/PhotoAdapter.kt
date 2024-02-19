package com.example.photos.adapter


import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.photos.R
import com.example.photos.model.Photo

class PhotoAdapter(
    private val context: Context,
    private val photoList: MutableList<Photo>,
): ArrayAdapter<Photo>(context, R.layout.spinner_item, photoList) {
    private data class PhotoHolder(val photoTitleTextView: TextView)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val photoView = convertView ?: LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false).apply {
            tag = PhotoHolder(findViewById(R.id.text))
        }

        (photoView.tag as PhotoHolder).photoTitleTextView.text = photoList[position].title

        return photoView
    }
}