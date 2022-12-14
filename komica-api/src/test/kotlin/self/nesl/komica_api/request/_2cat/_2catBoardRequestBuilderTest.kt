package self.nesl.komica_api.request._2cat

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import self.nesl.komica_api.loadFile

internal class _2catBoardRequestBuilderTest {

    @Test
    fun `Test setPageReq expect successful`() {
        val req = _2catBoardRequestBuilder()
            .url("https://2cat.org/granblue")
            .setPageReq(1)
            .build()
        assertEquals("https://2cat.org/granblue?page=1".toHttpUrl(), req.url)
    }

    @Test
    fun `Test setPageReq with url with page setPageReq expect successful`() {
        val req = _2catBoardRequestBuilder()
            .url("https://2cat.org/granblue?page=2")
            .setPageReq(1)
            .build()
        assertEquals("https://2cat.org/granblue?page=1".toHttpUrl(), req.url)
    }
}