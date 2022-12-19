package dev.zlong.komica_api.request.sora

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Request
import dev.zlong.komica_api.*
import dev.zlong.komica_api.request.RequestBuilder

class SoraPostRequestBuilder: RequestBuilder {
    private lateinit var builder: HttpUrl.Builder

    override fun url(url: String): SoraPostRequestBuilder {
        this.builder= url.toHttpUrl().newBuilder()
        return this
    }

    override fun url(url: HttpUrl): SoraPostRequestBuilder {
        this.builder= url.newBuilder()
        return this
    }

    fun setFragment(value: String?): SoraPostRequestBuilder {
        return if(value == null) removeFragment()
        else addFragment(value)
    }

    private fun addFragment(value: String): SoraPostRequestBuilder {
        if (hasFragment())
            removeFragment()
        builder = builder.fragment(value)
        return this
    }

    private fun hasFragment(): Boolean {
        return builder.build().fragment.isNullOrBlank().not()
    }

    private fun removeFragment(): SoraPostRequestBuilder {
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