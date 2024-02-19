package com.example.photos.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.photos.R
import com.example.photos.adapter.PhotoAdapter
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        setSupportActionBar(activityMainBinding.mainToolbar.apply {
            title = getString(R.string.app_name)
        })

        activityMainBinding.photoSpinner.apply {
            adapter = photoAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) { }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // NSA
                }
            }
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
}