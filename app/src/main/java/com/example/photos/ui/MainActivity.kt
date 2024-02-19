package com.example.photos.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.ImageRequest
import com.example.photos.R
import com.example.photos.adapter.PhotoAdapter
import com.example.photos.adapter.PhotoListAdapter
import com.example.photos.databinding.ActivityMainBinding
import com.example.photos.model.Photo
import com.example.photos.model.ServiceApi


class MainActivity : AppCompatActivity() {
    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val photoList: MutableList<Photo> = mutableListOf()
    private val photoAdapter: PhotoAdapter by lazy {
        PhotoAdapter(this, photoList)
    }

    private val photoImageList: MutableList<Bitmap> = mutableListOf()
    private val photoListAdapter: PhotoListAdapter by lazy {
        PhotoListAdapter(this, photoImageList)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        setSupportActionBar(activityMainBinding.mainToolbar.apply {
            title = getString(R.string.app_name)
        })

        activityMainBinding.photoSpinner.apply {
            adapter = photoAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val size = photoImageList.size
                    photoImageList.clear()
                    photoListAdapter.notifyItemRangeRemoved(0, size)
                    retrieveProductImages(photoList[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // NSA
                }
            }
        }

        activityMainBinding.photoRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = photoListAdapter
        }

        retrievePhotos()

    }

    private fun retrievePhotos() =
        ServiceApi.ProductListRequest(
            { photoList ->
                photoList.also {
                    photoAdapter.addAll(it)
                }
            },
            {
                Toast.makeText(this, getString(R.string.request_problem), Toast.LENGTH_SHORT).show()
            },
        ).also { ServiceApi.getInstance(this).addToRequestQueue(it) }
    private fun retrieveProductImages(photo: Photo) {
        imageRequest(photo.thumbnailUrl)
        imageRequest(photo.url)
    }

    private fun imageRequest(imageUrl: String) {
        ImageRequest(
            imageUrl,
            { response ->
                photoImageList.add(response)
                photoListAdapter.notifyItemInserted(photoImageList.lastIndex)
            },
            0,
            0,
            ImageView.ScaleType.CENTER,
            Bitmap.Config.ARGB_8888,
            {
                Toast.makeText(this, getString(R.string.request_problem), Toast.LENGTH_SHORT)
                    .show()
            },
        ).also { ServiceApi.getInstance(this).addToRequestQueue(it) }
    }
}