package com.emikhalets.network.model.tv_series

import kotlinx.serialization.Serializable

@Serializable
data class CreatedBy(
    val id: Int?,
    val credit_id: String?,
    val name: String?,
    val gender: Int?,
    val poster_path: String?,
)
