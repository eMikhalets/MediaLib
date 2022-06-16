package com.emikhalets.network.model.movies

import kotlinx.serialization.Serializable

@Serializable
data class MovieSearchResponse(
    val page: Int?,
    val results: List<MovieSearchResult>?,
    val total_results: Int?,
    val total_pages: Int?,
)
