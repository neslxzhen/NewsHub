package dev.zlong.komica_api.request.sora

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Request
import dev.zlong.komica_api.*
import dev.zlong.komica_api.request.RequestBuilder

class SoraThreadRequestBuilder: RequestBuilder {
    private lateinit var builder: HttpUrl.Builder

    override fun url(url: String): SoraThreadRequestBuilder {
        this.builder= url.toHttpUrl().newBuilder()
        return this
    }

    override fun url(url: HttpUrl): SoraThreadRequestBuilder {
        this.builder = if (!url.isFile("pixmicat", "php")) {
            url.newBuilder().addFilename("pixmicat", "php")
        } else {
            url.newBuilder()
        }
        return this
    }

    fun setRes(res: String?): SoraThreadRequestBuilder {
        return if(res == null) removeQuery("res")
        else addQuery("res", res)
    }

    private fun addQuery(queryName: String, value: String): SoraThreadRequestBuilder {
        if (hasQuery(queryName))
            removeQuery(queryName)
        builder = builder.addQueryParameter(queryName, value)
        return this
    }

    private fun hasQuery(queryName: String): Boolean {
        return builder.build().queryParameter(queryName).isNullOrBlank().not()
    }

    private fun removeQuery(queryName: String): SoraThreadRequestBuilder {
        if(hasQuery(queryName))
            builder = builder.removeAllQueryParameters(queryName)
        return this
    }

    override fun build(): Request {
        return Request.Builder()
            .url(builder.build())
            .build()
    }
}