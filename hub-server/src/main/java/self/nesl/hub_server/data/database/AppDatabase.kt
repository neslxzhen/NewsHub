package self.nesl.hub_server.data.database

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import self.nesl.hub_server.data.Host
import self.nesl.hub_server.data.Paragraph
import self.nesl.hub_server.data.ParagraphType
import self.nesl.hub_server.data.board.Board
import self.nesl.hub_server.data.board.BoardDao
import self.nesl.hub_server.data.news.gamer.GamerNews
import self.nesl.hub_server.data.news.gamer.GamerNewsDao
import self.nesl.hub_server.data.post.gamer.GamerPost
import self.nesl.hub_server.data.post.gamer.GamerPostDao
import self.nesl.hub_server.data.post.komica.KomicaPost
import self.nesl.hub_server.data.post.komica.KomicaPostDao

@Database(
    entities = [
        KomicaPost::class,
        GamerNews::class,
        GamerPost::class,
        Board::class,
    ],
    version = 1
)

@TypeConverters(
    AppDatabase.StringListConverter::class,
    AppDatabase.ParagraphListConverter::class,
    AppDatabase.HostConverter::class,
)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        private val gson = Gson()
    }

    object StringListConverter {
        @TypeConverter
        @JvmStatic
        fun valueToRoom(value: List<String>?): String {
            return value?.let { gson.toJson(it) } ?: ""
        }

        @TypeConverter
        @JvmStatic
        fun roomToValue(json: String): List<String> {
            return json.takeIf { it.isNotBlank() }?.let { jsonString: String ->
                val type = object : TypeToken<List<String>>() {}.type
                gson.fromJson(jsonString, type)
            } ?: emptyList()
        }
    }

    object ParagraphListConverter {
        @TypeConverter
        @JvmStatic
        fun valueToRoom(value: List<Paragraph>?): String {
            return value?.let { gson.toJson(it) } ?: ""
        }

        @TypeConverter
        @JvmStatic
        fun roomToValue(json: String): List<Paragraph> {
            return json.takeIf { it.isNotBlank() }?.let { jsonString: String ->
                JSONArray(jsonString).map {
                    when (it.getString("type")) {
                        ParagraphType.QUOTE.toString() -> Paragraph.Quote(
                            content = it.getString("content")
                        )
                        ParagraphType.REPLY_TO.toString() -> Paragraph.ReplyTo(
                            id = it.getString("id")
                        )
                        ParagraphType.TEXT.toString() -> Paragraph.Text(
                            content = it.getString("content")
                        )
                        ParagraphType.IMAGE.toString() -> Paragraph.ImageInfo(
                            thumb = it.getString("thumb"),
                            raw = it.getString("raw"),
                        )
                        ParagraphType.LINK.toString() -> Paragraph.Link(
                            content = it.getString("content")
                        )
                        else -> throw IllegalArgumentException()
                    }
                }
            } ?: emptyList()
        }

        private fun <O> JSONArray.map(callback: (JSONObject) -> O): List<O> {
            val list = mutableListOf<O>()
            for (i in 0 until this.length()) {
                list.add(callback(this.getJSONObject(i)))
            }
            return list
        }
    }

    object HostConverter {
        @TypeConverter
        @JvmStatic
        fun valueToRoom(value: Host?): String {
            return value?.let { gson.toJson(it) } ?: ""
        }

        @TypeConverter
        @JvmStatic
        fun roomToValue(json: String): Host? {
            return json.takeIf { it.isNotBlank() }?.let { jsonString: String ->
                val type = object : TypeToken<Host>() {}.type
                gson.fromJson(jsonString, type)
            }
        }
    }

    abstract fun komicaPostDao(): KomicaPostDao
    abstract fun gamerNewsDao(): GamerNewsDao
    abstract fun gamerPostDao(): GamerPostDao
    abstract fun boardDao(): BoardDao
}