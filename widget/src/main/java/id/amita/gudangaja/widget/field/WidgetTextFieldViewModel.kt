package id.amita.gudangaja.widget.field

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WidgetTextFieldViewModel : ViewModel() {

    private val _isFieldValid = MutableLiveData<Boolean>()
    val isFieldValid get() = _isFieldValid

    fun onFieldValidChange(isValid: Boolean) {
        _isFieldValid.value = isValid
    }
}