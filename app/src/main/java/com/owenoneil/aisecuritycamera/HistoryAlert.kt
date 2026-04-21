package com.owenoneil.aisecuritycamera

import kotlinx.serialization.Serializable

@Serializable
data class HistoryAlert(
    val alert: String,
    val created_at: String,
    val video_path: String?  = null
)