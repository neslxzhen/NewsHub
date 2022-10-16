package self.nesl.gamer_api.parser

import org.jsoup.nodes.Element

interface Parser<T> {
    fun parse(source: Element, url: String): T
}