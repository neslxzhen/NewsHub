package dev.zlong.gamer_api.interactor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetBoard {
    suspend fun invoke(url: String) = withContext(Dispatchers.IO) {
        GetAllBoard().invoke().first { url.contains(it.url) }
    }
}