package id.amita.gudangaja.ui.welcome

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.amita.gudangaja.databinding.ActivityWelcomBinding

class WelcomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.edtTextField.onFieldValid {

        }
    }
}