package com.geras.fishistory.ui.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import com.geras.fishistory.R
import com.geras.fishistory.data.dataclasses.Fish
import com.geras.fishistory.databinding.ActivityDataformBinding

class DataFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDataformBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

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
                R.drawable.carp5       // It`s a temporary solution, until the button doesn`t add a photo of a new fish
            )
            val resultData = Intent()
            resultData.putExtra(KEY_FISH, newFish)
            setResult(Activity.RESULT_OK, resultData)
            finish()
        }
        binding.addPhoto.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivity(gallery)

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