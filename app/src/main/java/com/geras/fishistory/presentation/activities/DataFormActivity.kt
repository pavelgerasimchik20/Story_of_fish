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
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import coil.load
import com.geras.fishistory.FishHistoryApplication
import com.geras.fishistory.R
import com.geras.fishistory.data.Fish
import com.geras.fishistory.databinding.ActivityDataformBinding
import com.geras.fishistory.presentation.vm.DataFormViewModel
import java.io.*
import java.util.*

class DataFormActivity : AppCompatActivity() {

    private var _binding: ActivityDataformBinding? = null
    private val binding: ActivityDataformBinding
        get() = _binding!!
    private var bitmap: Bitmap? = null
    private var path: String? = null

    private val viewModel: DataFormViewModel by viewModels {
        (application as FishHistoryApplication).appComponent.getViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDataformBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val inputData = intent?.getSerializableExtra(CURRENT_FISH) as? Fish
        inputData?.let { fish ->
            binding.nameOfFish.setText(fish.name)
            binding.weightOfFish.setText(fish.weight.toString())
            binding.location.setText(fish.location)
            fish.photoPath?.let {
                binding.previewPhoto.load(File(it))
                path = it
            }
        }

        binding.addFishBtn.text = if(inputData == null) {
            getString(R.string.add_fish_button)
        } else {
            getString(R.string.update_fish_button)
        }

        binding.backArrow.setOnClickListener {
            onBackPressed()
        }
        binding.addFishBtn.setOnClickListener {

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
                id = inputData?.id ?: UUID.randomUUID().toString(),
                name = binding.nameOfFish.text.toString(),
                location = binding.location.text.toString(),
                weight = weight,
                photoPath = path
            )
            val resultData = Intent()
            resultData.putExtra(KEY_FISH, newFish)
            setResult(Activity.RESULT_OK, resultData)
            finish()

        }

        binding.addPhoto.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) != PERMISSION_GRANTED
            ) {
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

        if (requestCode == REQUEST_CODE_CAMERA_PERMISSION && grantResults[0] == PERMISSION_GRANTED) {
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
                        saveToLocalStorage(bitmap!!)
                        binding.previewPhoto.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }

    private fun saveToLocalStorage(bitmap: Bitmap) {

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
        path?.let { File(it).delete() }
        path = outFile.path
    }

    companion object {

        private const val REQUEST_CODE_PHOTO = 1
        private const val REQUEST_CODE_CAMERA_PERMISSION = 2
        private const val KEY_FISH = "Key fish"
        private const val CURRENT_FISH = "CURRENT_FISH"

        fun getCreateContract(): ActivityResultContract<Fish?, Fish?> =
            object : ActivityResultContract<Fish?, Fish?>() {
                override fun createIntent(context: Context, input: Fish?): Intent {
                    val intent = Intent(context, DataFormActivity::class.java)
                    input?.let { intent.putExtra(CURRENT_FISH, it) }
                    return intent
                }

                override fun parseResult(resultCode: Int, intent: Intent?): Fish? {
                    return if (resultCode == RESULT_OK) {
                        intent?.getSerializableExtra(KEY_FISH) as? Fish
                    } else null
                }
            }
    }
}