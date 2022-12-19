package dev.zlong.komica_api.request._2cat

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Request
import dev.zlong.komica_api.*
import dev.zlong.komica_api.request.RequestBuilder
import dev.zlong.komica_api.request.sora.SoraBoardRequestBuilder

class _2catBoardRequestBuilder: RequestBuilder {
    private lateinit var builder: HttpUrl.Builder

    override fun url(url: String): _2catBoardRequestBuilder {
        this.builder= url.toHttpUrl().newBuilder()
        return this
    }

    override fun url(url: HttpUrl): _2catBoardRequestBuilder {
        this.builder= url.newBuilder()
        return this
    }

    fun setPageReq(page: Int?): _2catBoardRequestBuilder {
        builder = builder
            .apply {
                if (page.isZeroOrNull()) {
                    removeFilename("htm")
                } else {
                    val _httpUrl = builder.build()
                    val extra = _httpUrl.pathSegments - _httpUrl.toKBoard().url.toHttpUrl().pathSegments
                    if (extra.isEmpty()) {
                        addFilename("$page", "htm")
                    } else {
                        setFilename("${page}.htm")
                    }
                }
            }
        return this
    }

    override fun build(): Request {
        return Request.Builder()
            .url(builder.build())
            .build()
    }
}