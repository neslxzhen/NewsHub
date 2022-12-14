package self.nesl.hub_server.di

import android.app.Application
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import self.nesl.hub_server.data.database.AppDatabase
import self.nesl.hub_server.data.board.BoardRepository
import self.nesl.hub_server.data.board.BoardRepositoryImpl
import self.nesl.hub_server.data.database.PrePopulateCallBack
import self.nesl.hub_server.data.news.gamer.GamerNewsRepositoryImpl
import self.nesl.hub_server.data.news.NewsRepository
import self.nesl.hub_server.data.news.gamer.GamerNews
import self.nesl.hub_server.data.news.komica.KomicaNews
import self.nesl.hub_server.data.post.ThreadRepository
import self.nesl.hub_server.data.post.gamer.GamerPost
import self.nesl.hub_server.data.post.gamer.GamerThreadRepositoryImpl
import self.nesl.hub_server.data.post.komica.KomicaPost
import self.nesl.hub_server.data.news.komica.KomicaNewsRepositoryImpl
import self.nesl.hub_server.data.post.komica.KomicaThreadRepositoryImpl
import self.nesl.hub_server.data.topic.TopicRepository
import self.nesl.hub_server.data.topic.TopicRepositoryImpl
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [
    DataModule.RepositoryBinder::class,
])
object DataModule {

    @Singleton
    @Provides
    fun provideDatabase(
        app: Application,
        callback: PrePopulateCallBack
    ): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "app.db"
        )
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()
    }

    @Singleton
    @Provides
    fun provideTransactionProvider(database: AppDatabase) = TransactionProvider(database)

    @Singleton
    @Provides
    fun provideKomicaNewsDao(database: AppDatabase) = database.komicaNewsDao()

    @Singleton
    @Provides
    fun provideKomicaPostDao(database: AppDatabase) = database.komicaPostDao()

    @Singleton
    @Provides
    fun provideGamerNewsDao(database: AppDatabase) = database.gamerNewsDao()

    @Singleton
    @Provides
    fun provideGamerPostDao(database: AppDatabase) = database.gamerPostDao()

    @Singleton
    @Provides
    fun provideBoardDao(database: AppDatabase) = database.boardDao()

    @InstallIn(SingletonComponent::class)
    @Module
    abstract class RepositoryBinder {

        @Binds
        abstract fun bindTopicRepository(impl: TopicRepositoryImpl): TopicRepository

        @Binds
        abstract fun bindBoardRepository(impl: BoardRepositoryImpl): BoardRepository

        @Binds
        abstract fun bindKomicaNewsRepository(impl: KomicaNewsRepositoryImpl): NewsRepository<KomicaNews>

        @Binds
        abstract fun bindGamerNewsRepository(impl: GamerNewsRepositoryImpl): NewsRepository<GamerNews>

        @Binds
        abstract fun bindKomicaPostRepository(impl: KomicaThreadRepositoryImpl): ThreadRepository<KomicaPost>

        @Binds
        abstract fun bindGamerPostRepository(impl: GamerThreadRepositoryImpl): ThreadRepository<GamerPost>
    }

    @DataScope
    @Provides
    @Singleton
    fun provideDataScope() = CoroutineScope(SupervisorJob())
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class DataScope