package com.emikhalets.network.model

import kotlinx.serialization.Serializable

@Serializable
data class SpokenLanguage(
    val iso_639_1: String?,
    val name: String?,
    val english_name: String?,
)
