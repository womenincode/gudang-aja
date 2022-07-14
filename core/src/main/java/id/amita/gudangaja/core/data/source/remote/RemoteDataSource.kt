package id.amita.gudangaja.core.data.source.remote

import android.util.Log
import id.amita.gudangaja.core.data.States
import id.amita.gudangaja.core.data.source.remote.firebase.auth.Auth
import id.amita.gudangaja.core.data.source.remote.firebase.collection.DataUserCollection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val auth: Auth
) {

    companion object {
        private const val TAG = "RemoteDataSource"
    }

    suspend fun createNewUser(dataUserCollection: DataUserCollection, password: String) =
        callbackFlow<States<String>> {
            trySend(States.loading())
            auth.createNewUser(dataUserCollection, password) { isSuccess, message ->
                if (isSuccess) trySend(States.success(message)) else trySend(States.failed(message))
            }
            awaitClose { Log.d(TAG, "createNewUser: close") }
        }.catch {
            Log.d(TAG, "createNewUser: failed = ${it.message}")
            emit(States.failed(it.message ?: ""))
        }.flowOn(Dispatchers.IO)

    suspend fun login(dataUserCollection: DataUserCollection, password: String) =
        callbackFlow<States<String>> {
            trySend(States.loading())
            auth.login(dataUserCollection, password) { isSuccess, message ->
                if (isSuccess) trySend(States.success(message)) else trySend(States.failed(message))
            }
            awaitClose { Log.d(TAG, "login: close") }
        }.catch {
            Log.d(TAG, "login: failed = ${it.message}")
            emit(States.failed(it.message ?: ""))
        }.flowOn(Dispatchers.IO)
}