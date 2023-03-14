package com.example.sportsquiz.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.flow.MutableStateFlow


class NetworkHandler(private val context: Context) : NetworkCallback() {

    val isNetworkConnected: MutableStateFlow<Boolean> = MutableStateFlow(true)

    val isConnected: Boolean
        get() = isNetworkConnected.value

    private val networkRequest: NetworkRequest = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .build()

    fun listen() {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(networkRequest, this)
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        isNetworkConnected.value = true
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        isNetworkConnected.value = false
    }

    override fun onUnavailable() {
        super.onUnavailable()
        isNetworkConnected.value = false
    }
}