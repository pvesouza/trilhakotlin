package br.com.example.pedro.myapplication

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

class MyService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        Log.i("New Token Service", FirebaseInstanceId.getInstance().toString())
    }
}