package com.geras.fishistory.presentation.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import com.geras.fishistory.R
import com.geras.fishistory.data.Fish
import com.geras.fishistory.databinding.ActivityDataformBinding
import java.io.File


private const val REQUEST_CODE_PHOTO = 1

class DataFormActivity : AppCompatActivity() {

    private lateinit var directory: File
    private lateinit var binding: ActivityDataformBinding
    private lateinit var bitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //createDirectory()
        binding = ActivityDataformBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
                binding.nameOfFish.text.toString(),
                binding.location.text.toString(),
                weight,
                R.drawable.carp5 // let it be
            )
            val resultData = Intent()
            resultData.putExtra(KEY_FISH, newFish)
            setResult(Activity.RESULT_OK, resultData)
            finish()
        }

        binding.addPhoto.setOnClickListener {
            startActivityForResult(
                Intent(MediaStore.ACTION_IMAGE_CAPTURE)/*.putExtra(
                    MediaStore.EXTRA_OUTPUT,
                    generateFileUri()*/,
                REQUEST_CODE_PHOTO
            )
        }
    }
/*
    private fun generateFileUri(): Uri {
        val file: File?
        file = File(
            directory.getPath() + "/" + "photo_"
                    + System.currentTimeMillis() + ".jpg"
        );
        return Uri.fromFile(file);
    }

    private fun createDirectory() {
        directory = File(
            Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            "fish_folder"
        )
        if (!directory.exists()) directory.mkdirs()
    }*/

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
                        binding.previewPhoto.setImageBitmap(obj)
                    }
                }
            }
        }
    }

    companion object {

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