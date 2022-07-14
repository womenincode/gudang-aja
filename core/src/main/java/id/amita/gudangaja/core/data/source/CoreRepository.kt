package id.amita.gudangaja.core.data.source

import id.amita.gudangaja.core.data.source.remote.RemoteDataSource
import id.amita.gudangaja.core.domain.model.UserData
import id.amita.gudangaja.core.domain.model.toCollection
import id.amita.gudangaja.core.domain.repository.ICoreRepository
import javax.inject.Inject

class CoreRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : ICoreRepository {

    override suspend fun createNewUser(userData: UserData, password: String) =
        remoteDataSource.createNewUser(userData.toCollection(), password)

    override suspend fun login(userData: UserData, password: String) =
        remoteDataSource.login(userData.toCollection(), password)
}