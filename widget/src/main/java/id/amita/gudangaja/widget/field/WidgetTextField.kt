package id.amita.gudangaja.widget.field

import android.app.Activity
import android.content.Context
import android.text.InputFilter
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputLayout.END_ICON_PASSWORD_TOGGLE
import id.amita.gudangaja.widget.R
import id.amita.gudangaja.widget.databinding.WidgetTextFieldBinding
import id.amita.gudangaja.widget.utils.WidgetUtils.lifecycleOwner


class WidgetTextField @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val viewModel: WidgetTextFieldViewModel = WidgetTextFieldViewModel()
    private var customValidation = -1
    private var errorText = "Field not valid"

    private val binding: WidgetTextFieldBinding by lazy {
        WidgetTextFieldBinding.inflate(LayoutInflater.from(context), this, true)
    }

    init {
        val fieldStyle = context.obtainStyledAttributes(attrs, R.styleable.text_field_attribute)

        fieldStyle.getText(R.styleable.text_field_attribute_android_label).let {
            binding.tvLabelTextField.apply {
                text = it
                isVisible = it != null
            }
        }


        fieldStyle.getText(R.styleable.text_field_attribute_prefixText).let {
            if (it != null) binding.tilTextField.prefixText = it
        }
        fieldStyle.getText(R.styleable.text_field_attribute_suffixText).let {
            if (it != null) binding.tilTextField.suffixText = it
        }
        fieldStyle.getText(R.styleable.text_field_attribute_errorText)
            ?.let { errorText = it.toString() }
        binding.edtTextField.hint =
            fieldStyle.getText(R.styleable.text_field_attribute_android_hint)
        binding.tilTextField.helperText =
            fieldStyle.getText(R.styleable.text_field_attribute_helperText)
        customValidation = fieldStyle.getInt(R.styleable.text_field_attribute_customValidation, -1)

        fieldStyle.getInt(
            R.styleable.text_field_attribute_android_inputType,
            InputType.TYPE_CLASS_TEXT
        ).let { inputType ->
            val chosenInputType =
                if (inputType == 129 || inputType == InputType.TYPE_NUMBER_VARIATION_PASSWORD) {
                    binding.tilTextField.endIconMode = END_ICON_PASSWORD_TOGGLE
                    VALIDATION_TEXT
                } else inputType
            if (inputType == InputType.TYPE_CLASS_PHONE)
                binding.edtTextField.filters += InputFilter.LengthFilter(15)
            binding.edtTextField.inputType = chosenInputType
            setUpValidation(chosenInputType)
        }

        fieldStyle.recycle()
    }

    val text get() = binding.edtTextField.text.toString()

    private fun setUpValidation(inputType: Int) {
        when (if (customValidation != -1) getCustomInputType(customValidation) else inputType) {
            InputType.TYPE_CLASS_TEXT -> textValidate(viewModel::onFieldValidChange)
            InputType.TYPE_CLASS_PHONE -> phoneValidate(viewModel::onFieldValidChange)
            InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS -> emailValidate(viewModel::onFieldValidChange)
        }
    }


    private fun textValidate(onValid: (Boolean) -> Unit) {
        binding.edtTextField.doAfterTextChanged {
            onValid(it != null && it.isNotBlank())
        }
    }

    private fun phoneValidate(onValid: (Boolean) -> Unit) {
        binding.edtTextField.doAfterTextChanged {
            val input = it.toString()
            if (input.isNotEmpty() && input[0] == '0') it?.replace(0, 1, "")
            onValid(it != null && it.isNotBlank() && it.length > 9)
        }
    }

    private fun emailValidate(onValid: (Boolean) -> Unit) {
        binding.edtTextField.doAfterTextChanged {
            onValid(android.util.Patterns.EMAIL_ADDRESS.matcher(it.toString().trim()).matches())
        }
    }

    fun onFieldValid(onValid: (Boolean) -> Unit) {
        (context as Activity).lifecycleOwner?.let { owner ->
            viewModel.isFieldValid.observe(owner) {
                showError(!it)
                onValid(it)
            }
        }
    }

    private fun showError(showError: Boolean) {
        binding.tilTextField.apply {
            error = errorText
            isErrorEnabled = showError
        }
    }

    private fun getCustomInputType(type: Int): Int {
        return when (type) {
            VALIDATION_TEXT -> InputType.TYPE_CLASS_TEXT
            VALIDATION_EMAIL -> InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            VALIDATION_PHONE -> InputType.TYPE_CLASS_PHONE
            VALIDATION_PASSWORD -> InputType.TYPE_TEXT_VARIATION_PASSWORD
            else -> InputType.TYPE_CLASS_TEXT
        }
    }

    companion object {
        const val VALIDATION_TEXT = 1
        const val VALIDATION_EMAIL = 2
        const val VALIDATION_PHONE = 3
        const val VALIDATION_PASSWORD = 4
    }
}