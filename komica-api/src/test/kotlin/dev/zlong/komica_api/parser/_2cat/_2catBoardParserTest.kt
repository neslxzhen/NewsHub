package dev.zlong.komica_api.parser._2cat

import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import dev.zlong.komica_api.loadFile
import dev.zlong.komica_api.request._2cat._2catThreadRequestBuilder
import dev.zlong.komica_api.request.sora.SoraBoardRequestBuilder
import dev.zlong.komica_api.toResponseBody

internal class _2catBoardParserTest {

    @Test
    fun `Test _2catBoardParser expect successful`() {
        val builder = SoraBoardRequestBuilder()
        val parser = _2catBoardParser(_2catPostParser(_2catUrlParser(), _2catPostHeadParser(_2catUrlParser())), _2catThreadRequestBuilder())
        val posts = parser.parse(
            Jsoup.parse(loadFile("./src/test/html/org/2cat/BoardPage.html")).toResponseBody(),
            builder.url("https://2cat.org/granblue").build(),
        )
        assertEquals(8, posts.size)
    }
}