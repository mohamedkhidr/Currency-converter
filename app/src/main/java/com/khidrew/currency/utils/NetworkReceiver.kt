package com.khidrew.currency.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast

class NetworkReceiver(private val callback: (Boolean) -> Unit) :
    BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
      if(!isInitialStickyBroadcast) { // prevent event fire at first subscribe
          val connectivityManager =
              context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
          val network = connectivityManager.activeNetwork
          val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

          callback.invoke(networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true)
      }
    }
}