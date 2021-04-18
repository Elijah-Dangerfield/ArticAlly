package com.dangerfield.artically.data.network.mappers

import com.dangerfield.artically.data.network.model.TopHeadlinesResponse
import com.dangerfield.artically.domain.model.TopHeadlines
import com.dangerfield.artically.domain.util.EntityMapper
import javax.inject.Inject

class TopHeadlineNetworkEntityMapper @Inject constructor(
    private val articleMapper : ArticleNetworkEntityMapper
) : EntityMapper<TopHeadlinesResponse, TopHeadlines> {
    override fun mapFromEntity(entity: TopHeadlinesResponse): TopHeadlines {
        return TopHeadlines(articles = entity.articleNetworkEntities?.map { articleMapper.mapFromEntity(it) })
    }

    override fun mapToEntity(domainModel: TopHeadlines): TopHeadlinesResponse {
        return TopHeadlinesResponse(
            articleNetworkEntities = domainModel.articles.map { articleMapper.mapToEntity(it) },
            totalResults = domainModel.articles.size,
            status = "ok"
        )
    }
}