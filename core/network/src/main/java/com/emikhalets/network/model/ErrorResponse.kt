package com.emikhalets.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val status_message: String?,
    val status_code: Int?,
)
