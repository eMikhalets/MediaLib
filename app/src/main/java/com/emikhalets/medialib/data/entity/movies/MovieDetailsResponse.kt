package com.emikhalets.medialib.data.entity.movies

import com.emikhalets.medialib.data.entity.Genre
import com.emikhalets.medialib.data.entity.ProductionCompany
import com.emikhalets.medialib.data.entity.ProductionCountry
import com.emikhalets.medialib.data.entity.SpokenLanguage

data class MovieDetailsResponse(
    val adult: Boolean?,
    val backdrop_path: String?,
    val belongs_to_collection: Int?,
    val budget: Int?,
    val genres: List<Genre>?,
    val homepage: String?,
    val id: Int?,
    val imdb_id: String?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val production_companies: List<ProductionCompany>?,
    val production_countries: List<ProductionCountry>?,
    val release_date: String?,
    val revenue: Int?,
    val runtime: Int?,
    val spoken_languages: List<SpokenLanguage>?,
    val status: String?,
    val tagline: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?,
)
