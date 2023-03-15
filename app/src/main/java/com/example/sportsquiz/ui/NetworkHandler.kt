package com.example.sportsquiz.ui

import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

interface NetworkHandler {
    val isConnected: Boolean
    val isNetworkConnected: MutableStateFlow<Boolean>
}

abstract class NetworkHandlerImpl(
    protected val connectivityManager: ConnectivityManager,
) : NetworkHandler {

    @Volatile
    protected var cachedIsConnected = false

    override val isConnected: Boolean
        get() = cachedIsConnected

    override val isNetworkConnected = MutableStateFlow(true)

    abstract fun startListening()

    abstract fun stopListening()
}

@Suppress("Deprecation")
@TargetApi(23)
class LegacyNetworkHandler(
    private val context: Context,
    connectivityManager: ConnectivityManager,
) : NetworkHandlerImpl(connectivityManager) {

    private val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val isConnected = connectivityManager.activeNetworkInfo?.isConnected == true
            cachedIsConnected = isConnected
            isNetworkConnected.update { isConnected }
        }
    }

    override fun startListening() {
        context.registerReceiver(receiver, filter)
    }

    override fun stopListening() {
        context.unregisterReceiver(receiver)
    }
}

@TargetApi(24)
class NougatNetworkHandler(
    connectivityManager: ConnectivityManager,
) : NetworkHandlerImpl(connectivityManager) {

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            cachedIsConnected = true
            isNetworkConnected.update { true }
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            cachedIsConnected = false
            isNetworkConnected.update { false }
        }
    }

    override fun startListening() {
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    override fun stopListening() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}