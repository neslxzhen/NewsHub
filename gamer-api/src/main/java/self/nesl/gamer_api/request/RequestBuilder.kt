package self.nesl.gamer_api.request

import okhttp3.HttpUrl
import okhttp3.Request

interface RequestBuilder {
    fun url(url: String): RequestBuilder
    fun url(url: HttpUrl): RequestBuilder
    fun setPageReq(page: Int?): RequestBuilder
    fun build(): Request
}