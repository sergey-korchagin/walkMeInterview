package com.skorch.walkmeinterview.di

import android.content.Context
import androidx.room.Room
import com.skorch.walkmeinterview.data.ArticleRepositoryImpl
import com.skorch.walkmeinterview.data.local.ArticleDao
import com.skorch.walkmeinterview.data.local.ArticlesDatabase
import com.skorch.walkmeinterview.data.remote.ArticleApiService
import com.skorch.walkmeinterview.domain.ArticleRepository
import com.skorch.walkmeinterview.domain.GetArticlesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://fakenews.squirro.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideArticleApi(retrofit: Retrofit): ArticleApiService {
        return retrofit.create(ArticleApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ArticlesDatabase {
        return Room.databaseBuilder(
            context,
            ArticlesDatabase::class.java,
            "articles_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideArticleDao(database: ArticlesDatabase): ArticleDao {
        return database.articleDao()
    }

    @Provides
    @Singleton
    fun provideArticleRepository(
        api: ArticleApiService,
        dao: ArticleDao
    ): ArticleRepository {
        return ArticleRepositoryImpl(api, dao)
    }

    @Provides
    @Singleton
    fun provideGetArticlesUseCase(repository: ArticleRepository): GetArticlesUseCase {
        return GetArticlesUseCase(repository)
    }
}
