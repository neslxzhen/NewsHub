package dev.zlong.komica_api.request.sora

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import dev.zlong.komica_api.loadFile

internal class SoraThreadRequestBuilderTest {

    @Test
    fun `Test setFragment expect successful`() {
        val req = SoraThreadRequestBuilder()
            .url("https://sora.komica.org/00/pixmicat.php?res=25214959")
            .setRes("25214960")
            .build()
        assertEquals("https://sora.komica.org/00/pixmicat.php?res=25214960".toHttpUrl(), req.url)
    }
}