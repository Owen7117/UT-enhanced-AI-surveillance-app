package com.owenoneil.aisecuritycamera

import kotlinx.serialization.Serializable

@Serializable
data class HistoryAlert(
    val email: String,
    val alert: String,
    val created_at: String
)