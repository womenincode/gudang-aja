package id.amita.gudangaja.ui.register

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.amita.gudangaja.core.data.States
import id.amita.gudangaja.core.data.source.CoreRepository
import id.amita.gudangaja.core.domain.model.UserData
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: CoreRepository
) : ViewModel() {

    private val _isAccountCreated = MutableLiveData<String>()
    val isAccountCreated get() = _isAccountCreated

    fun createNewAccount(userData: UserData, password: String) {
        viewModelScope.launch {
            repository.createNewUser(userData, password).collect {
                when (it) {
                    is States.Loading -> {
                        Log.d("TAG", "createNewAccount: loading")
                    }
                    is States.Success -> {
                        it.data.let { data ->
                            Log.d("TAG", "createNewAccount: $data")
                            _isAccountCreated.value = data
                        }
                    }
                    is States.Failed -> {
                        Log.d("TAG", "createNewAccount: failed ${it.message}")
                        _isAccountCreated.value = it.message
                    }
                }
            }
        }
    }
}