package dev.zlong.komica_api.parser.sora

import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import dev.zlong.komica_api.loadFile
import dev.zlong.komica_api.request.sora.SoraBoardRequestBuilder
import dev.zlong.komica_api.request.sora.SoraThreadRequestBuilder
import dev.zlong.komica_api.toResponseBody

internal class SoraBoardParserTest {

    @Test
    fun `Test parse posts with 綜合 BoardPage html expect successful`() {
        val builder = SoraBoardRequestBuilder()
        val parser = SoraBoardParser(SoraPostParser(SoraUrlParser(), SoraPostHeadParser()), SoraThreadRequestBuilder())
        val posts = parser.parse(
            Jsoup.parse(loadFile("./src/test/html/org/komica/sora/BoardPage.html")).toResponseBody(),
            builder.url("https://sora.komica.org/00").build(),
        )
        assertEquals(15, posts.size)
    }

    @Test
    fun `Test parse posts with 2cat BoardPage html expect successful`() {
        val builder = SoraBoardRequestBuilder()
        val parser = SoraBoardParser(SoraPostParser(SoraUrlParser(), SoraPostHeadParser()), SoraThreadRequestBuilder())
        val posts = parser.parse(
            Jsoup.parse(loadFile("./src/test/html/org/komica/2cat/BoardPage.html")).toResponseBody(),
            builder.url("https://2cat.komica.org/~tedc21thc/new").build(),
        )
        assertEquals(11, posts.size)
    }
}