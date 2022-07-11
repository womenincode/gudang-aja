package id.amita.gudangaja.ui.login

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
class LoginViewModel @Inject constructor(
    private val repository: CoreRepository
) : ViewModel() {

    private val _loginMessage = MutableLiveData<String>()
    val loginMessage get() = _loginMessage

    fun login(userData: UserData, password: String) {
        viewModelScope.launch {
            repository.login(userData, password).collect {
                when (it) {
                    is States.Loading -> {
                        Log.d("TAG", "login: loading")
                    }
                    is States.Success -> {
                        it.data.let { data ->
                            Log.d("TAG", "login: $data")
                            _loginMessage.value = data
                        }
                    }
                    is States.Failed -> {
                        Log.d("TAG", "login: failed ${it.message}")
                        _loginMessage.value = it.message
                    }
                }
            }
        }
    }
}