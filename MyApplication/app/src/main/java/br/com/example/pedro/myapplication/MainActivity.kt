package br.com.example.pedro.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import br.com.example.pedro.myapplication.utils.NotificationUtils
import com.google.firebase.iid.FirebaseInstanceId

class MainActivity : AppCompatActivity() {

    private lateinit var notificationButton: Button
    private lateinit var notificationUtils: NotificationUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notificationButton = findViewById(R.id.btn_main_notification) as Button
        notificationUtils = NotificationUtils(this)

        notificationButton.setOnClickListener {
            this.notificationUtils.showNotification("1234", "Bootcamp Android", "Kotlin Android course")
        }

        Log.d("Token", FirebaseInstanceId.getInstance().token.toString())
    }
}