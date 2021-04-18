package com.dangerfield.artically.data.network.model

import com.google.gson.annotations.SerializedName

data class ArticleNetworkEntity(
    @SerializedName("author") val author: String? = "",
    @SerializedName("content") val content: String? = "",
    @SerializedName("description") val description: String? = "",
    @SerializedName("publishedAt") val publishedAt: String? = "",
    @SerializedName("source") val sourceNetworkEntity: SourceNetworkEntity? = SourceNetworkEntity("",""),
    @SerializedName("title") val title: String? = "",
    @SerializedName("url") val url: String? = "",
    @SerializedName("urlToImage") val urlToImage: String? = ""
) {
}