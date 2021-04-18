package com.dangerfield.artically.data.cache.mappers

import com.dangerfield.artically.data.cache.model.TopHeadlineCacheEntity
import com.dangerfield.artically.domain.model.Article
import com.dangerfield.artically.domain.model.Source
import com.dangerfield.artically.domain.util.EntityMapper
import javax.inject.Inject

class TopHeadlineCacheEntityMapper @Inject constructor(): EntityMapper<TopHeadlineCacheEntity, Article> {
    override fun mapFromEntity(entity: TopHeadlineCacheEntity): Article {
        return Article(
            author = entity.author,
            content = entity.content,
            description = entity.description,
            publishedAt = entity.publishedAt,
            source = Source(id = entity.sourceId, name = entity.sourceName),
            title = entity.title,
            url = entity.url,
            urlToImage = entity.urlToImage
        )
    }

    override fun mapToEntity(domainModel: Article): TopHeadlineCacheEntity {
        return TopHeadlineCacheEntity(
            id = null,
            author = domainModel.author,
            content = domainModel.content,
            description = domainModel.description,
            publishedAt = domainModel.publishedAt,
            sourceId = domainModel.source?.id,
            sourceName = domainModel.source?.name,
            title = domainModel.title,
            url = domainModel.url,
            urlToImage = domainModel.urlToImage
        )
    }
}