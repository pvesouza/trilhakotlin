package br.com.example.pedro.myapplication.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import br.com.example.pedro.myapplication.MainActivity
import br.com.example.pedro.myapplication.R

class NotificationUtils(private val context: Context) {

    private lateinit var notificationChannel: NotificationChannel
    private lateinit var notificationManager: NotificationManager
    private lateinit var builder: NotificationCompat.Builder

    fun showNotification(channelId: String, title: String, message: String){
        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0,
            intent, PendingIntent.FLAG_UPDATE_CURRENT)

        // Modern notifications
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d("NOtification", "sdk")
            // All notifications will appear on the screen
            notificationChannel = NotificationChannel(channelId, message,
                NotificationManager.IMPORTANCE_HIGH).apply {
                    lightColor = Color.BLUE
                enableVibration(true)
            }

            notificationManager.createNotificationChannel(notificationChannel)
            // Responsible for throws the notification
            builder = NotificationCompat.Builder(context, channelId).apply {
                setSmallIcon(R.drawable.images)
                setContentTitle(title)
                setContentText(message)
                setAutoCancel(true)
                setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE))
                setContentIntent(pendingIntent)
            }
            notificationManager.notify(channelId.toInt(), builder.build())
        }else{
            Log.d("NOtification", " no sdk")
        }
    }
}