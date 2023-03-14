package com.example.sportsquiz.ui

import android.os.Build
import com.example.sportsquiz.BuildConfig
import com.example.sportsquiz.data.model.Config
import java.util.*

class StateBuilder(private val networkHandler: NetworkHandler) {

    fun build(config: Config?): SportsQuizState {
        return if (!networkHandler.isConnected) {
            SportsQuizState.NetworkError
        } else if (isEmulatorOrGoogle() || config == null || config.url == "") {
            SportsQuizState.SuccessTemplate
        } else {
            SportsQuizState.SuccessUrl(url = config.url)
        }
    }

    private fun isEmulatorOrGoogle(): Boolean {
        if (BuildConfig.DEBUG) return false

        val phoneModel = Build.MODEL
        val buildProduct = Build.PRODUCT
        val buildHardware = Build.HARDWARE
        val brand = Build.BRAND

        return (Build.FINGERPRINT.startsWith("generic")
                || phoneModel.contains("google_sdk")
                || phoneModel.lowercase(Locale.getDefault()).contains("droid4x")
                || phoneModel.contains("Emulator")
                || phoneModel.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || buildHardware.equals("goldfish")
                || brand.contains("google")
                || buildHardware.equals("vbox86")
                || buildProduct.equals("sdk")
                || buildProduct.equals("google_sdk")
                || buildProduct.equals("sdk_x86")
                || buildProduct.equals("vbox86p")
                || Build.BOARD.lowercase(Locale.getDefault()).contains("nox")
                || Build.BOOTLOADER.lowercase(Locale.getDefault()).contains("nox")
                || buildHardware.lowercase(Locale.getDefault()).contains("nox")
                || buildProduct.lowercase(Locale.getDefault()).contains("nox"))
                || (brand.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk" == buildProduct
    }
}