package br.com.example.pedro.examplegallery

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.SystemClock
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract

class PickingImageContract(private var context: Context,
                           private var permissionCode: Int = MainActivity.PERMISSION_CODE_IMAGE) :
    ActivityResultContract<String, Uri>() {

    private var imageUri: Uri? = null

    fun setPermissionCode(permissionCode: Int) {
        this.permissionCode = permissionCode
    }

    override fun createIntent(context: Context, input: String?): Intent {

        if (permissionCode == MainActivity.PERMISSION_CODE_IMAGE) {
            val intent: Intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI
            ).apply {
                type = input
            }
            Log.d("URI", "intent created")
            return intent
        } else {

            val values = ContentValues()
            values.put(MediaStore.Images.Media.TITLE, "Title: ${SystemClock.currentThreadTimeMillis()}")
            values.put(MediaStore.Images.Media.DESCRIPTION, R.string.label_created_image_description)
           imageUri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

            val intent: Intent = Intent().apply {
                action = MediaStore.ACTION_IMAGE_CAPTURE
                putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            }
            Log.d("URI", "intent created")
            return intent
        }

    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {

        Log.d("URI", "$resultCode")
        if (resultCode == Activity.RESULT_OK) {
            if (permissionCode == MainActivity.PERMISSION_CODE_IMAGE) {
                val data = intent?.data
                return data
            }else {
                return this.imageUri
            }
        }
        return null
    }
}