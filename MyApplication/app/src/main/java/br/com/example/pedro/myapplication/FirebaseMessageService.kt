package br.com.example.pedro.myapplication

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import br.com.example.pedro.myapplication.utils.NotificationUtils
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessageService : FirebaseMessagingService() {

    companion object{
        const val FIREBASE_MESSAGE = "FirebaseMessageService"
    }

    override fun onMessageReceived(p0: RemoteMessage) {

        Log.d(FIREBASE_MESSAGE, p0.from.toString())

        if (p0.notification != null) {
            val notification = NotificationUtils(this)
            notification.showNotification("1234",
                p0.notification?.title.toString(),
                p0.notification?.body.toString())
        }
    }

}