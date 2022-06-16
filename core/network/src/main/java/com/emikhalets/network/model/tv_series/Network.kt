package com.emikhalets.network.model.tv_series

import kotlinx.serialization.Serializable

@Serializable
data class Network(
    val name: String?,
    val id: Int?,
    val logo_path: String?,
    val origin_country: String?,
)
