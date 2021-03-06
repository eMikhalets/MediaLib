package com.emikhalets.network.model.tv_series

import com.emikhalets.network.model.Genre
import com.emikhalets.network.model.ProductionCompany
import com.emikhalets.network.model.ProductionCountry
import com.emikhalets.network.model.SpokenLanguage
import kotlinx.serialization.Serializable

@Serializable
data class TvDetailsResponse(
    val backdrop_path: String?,
    val created_by: List<CreatedBy>?,
    val episode_run_time: List<Int>?,
    val first_air_date: String?,
    val genres: List<Genre>?,
    val homepage: String?,
    val id: Int?,
    val in_production: String?,
    val languages: List<String>?,
    val last_air_date: String?,
    val last_episode_to_air: List<LastEpisodeToAir>?,
    val name: String?,
    val next_episode_to_air: Int?,
    val networks: List<Network>?,
    val number_of_episodes: Int?,
    val number_of_seasons: Int?,
    val origin_country: String?,
    val original_language: String?,
    val original_name: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val production_companies: List<ProductionCompany>?,
    val production_countries: List<ProductionCountry>?,
    val seasons: List<Season>?,
    val spoken_languages: List<SpokenLanguage>?,
    val status: String?,
    val tagline: String?,
    val type: String?,
    val vote_average: Double?,
    val vote_count: Int?,
)