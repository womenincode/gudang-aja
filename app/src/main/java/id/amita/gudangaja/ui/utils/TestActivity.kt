package id.amita.gudangaja.ui.utils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.amita.gudangaja.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.edtPhone.onFieldValid {

        }
    }
}