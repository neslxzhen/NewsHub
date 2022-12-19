package dev.zlong.komica_api.request._2cat

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import dev.zlong.komica_api.loadFile

internal class _2catThreadRequestBuilderTest {

    @Test
    fun `Test setRes expect successful`() {
        val req = _2catThreadRequestBuilder()
            .url("https://2cat.org/granblue/?res=963")
            .setRes("964")
            .build()
        assertEquals("https://2cat.org/granblue/?res=964".toHttpUrl(), req.url)
    }
}