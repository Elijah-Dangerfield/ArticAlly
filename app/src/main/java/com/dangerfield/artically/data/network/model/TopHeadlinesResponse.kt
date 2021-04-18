package com.dangerfield.artically.data.network.model

import com.google.gson.annotations.SerializedName

data class TopHeadlinesResponse(
    @SerializedName("status") var status: String,
    @SerializedName("totalResults") var totalResults: Int,
    @SerializedName("articles") var articleNetworkEntities:  List<ArticleNetworkEntity>
)