package id.amita.gudangaja.core

import id.amita.gudangaja.core.utils.Security
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testGenerateEmail() {
        val result = Security.encrypt("lala1234")
        println("Encrypt = $result")
        println("Decrypt = ${Security.decrypt(result)}")
    }
}