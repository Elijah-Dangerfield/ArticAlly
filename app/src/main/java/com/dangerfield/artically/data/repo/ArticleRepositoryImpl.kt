package com.dangerfield.artically.data.repo

import com.dangerfield.artically.data.cache.db.TopHeadlinesDao
import com.dangerfield.artically.data.cache.mappers.TopHeadlineCacheEntityMapper
import com.dangerfield.artically.data.network.mappers.TopHeadlineNetworkEntityMapper
import com.dangerfield.artically.data.network.service.ArticlesService
import com.dangerfield.artically.domain.model.TopHeadlines
import com.dangerfield.artically.domain.repo.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val articlesService: ArticlesService,
    private val topHeadlinesNetworkEntityMapper: TopHeadlineNetworkEntityMapper,
    private val topHeadlinesDao: TopHeadlinesDao,
    private val topHeadlineCacheEntityMapper: TopHeadlineCacheEntityMapper
) : ArticleRepository {

    override suspend fun fetchNewTopHeadlines(): TopHeadlines {
        val result = articlesService.getTopHeadlines()
        return topHeadlinesNetworkEntityMapper.mapFromEntity(result)
    }

    override suspend fun fetchCachedTopHeadlines(): Flow<TopHeadlines> {
        return topHeadlinesDao.getTopHeadlines()
                .map { cacheResponse ->
                    val articles = cacheResponse.map { topHeadlineCacheEntityMapper.mapFromEntity(it) }
                    TopHeadlines(articles)
                }
    }

    override suspend fun replaceCachedHeadlines(headlines: TopHeadlines) {
        val cacheEntities = headlines.articles.map { topHeadlineCacheEntityMapper.mapToEntity(it) }
        topHeadlinesDao.replaceTopHeadlines(cacheEntities)
    }

}