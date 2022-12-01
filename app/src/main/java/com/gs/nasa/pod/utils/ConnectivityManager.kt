package com.gs.nasa.pod.utils

import android.content.Context
import android.net.ConnectivityManager

class ConnectivityManager constructor(private val context: Context) {
    fun isInternetConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetwork != null && cm.getNetworkCapabilities(cm.activeNetwork) != null
    }
}