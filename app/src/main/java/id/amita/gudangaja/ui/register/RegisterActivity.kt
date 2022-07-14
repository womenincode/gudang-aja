package id.amita.gudangaja.ui.register

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import id.amita.gudangaja.core.domain.model.UserData
import id.amita.gudangaja.databinding.ActivityRegisterBinding
import id.amita.gudangaja.widget.button.ButtonUtils.setValidateButton

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val model: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            btnRegister.setValidateButton(fieldName, fieldPhone, fieldPassword)
            btnRegister.setOnClickListener {
                model.createNewAccount(
                    UserData(fieldName.text, "62${fieldPhone.text}", ""),
                    fieldPassword.text
                )
            }
        }

        model.isAccountCreated.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}