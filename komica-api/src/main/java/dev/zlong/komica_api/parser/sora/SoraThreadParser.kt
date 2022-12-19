package dev.zlong.komica_api.parser.sora

import org.jsoup.nodes.Element
import dev.zlong.komica_api.installThreadTag
import dev.zlong.komica_api.model.KPost
import dev.zlong.komica_api.model.filterReplyToIs
import dev.zlong.komica_api.parser.Parser
import dev.zlong.komica_api.request.sora.SoraPostRequestBuilder
import dev.zlong.komica_api.toResponseBody
import okhttp3.HttpUrl
import okhttp3.Request
import okhttp3.ResponseBody
import org.jsoup.Jsoup

class SoraThreadParser(
    private val postParser: Parser<KPost>,
    private val postRequestBuilder: SoraPostRequestBuilder,
): Parser<List<KPost>> {
    override fun parse(res: ResponseBody, req: Request): List<KPost> {
        val source = Jsoup.parse(res.string())
        val url = req.url
        return listOf(parseHeadPost(source, url)).plus(parseReplies(source, url))
    }

    private fun parseHeadPost(source: Element, url: HttpUrl): KPost {
        val req = postRequestBuilder.url(url).build()
        return postParser.parse(source.selectFirst("div.threadpost").toResponseBody(), req)
    }

    private fun parseReplies(source: Element, url: HttpUrl): List<KPost> {
        val threads = source.selectFirst("#threads").installThreadTag().select("div.thread")
        val posts = threads.select("div.reply").map { reply_ele ->
            val fragment = reply_ele.attr("id") // r12345678
            val postId = fragment.substring(1)
            val req = postRequestBuilder.url(url).setFragment("r$postId").build()
            postParser.parse(reply_ele.toResponseBody(), req)
        }
        setRepliesCount(posts)
        return posts
    }

    private fun setRepliesCount(posts: List<KPost>) {
        for (post in posts) {
            post.replies = posts.filterReplyToIs(post.id).size
        }
    }
}

