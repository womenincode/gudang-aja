package id.amita.gudangaja.core.utils

import com.google.android.gms.common.util.Base64Utils
import id.amita.gudangaja.core.BuildConfig
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object Security {

    private const val ALGORITHM = "AES"
    private const val TRANSFORMATION = "AES/CBC/PKCS5Padding"
    private val secretKey = SecretKeySpec(BuildConfig.SECRET_KEY.toByteArray(), ALGORITHM)
    private val iv = BuildConfig.IV.toByteArray()

    fun encrypt(plaintext: String): ByteArray {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, IvParameterSpec(iv))
        return cipher.doFinal(plaintext.toByteArray())
    }

    fun decrypt(cipherText: ByteArray): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iv))
        return String(cipher.doFinal(cipherText))
    }

    fun ByteArray.toBase64() = Base64Utils.encode(this)
    fun String.decryptBase64() = Base64Utils.decode(this)
}