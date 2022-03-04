package br.com.example.pedro.examplegallery

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var pickButton: Button
    private lateinit var imageView: ImageView
    private lateinit var activityLauncher: ActivityResultLauncher<String>
    private lateinit var openCameraButton: Button

    companion object {
        const val PERMISSION_CODE_IMAGE = 1000
        const val PERMISSION_CODE_CAMERA = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pickButton = findViewById(R.id.button_pick_image)
        imageView = findViewById(R.id.imageView_imagemain)
        openCameraButton = findViewById(R.id.button_open_cam)
        val pickinImageContract = PickingImageContract(this)

        // An intent to pick an image from gallery
        activityLauncher = registerForActivityResult(pickinImageContract) { result ->

            if (result != null) {
                imageView.setImageURI(result)
            } else {
                Log.d("MainActivity", "wrong")
            }
        }

        // Action of picking a photo from gallery
        pickButton.setOnClickListener {
            pickinImageContract.setPermissionCode(PERMISSION_CODE_IMAGE)
            launchPermission(PERMISSION_CODE_IMAGE)
        }

        // Action of take a picture
        openCameraButton.setOnClickListener {
            pickinImageContract.setPermissionCode(PERMISSION_CODE_CAMERA)
            launchPermission(PERMISSION_CODE_CAMERA)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE_IMAGE -> {
                if ((grantResults.isEmpty()) &&
                    (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    pickImageFromGallery()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }

            PERMISSION_CODE_CAMERA -> {
                if ( (grantResults.size > 1)) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        (grantResults[1] == PackageManager.PERMISSION_GRANTED)) {

                        pickImageFromCamera()
                    }
                    else {
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }

            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    // Picks an Image from gallery and returns it as a bitmap
    private fun pickImageFromGallery() {
        Log.d("MainActivity", "pickCalled")
        this.activityLauncher.launch("image/*")
    }

    private fun launchPermission(permissionCode: Int) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var permissionType: Int? = null
            if (permissionCode == PERMISSION_CODE_CAMERA) {
                val permissionCamera = checkSelfPermission(android.Manifest.permission.CAMERA)
                val permissionWriteStorage =
                    checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                if (permissionCamera == PackageManager.PERMISSION_DENIED
                    || permissionWriteStorage == PackageManager.PERMISSION_DENIED
                ) {
                    permissionType = PackageManager.PERMISSION_DENIED
                }
            } else if (permissionCode == PERMISSION_CODE_IMAGE) {
                permissionType =
                    checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            // Check for permission
            if (permissionType == PackageManager.PERMISSION_DENIED) {

                val permissionAlert =
                    if (permissionCode == PERMISSION_CODE_CAMERA) {
                        arrayOf(
                            android.Manifest.permission.CAMERA,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    } else {
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    }

                requestPermissions(permissionAlert, permissionCode)

            } else {
                if (permissionCode == PERMISSION_CODE_IMAGE) {
                    pickImageFromGallery()
                } else {
                    pickImageFromCamera()
                }
            }
        } else if (permissionCode == PERMISSION_CODE_IMAGE) {
            pickImageFromGallery()
        } else {
            pickImageFromCamera()
        }
    }

    private fun pickImageFromCamera() {
        Log.d("Main", "Requisição Câmera")
        this.activityLauncher.launch("")
    }
}