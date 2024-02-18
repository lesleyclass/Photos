package com.example.photos.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.photos.R
import com.example.photos.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        setSupportActionBar(activityMainBinding.mainToolbar.apply {
            title = getString(R.string.app_name)
        })
    }
}