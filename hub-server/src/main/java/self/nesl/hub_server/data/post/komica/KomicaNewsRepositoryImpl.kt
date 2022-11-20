package self.nesl.hub_server.data.post.komica

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import self.nesl.hub_server.data.board.Board
import self.nesl.hub_server.data.board.toKBoard
import self.nesl.hub_server.data.news.NewsRepository
import self.nesl.komica_api.KomicaApi
import self.nesl.newshub.di.IoDispatcher
import self.nesl.hub_server.di.TransactionProvider
import javax.inject.Inject

class KomicaNewsRepositoryImpl @Inject constructor(
    private val dao: KomicaPostDao,
    private val api: KomicaApi,
    private val transactionProvider: TransactionProvider,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
): NewsRepository<KomicaPost> {

    override suspend fun getAllNews(board: Board, page: Int): List<KomicaPost> = withContext(ioDispatcher) {
        val news = dao.readAllNews(board.url, page)
        news.ifEmpty {
            try {
                val request = api.getRequestBuilder(board.toKBoard())
                    .url(board.url)
                    .setPageReq(page)
                    .build()
                val remote = api.getAllPost(request)
                    .map { it.toKomicaPost(page, board.url) }
                transactionProvider.invoke {
                    dao.upsertAll(remote)
                }
                remote
            } catch (e: Exception) {
                Log.e("KomicaNewsRepo", e.stackTraceToString())
                emptyList()
            }
        }
    }

    override suspend fun clearAllNews() = withContext(ioDispatcher) {
        dao.clearAll()
    }
}