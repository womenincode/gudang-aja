package id.amita.gudangaja.core.domain.repository

import id.amita.gudangaja.core.data.States
import id.amita.gudangaja.core.domain.model.UserData
import kotlinx.coroutines.flow.Flow

interface ICoreRepository {

    suspend fun createNewUser(userData: UserData, password: String): Flow<States<String>>
    suspend fun login(userData: UserData, password: String): Flow<States<String>>
}