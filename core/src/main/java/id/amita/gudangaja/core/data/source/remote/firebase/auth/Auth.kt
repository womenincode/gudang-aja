package id.amita.gudangaja.core.data.source.remote.firebase.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import id.amita.gudangaja.core.data.source.remote.firebase.collection.DataUserCollection
import id.amita.gudangaja.core.utils.FirebaseCollection
import id.amita.gudangaja.core.utils.Security
import id.amita.gudangaja.core.utils.Security.toBase64
import javax.inject.Inject

class Auth @Inject constructor(
    private val database: FirebaseFirestore,
    private val auth: FirebaseAuth
) {

    val currentUser: FirebaseUser? = auth.currentUser

    companion object {
        private const val TAG = "Auth"
    }

    fun createNewUser(
        dataUserCollection: DataUserCollection, password: String,
        onResult: (isSuccess: Boolean, message: String) -> Unit
    ) {
        val generatedEmail = generateEmail(dataUserCollection.fullName ?: "")
        dataUserCollection.email = generatedEmail
        getDataByPhone(dataUserCollection.phoneNumber ?: "") { _, dataUser ->
            Log.d(TAG, "createNewUser: $dataUser")
            if (dataUser == DataUserCollection.EMPTY) {
                auth.createUserWithEmailAndPassword(
                    generatedEmail,
                    Security.encrypt(password).toBase64()
                ).addOnSuccessListener {
                    Log.d(TAG, "createNewUser: success = ${it.user}")
                    storeNewUser(dataUserCollection, it.user?.uid ?: "", onResult)
                }.addOnFailureListener {
                    Log.d(TAG, "createNewUser: failed = ${it.message}")
                    onResult(false, it.message.toString())
                }
            } else onResult(false, "Phone number was already taken")
        }
    }

    private fun storeNewUser(
        dataUserCollection: DataUserCollection, uid: String,
        onResult: (isSuccess: Boolean, message: String) -> Unit
    ) {
        database.collection(FirebaseCollection.USER)
            .document(uid)
            .set(dataUserCollection)
            .addOnSuccessListener { onResult(true, "Success create new Account") }
            .addOnFailureListener { onResult(false, "Failed to create Account") }
    }

    fun login(
        dataUserCollection: DataUserCollection, password: String,
        onResult: (isSuccess: Boolean, message: String) -> Unit
    ) {
        getDataByPhone(dataUserCollection.phoneNumber ?: "") { isSuccess, dataUser ->
            if (isSuccess) auth.signInWithEmailAndPassword(
                dataUser.email ?: "",
                Security.encrypt(password).toBase64()
            )
                .addOnSuccessListener { onResult(true, "Welcome back ${dataUser.fullName}!") }
                .addOnFailureListener { onResult(false, it.message ?: "") }
            else onResult(false, "Your phone number is not registered yet")
        }
    }

    fun getDataByPhone(
        phoneNumber: String,
        onResult: (isSuccess: Boolean, dataUser: DataUserCollection) -> Unit
    ) {
        database.collection(FirebaseCollection.USER)
            .whereEqualTo("phoneNumber", phoneNumber)
            .get()
            .addOnSuccessListener {
                it.toObjects(DataUserCollection::class.java).let { data ->
                    if (data.isNotEmpty()) onResult(true, data.first())
                    else onResult(false, DataUserCollection.EMPTY)
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "getDataByPhone: failed = ${it.message}")
                onResult(false, DataUserCollection.EMPTY)
            }
    }

    fun getDataUser(
        uid: String,
        isRealtime: Boolean? = false,
        onResult: (isSuccess: Boolean, dataUser: DataUserCollection) -> Unit
    ) {
        val collection = database.collection(FirebaseCollection.USER).document(uid)
        if (isRealtime == true) collection.addSnapshotListener { value, error ->
            if (error != null) {
                Log.d(TAG, "getDataUser: failed = ${error.message}")
                onResult(false, DataUserCollection.EMPTY)
                return@addSnapshotListener
            }

            if (value != null && value.exists()) {
                value.toObject(DataUserCollection::class.java)?.let { onResult(true, it) }
            } else {
                Log.d(TAG, "getDataUser: null")
                onResult(false, DataUserCollection.EMPTY)

            }
        } else collection.get()
            .addOnSuccessListener { value ->
                value.toObject(DataUserCollection::class.java)?.let { onResult(true, it) }
            }
            .addOnFailureListener {
                Log.d(TAG, "getDataUser: failed = ${it.message}")
                onResult(false, DataUserCollection.EMPTY)
            }
    }

    private fun generateEmail(name: String) = name
        .lowercase().replace(" ", "")
        .plus(name.toCharArray().map { it.code }.last())
        .plus((100..999).random())
        .plus("@gmail.com")
}