package id.amita.gudangaja.widget.button

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import id.amita.gudangaja.widget.R
import id.amita.gudangaja.widget.databinding.WidgetButtonFillBinding

class ButtonFill @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: WidgetButtonFillBinding by lazy {
        WidgetButtonFillBinding.inflate(
            LayoutInflater.from(context), this, true
        )
    }

    init {
        val buttonStyle = context.obtainStyledAttributes(attrs, R.styleable.button_attribute)
        val isButtonEnable =
            buttonStyle.getBoolean(R.styleable.button_attribute_android_enabled, true)

        binding.btnIcon.apply {
            val icon = buttonStyle.getResourceId(R.styleable.button_attribute_android_icon, -1)
            if (icon != -1) setImageResource(icon)
            isVisible = icon != -1
        }
        binding.containerLayout.gravity =
            when (buttonStyle.getInteger(R.styleable.button_attribute_alignment, 0)) {
                1 -> Gravity.START
                2 -> Gravity.END
                else -> Gravity.CENTER
            }

        binding.btnFill.apply {
            isEnabled = isButtonEnable
            setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    if (isButtonEnable) R.color.primary else R.color.gray
                )
            )
        }
        binding.btnText.text = buttonStyle.getText(R.styleable.button_attribute_android_text)
        buttonStyle.recycle()
    }

}