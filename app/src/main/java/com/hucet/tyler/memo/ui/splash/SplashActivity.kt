package com.hucet.tyler.memo.ui.splash

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.hucet.tyler.memo.ui.main.MainActivity


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkGooglePlayServices()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun checkGooglePlayServices() {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val status = googleApiAvailability.isGooglePlayServicesAvailable(this)

        fun showBlockGooglePlayUpdateDialog() {
            if (status != ConnectionResult.SUCCESS) {
                val dialog = googleApiAvailability.getErrorDialog(this, status, -1)
                dialog.setOnDismissListener { finish() }
                dialog.show()
            }
        }
        googleApiAvailability.showErrorNotification(this, status)
    }
}