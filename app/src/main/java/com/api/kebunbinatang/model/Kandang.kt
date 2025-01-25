package com.api.kebunbinatang.model

import kotlinx.serialization.Serializable

@Serializable
data class Kandang(
    val id_kandang: String,
    val id_hewan: String,
    val kapasitas: Int,
    val lokasi: String
)
