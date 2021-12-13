package com.geras.fishistory.presentation.activities

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.geras.fishistory.data.Fish
import com.geras.fishistory.databinding.ActivityDataformBinding
import java.io.*

class DataFormActivity : AppCompatActivity() {

    private var _binding: ActivityDataformBinding? = null
    private val binding: ActivityDataformBinding
        get() = _binding!!
    private var path: String? = null
    private var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDataformBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backArrow.setOnClickListener {
            onBackPressed()
        }
        binding.addFishBtn.setOnClickListener {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1
            )

            if (binding.nameOfFish.text.isNullOrEmpty()) {
                Toast.makeText(this, "Please enter name of your fish", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.weightOfFish.text.isNullOrEmpty()) {
                Toast.makeText(this, "Please enter weight of your fish", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            if (binding.location.text.isNullOrEmpty()) {
                Toast.makeText(this, "Please enter fishing location", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            val weight = binding.weightOfFish.text.toString().toDoubleOrNull()
                ?: return@setOnClickListener

            val newFish = Fish(
                binding.nameOfFish.text.toString(),
                binding.location.text.toString(),
                weight,
                path
            )
            val resultData = Intent()
            resultData.putExtra(KEY_FISH, newFish)
            setResult(Activity.RESULT_OK, resultData)
            finish()

        }

        binding.addPhoto.setOnClickListener {
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA), REQUEST_CODE_CAMERA_PERMISSION
                )
            } else {
                startActivityForResult(
                    Intent(MediaStore.ACTION_IMAGE_CAPTURE),
                    REQUEST_CODE_PHOTO
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_CAMERA_PERMISSION && grantResults[0] == PERMISSION_GRANTED){
            startActivityForResult(
                Intent(MediaStore.ACTION_IMAGE_CAPTURE),
                REQUEST_CODE_PHOTO
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_PHOTO && resultCode == RESULT_OK) {
            if (data != null) {
                val bundle = data.extras
                if (bundle != null) {
                    val obj = data.extras!!["data"]
                    if (obj is Bitmap) {
                        bitmap = obj
                        saveToGallery(bitmap!!)
                        binding.previewPhoto.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }

    private fun saveToGallery(bitmap: Bitmap) {

        var outputStream: FileOutputStream? = null
        val dir = File(getExternalFilesDir(null), "/Pics")
        dir.mkdirs()

        val filename = String.format("%d.png", System.currentTimeMillis())
        val outFile = File(dir, filename)
        outFile.createNewFile()

        try {
            outputStream = FileOutputStream(outFile)

        } catch (e: Exception) {
            e.printStackTrace()
        }

        outputStream.use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream?.flush()
            outputStream?.close()
        }
        path = outFile.path
    }

    companion object {

        private const val REQUEST_CODE_PHOTO = 1
        private const val REQUEST_CODE_CAMERA_PERMISSION = 2
        private const val KEY_FISH = "Key fish"

        fun getCreateContract(): ActivityResultContract<Unit, Fish?> =
            object : ActivityResultContract<Unit, Fish?>() {
                override fun createIntent(context: Context, input: Unit?): Intent {
                    return Intent(context, DataFormActivity::class.java)
                }

                override fun parseResult(resultCode: Int, intent: Intent?): Fish? {
                    return if (resultCode == RESULT_OK) {
                        intent?.getSerializableExtra(KEY_FISH) as? Fish
                    } else null
                }
            }
    }
}