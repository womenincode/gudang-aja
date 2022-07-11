package id.amita.gudangaja.core.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.amita.gudangaja.core.data.source.CoreRepository
import id.amita.gudangaja.core.data.source.remote.RemoteDataSource
import id.amita.gudangaja.core.data.source.remote.firebase.auth.Auth

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    fun provideFirebaseDatabase() = Firebase.firestore

    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    fun provideAuth(database: FirebaseFirestore, auth: FirebaseAuth) = Auth(database, auth)

    @Provides
    fun provideRemoteDataSource(
        auth: Auth
    ) = RemoteDataSource(auth)

    @Provides
    fun provideRepository(
        remoteDataSource: RemoteDataSource
    ) = CoreRepository(remoteDataSource)
}