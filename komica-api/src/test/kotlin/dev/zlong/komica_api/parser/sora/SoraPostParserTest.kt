package dev.zlong.komica_api.parser.sora

import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import dev.zlong.komica_api.loadFile
import dev.zlong.komica_api.request.sora.SoraPostRequestBuilder
import dev.zlong.komica_api.toResponseBody

internal class SoraPostParserTest {

    @Test
    fun `Test parse post with 綜合 ReplyPost html expect successful`() {
        val builder = SoraPostRequestBuilder()
        val parser = SoraPostParser(SoraUrlParser(), SoraPostHeadParser())
        val post = parser.parse(
            Jsoup.parse(loadFile("./src/test/html/org/komica/sora/ReplyPost.html")).toResponseBody(),
            builder.url( "https://sora.komica.org/00/pixmicat.php?res=25208017").build(),
        )
        assertEquals("25208017", post.id)
    }

    @Test
    fun `Test parse post with 2cat ReplyPost html expect successful`() {
        val builder = SoraPostRequestBuilder()
        val parser = SoraPostParser(SoraUrlParser(), SoraPostHeadParser())
        val post = parser.parse(
            Jsoup.parse(loadFile("./src/test/html/org/komica/2cat/ReplyPost.html")).toResponseBody(),
            builder.url( "https://2cat.komica.org/~tedc21thc/new/pixmicat.php?res=4003068").build(),
        )
        assertEquals("4003068", post.id)
    }
}