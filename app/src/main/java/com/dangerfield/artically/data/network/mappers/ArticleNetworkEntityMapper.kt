package com.dangerfield.artically.data.network.mappers

import com.dangerfield.artically.data.network.model.ArticleNetworkEntity
import com.dangerfield.artically.domain.model.Article
import com.dangerfield.artically.domain.util.EntityMapper
import javax.inject.Inject

class ArticleNetworkEntityMapper @Inject constructor(
    private val sourceNetworkEntityMapper: SourceNetworkEntityMapper
) : EntityMapper<ArticleNetworkEntity, Article> {

    override fun mapFromEntity(entity: ArticleNetworkEntity): Article {
        return Article(
            author = entity.author,
        content = entity.content,
        description = entity.description,
        publishedAt = entity.publishedAt,
        source = entity.sourceNetworkEntity?.let { sourceNetworkEntityMapper.mapFromEntity(it) },
        title = entity.title,
        url = entity.url,
        urlToImage = entity.urlToImage
        )
    }

    override fun mapToEntity(domainModel: Article): ArticleNetworkEntity {
        return ArticleNetworkEntity(
            author = domainModel.author,
            content = domainModel.content,
            description = domainModel.description,
            publishedAt = domainModel.publishedAt,
            sourceNetworkEntity = domainModel.source?.let { sourceNetworkEntityMapper.mapToEntity(it) },
            title = domainModel.title,
            url = domainModel.url,
            urlToImage = domainModel.urlToImage
        )
    }
}