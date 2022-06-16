package com.emikhalets.network.model.tv_series

import kotlinx.serialization.Serializable

@Serializable
data class TvSearchResponse(
    val page: Int?,
    val results: List<TvSearchResult>?,
    val total_results: Int?,
    val total_pages: Int?,
)
