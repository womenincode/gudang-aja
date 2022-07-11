package id.amita.gudangaja.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import id.amita.gudangaja.core.data.source.remote.firebase.auth.Auth
import id.amita.gudangaja.databinding.ActivitySplashBinding
import id.amita.gudangaja.ui.welcome.WelcomActivity
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var auth: Auth

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(mainLooper).postDelayed({
            auth.currentUser.let {
                Log.d("TAG", "currentUser: $it")
            }
            startActivity(
                Intent(this, WelcomActivity::class.java)
            )
            finish()
            //pindah ke mana
        }, 3000L)
    }
}