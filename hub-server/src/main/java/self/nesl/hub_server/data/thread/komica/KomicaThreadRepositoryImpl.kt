package self.nesl.hub_server.data.thread.komica

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import self.nesl.hub_server.toKomicaThread
import self.nesl.komica_api.KomicaApi
import self.nesl.newshub.di.IoDispatcher
import javax.inject.Inject

class KomicaThreadRepositoryImpl @Inject constructor(
    private val api: KomicaApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
): KomicaThreadRepository {

    override suspend fun getThread(url: String): KomicaThread = withContext(ioDispatcher) {
        val remote = api.getThread(url).toKomicaThread(url)
        return@withContext remote
    }
}