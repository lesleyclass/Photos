package com.example.photos.model

import android.content.Context
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import java.net.HttpURLConnection

class ServiceApi (context: Context) {
    companion object {
        const val PHOTO_ENDPOINT = "https://jsonplaceholder.typicode.com/photos/"

        @Volatile
        private var INSTANCE: ServiceApi? = null

        fun getInstance(context: Context) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: ServiceApi(context).also {
                INSTANCE = it
            }
        }
    }

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    fun <T> addToRequestQueue(request: Request<T>) {
        requestQueue.add(request)
    }

    class ProductListRequest(
        private val responseListener: Response.Listener<PhotoList>,
        errorListener: Response.ErrorListener,
    ): Request<PhotoList>(Method.GET, PHOTO_ENDPOINT, errorListener){
        override fun parseNetworkResponse(response: NetworkResponse?): Response<PhotoList> =
            if (response?.statusCode == HttpURLConnection.HTTP_OK || response?.statusCode == HttpURLConnection.HTTP_NOT_MODIFIED) {
                String(response.data).run {
                    Response.success(
                        Gson().fromJson(this, PhotoList::class.java),
                        HttpHeaderParser.parseCacheHeaders(response)
                    )
                }
            } else {
                Response.error(VolleyError())
            }

        override fun deliverResponse(response: PhotoList?) {
            responseListener.onResponse(response)
        }
    }
}