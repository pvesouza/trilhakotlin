package br.com.example.pedro.clientlist

import android.graphics.Bitmap
import java.io.Serializable

class Contact(val name: String,
              val phone : String,
              private var image:Bitmap? = null) : Serializable {

    fun setImage(image:Bitmap){
        this.image = image
    }

    fun getImage() : Bitmap? {
        return this.image
    }

}
