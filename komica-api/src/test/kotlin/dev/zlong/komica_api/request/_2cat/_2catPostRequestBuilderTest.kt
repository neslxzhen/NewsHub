package dev.zlong.komica_api.request._2cat

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import dev.zlong.komica_api.loadFile

internal class _2catPostRequestBuilderTest {

    @Test
    fun `Test setFragment expect successful`() {
        val req = _2catPostRequestBuilder()
            .url("https://2cat.org/granblue/?res=963#r23587")
            .setFragment("r23588")
            .build()
        assertEquals("https://2cat.org/granblue/?res=963#r23588".toHttpUrl(), req.url)
    }
}