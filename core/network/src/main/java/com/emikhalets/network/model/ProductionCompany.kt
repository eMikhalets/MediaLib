package com.emikhalets.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductionCompany(
    val name: String?,
    val id: Int?,
    val logo_path: String?,
    val origin_country: String?,
)
