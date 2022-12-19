package dev.zlong.komica_api.request

import okhttp3.HttpUrl
import okhttp3.Request

interface RequestBuilder {
    fun url(url: String): RequestBuilder
    fun url(url: HttpUrl): RequestBuilder
    fun build(): Request
}