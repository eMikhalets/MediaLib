package com.emikhalets.medialib.data.entity.movies

data class MovieSearchResponse(
    val page: Int?,
    val results: List<MovieSearchResult>?,
    val total_results: Int?,
    val total_pages: Int?,
)
