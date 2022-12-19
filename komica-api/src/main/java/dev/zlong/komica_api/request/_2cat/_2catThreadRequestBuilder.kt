package dev.zlong.komica_api.request._2cat

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Request
import dev.zlong.komica_api.*
import dev.zlong.komica_api.request.RequestBuilder

class _2catThreadRequestBuilder: RequestBuilder {
    private lateinit var builder: HttpUrl.Builder

    override fun url(url: String): _2catThreadRequestBuilder {
        this.builder= url.toHttpUrl().newBuilder()
        return this
    }

    override fun url(url: HttpUrl): _2catThreadRequestBuilder {
        this.builder = url.newBuilder()
        return this
    }

    fun setRes(res: String?): _2catThreadRequestBuilder {
        return if(res == null) removeQuery("res")
        else addQuery("res", res)
    }

    private fun addQuery(queryName: String, value: String): _2catThreadRequestBuilder {
        if (hasQuery(queryName))
            removeQuery(queryName)
        builder = builder.addQueryParameter(queryName, value)
        return this
    }

    private fun hasQuery(queryName: String): Boolean {
        return builder.build().queryParameter(queryName).isNullOrBlank().not()
    }

    private fun removeQuery(queryName: String): _2catThreadRequestBuilder {
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