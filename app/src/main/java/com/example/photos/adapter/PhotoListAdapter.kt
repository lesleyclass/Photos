package com.example.photos.adapter


import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.photos.databinding.PhotoItemBinding

class PhotoListAdapter(
    private val activityContext: Context,
    private val productImageList: MutableList<Bitmap>
): RecyclerView.Adapter<PhotoListAdapter.ProductImageViewHolder>() {
    inner class ProductImageViewHolder(photoItemBinding: PhotoItemBinding): RecyclerView.ViewHolder(photoItemBinding.photoImageView){
        val productIv: ImageView = photoItemBinding.photoImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductImageViewHolder =
        ProductImageViewHolder(PhotoItemBinding.inflate(LayoutInflater.from(activityContext), parent, false))

    override fun onBindViewHolder(holder: ProductImageViewHolder, position: Int) =
        holder.productIv.setImageBitmap(productImageList[position])

    override fun getItemCount(): Int = productImageList.size
}