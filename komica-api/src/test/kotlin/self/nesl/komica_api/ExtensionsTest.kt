package self.nesl.komica_api

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import self.nesl.komica_api.model.KPostBuilder
import self.nesl.komica_api.model.KReplyTo


internal class ExtensionsTest {

    @Test
    fun `Test withHttps extension with same url expect successful`() =
        assertEquals("https://www.google.com", "https://www.google.com".withHttps())

    @Test
    fun `Test withHttps extension with start with double slash expect successful`() =
        assertEquals("https://www.google.com", "//www.google.com".withHttps())

    @Test
    fun `Test withHttps extension expect failure`() {
        assertThrows<ParseException> {
            "www.google.com".withHttps()
        }
    }

    @Test
    fun `Test withHttps extension with host expect successful`() =
        assertEquals("https://www.google.com", "www.google.com".withHttps())

    @Test
    fun `Test withHttps extension with path expect successful`() =
        assertEquals("https://www.google.com/search", "/search".withHttps("https://www.google.com"))

    @Test
    fun `Test withHttps extension with dot path expect successful`() =
        assertEquals("https://www.google.com/./search", "./search".withHttps("https://www.google.com"))

    @Test
    fun `Test replaceJpnWeekday extension expect successful`() =
        assertEquals("Mon Tue", "月 火".replaceJpnWeekday())

    @Test
    fun `Test replaceChiWeekday extension expect successful`() =
        assertEquals("Mon Tue", "一 二".replaceChiWeekday())

    @Test
    fun `Test toMillTimestamp extension expect successful`() =
        assertEquals(1662795827333L, "2022/09/10(Sat) 15:43:47.333".toMillTimestamp())

    @Test
    fun `Test toMillTimestamp with years with only two digits extension expect successful`() =
        assertEquals(1662795827000L, "22/09/10(Sat) 15:43:47".toMillTimestamp())
}

