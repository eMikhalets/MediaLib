package com.emikhalets.medialib.data.entity.tv_series

data class TvSearchResponse(
    val page: Int?,
    val results: List<TvSearchResult>?,
    val total_results: Int?,
    val total_pages: Int?,
)
