package self.nesl.hub_server.data.post.gamer

import self.nesl.gamer_api.model.GBoard


interface GamerNewsRepository {
    suspend fun getAllNews(board: GBoard, page: Int): List<GamerNews>
    suspend fun clearAllNews()
}