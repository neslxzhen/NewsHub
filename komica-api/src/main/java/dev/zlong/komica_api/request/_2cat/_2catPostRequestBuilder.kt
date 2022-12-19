package dev.zlong.komica_api.request._2cat

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Request
import dev.zlong.komica_api.*
import dev.zlong.komica_api.request.RequestBuilder

class _2catPostRequestBuilder: RequestBuilder {
    private lateinit var builder: HttpUrl.Builder

    override fun url(url: String): _2catPostRequestBuilder {
        this.builder= url.toHttpUrl().newBuilder()
        return this
    }

    override fun url(url: HttpUrl): _2catPostRequestBuilder {
        this.builder= url.newBuilder()
        return this
    }

    fun setFragment(value: String?): _2catPostRequestBuilder {
        return if(value == null) removeFragment()
        else addFragment(value)
    }

    private fun addFragment(value: String): _2catPostRequestBuilder {
        if (hasFragment())
            removeFragment()
        builder = builder.fragment(value)
        return this
    }

    private fun hasFragment(): Boolean {
        return builder.build().fragment.isNullOrBlank().not()
    }

    private fun removeFragment(): _2catPostRequestBuilder {
        if(hasFragment())
            builder = builder.fragment(null)
        return this
    }

    override fun build(): Request {
        return Request.Builder()
            .url(builder.build())
            .build()
    }
}