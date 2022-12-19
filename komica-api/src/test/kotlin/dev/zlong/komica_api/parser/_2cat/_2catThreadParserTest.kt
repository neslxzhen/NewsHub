package dev.zlong.komica_api.parser._2cat

import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import dev.zlong.komica_api.loadFile
import dev.zlong.komica_api.request._2cat._2catPostRequestBuilder
import dev.zlong.komica_api.request._2cat._2catThreadRequestBuilder
import dev.zlong.komica_api.toResponseBody

internal class _2catThreadParserTest {

    @Test
    fun `Test _2catThreadParser expect successful`() {
        val builder = _2catThreadRequestBuilder()
        val parser = _2catThreadParser(_2catPostParser(_2catUrlParser(), _2catPostHeadParser(_2catUrlParser())), _2catPostRequestBuilder())
        val thread = parser.parse(
            Jsoup.parse(loadFile("./src/test/html/org/2cat/ThreadPage.html")).toResponseBody(),
            builder.url("https://2cat.org/granblue/?res=963").build(),
        )
        assertEquals(6, thread.size)
    }
}