package com.dangerfield.artically.di

import android.content.Context
import androidx.room.Room
import com.dangerfield.artically.data.cache.db.ArticallyDatabase
import com.dangerfield.artically.data.cache.db.TopHeadlinesDao
import com.dangerfield.artically.data.cache.mappers.TopHeadlineCacheEntityMapper
import com.dangerfield.artically.data.network.mappers.TopHeadlineNetworkEntityMapper
import com.dangerfield.artically.data.network.service.ArticlesService
import com.dangerfield.artically.data.repo.ArticleRepositoryImpl
import com.dangerfield.artically.data.util.RateLimiterImpl
import com.dangerfield.artically.domain.repo.ArticleRepository
import com.dangerfield.artically.domain.usecases.getTopHeadlines.GetTopHeadlines
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) //makes this an application wide dependency
object AppModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun providesArticleService(gson: Gson): ArticlesService {

        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BASIC
        }

        val client: OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()


        return Retrofit.Builder().baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build().create(ArticlesService::class.java)
    }

    @Singleton
    @Provides
    fun providesArticallyDatabase(@ApplicationContext context: Context) : ArticallyDatabase {
        return ArticallyDatabase.invoke(context)
    }


    @Singleton
    @Provides
    fun providesTopHeadlinesDao(db: ArticallyDatabase) : TopHeadlinesDao {
        return db.topHeadlinesDao()
    }

    @Singleton
    @Provides
    fun providesGetTopHeadlinesUseCase(repository: ArticleRepository) : GetTopHeadlines {
        val rateLimiter = RateLimiterImpl(15, TimeUnit.MINUTES)
        return GetTopHeadlines(repository, rateLimiter)
    }

    @Singleton
    @Provides
    fun providesArticleRepository(
        topHeadlinesNetworkEntityMapper: TopHeadlineNetworkEntityMapper,
        articlesService: ArticlesService,
        topHeadlinesDao: TopHeadlinesDao,
        topHeadlineCacheEntityMapper: TopHeadlineCacheEntityMapper
    ): ArticleRepository {
        return ArticleRepositoryImpl(
            articlesService,
            topHeadlinesNetworkEntityMapper,
            topHeadlinesDao,
            topHeadlineCacheEntityMapper)
    }
}