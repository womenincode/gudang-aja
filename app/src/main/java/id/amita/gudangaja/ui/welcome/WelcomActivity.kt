package id.amita.gudangaja.ui.welcome

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.amita.gudangaja.databinding.ActivityWelcomBinding
import id.amita.gudangaja.ui.login.LoginActivity
import id.amita.gudangaja.ui.register.RegisterActivity

class WelcomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnLogin.setOnClickListener {
                startActivity(Intent(this@WelcomActivity, LoginActivity::class.java))
            }
            btnRegister.setOnClickListener {
                startActivity(Intent(this@WelcomActivity, RegisterActivity::class.java))
            }
        }
    }
}