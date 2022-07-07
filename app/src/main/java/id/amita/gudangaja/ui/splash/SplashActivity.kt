package id.amita.gudangaja.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import id.amita.gudangaja.R
import id.amita.gudangaja.databinding.ActivitySplashBinding
import id.amita.gudangaja.ui.welcome.WelcomActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(mainLooper).postDelayed({
            startActivity(
                Intent(this, WelcomActivity::class.java)
            )
            finish()
           //pindah ke mana
        }, 3000L)
    }
}