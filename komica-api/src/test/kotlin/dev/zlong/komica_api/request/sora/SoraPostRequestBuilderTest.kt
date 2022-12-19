package dev.zlong.komica_api.request.sora

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import dev.zlong.komica_api.loadFile

internal class SoraPostRequestBuilderTest {

    @Test
    fun `Test setFragment expect successful`() {
        val req = SoraPostRequestBuilder()
            .url("https://sora.komica.org/00/pixmicat.php?res=25214959")
            .setFragment("r23588")
            .build()
        assertEquals("https://sora.komica.org/00/pixmicat.php?res=25214959#r23588".toHttpUrl(), req.url)
    }
}