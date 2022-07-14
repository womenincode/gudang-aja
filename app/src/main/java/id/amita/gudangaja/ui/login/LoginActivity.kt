package id.amita.gudangaja.ui.login

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import id.amita.gudangaja.core.domain.model.UserData
import id.amita.gudangaja.databinding.ActivityLoginBinding
import id.amita.gudangaja.widget.button.ButtonUtils.setValidateButton

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val model: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnLogin.setValidateButton(fieldPhone, fieldPassword)
            btnLogin.setOnClickListener {
                model.login(
                    UserData("", "62${fieldPhone.text}", ""),
                    fieldPassword.text
                )
            }
        }
        model.loginMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}