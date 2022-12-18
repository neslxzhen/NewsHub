package self.nesl.gamer_api.parser

import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Request
import org.jsoup.nodes.Element
import self.nesl.gamer_api.expandInt
import self.nesl.gamer_api.model.GNews

class NewsParser: Parser<GNews> {
    private var news = GNews(
        url = "",
        title = "",
        gp = 0,
        preview = "",
        thumb = "",
        interactions = 0,
        popularity = 0,
        posterName = "",
        createdAt = "",
    )

    override fun parse(source: Element, req: Request): GNews {
        setTitle(source)
        setGp(source)
        setPreview(source)
        setThumb(source)
        setInteractions(source)
        setPopularity(source)
        setPosterName(source)
        setCreatedAt(source)
        news = news.copy(url = req.url.toString())
        return news
    }

    private fun setTitle(source: Element) {
        val title = source.selectFirst("p.b-list__main__title").text()
        news = news.copy(title = title)
    }

    private fun setGp(source: Element) {
        val gp = source.selectFirst("span.b-list__summary__gp.b-gp.b-gp--good")?.text()
        news = news.copy(gp = gp?.toInt() ?: 0)
    }

    private fun setPreview(source: Element) {
        val preview = source.selectFirst("p.b-list__brief").text()
        news = news.copy(preview = preview)
    }

    private fun setThumb(source: Element) {
        val thumb = source.selectFirst("div.b-list__img.skeleton.lazyload")?.attr("data-thumbnail")
        news = news.copy(thumb = thumb)
    }

    private fun setInteractions(source: Element) {
        val text = source.selectFirst("p.b-list__count__number span[title^=\"互動\"]").text()
        news = news.copy(interactions = text.expandInt())
    }

    private fun setPopularity(source: Element) {
        val text = source.selectFirst("p.b-list__count__number span[title^=\"人氣\"]").text()
        news = news.copy(popularity = text.expandInt())
    }

    private fun setPosterName(source: Element) {
        val text = source.selectFirst("p.b-list__count__user").text()
        news = news.copy(posterName = text)
    }

    private fun setCreatedAt(source: Element) {
        val text = source.selectFirst("p.b-list__time__edittime").text()
        news = news.copy(createdAt = text)
    }
}