package com.emikhalets.network.model.tv_series

import kotlinx.serialization.Serializable

@Serializable
data class LastEpisodeToAir(
    val air_date: String?,
    val episode_count: Int?,
    val id: Int?,
    val name: String?,
    val overview: String?,
    val production_code: String?,
    val season_number: Int?,
    val still_path: Int?,
    val vote_average: Double?,
    val vote_count: Int?,
)
