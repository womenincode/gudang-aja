package id.amita.gudangaja.core.domain.model

import id.amita.gudangaja.core.data.source.remote.firebase.collection.DataUserCollection

data class UserData(
    val fullName: String,
    val phoneNumber: String,
    val email: String,
)

fun UserData.toCollection() = DataUserCollection(this.fullName, this.phoneNumber, this.email)
