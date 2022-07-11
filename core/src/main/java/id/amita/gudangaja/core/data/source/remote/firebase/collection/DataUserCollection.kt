package id.amita.gudangaja.core.data.source.remote.firebase.collection

data class DataUserCollection(
    val fullName: String? = null,
    val phoneNumber: String? = null,
    var email: String? = null,
) {
    companion object {
        val EMPTY = DataUserCollection()
    }
}

