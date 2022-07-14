package id.amita.gudangaja.widget.button

import id.amita.gudangaja.widget.field.WidgetTextField

object ButtonUtils {

    private lateinit var listStates: MutableList<Boolean>

    fun ButtonFill.setValidateButton(vararg fields: WidgetTextField) {
        listStates = mutableListOf()
        repeat(fields.size) { listStates.add(false) }.also {
            fields.forEachIndexed { index, widgetTextField ->
                widgetTextField.onFieldValid { validateButton(this, index, it) }
            }
        }
    }

    private fun validateButton(buttonFill: ButtonFill, index: Int, state: Boolean) {
        listStates[index] = state
        (listStates.count { it } == listStates.size).let { valid -> buttonFill.isEnable = valid }
    }
}