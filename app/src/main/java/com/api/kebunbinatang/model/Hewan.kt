package com.api.kebunbinatang.model

import kotlinx.serialization.Serializable

@Serializable
data class Hewan (
    val id_hewan: String,
    val nama_hewan: String,
    val tipe_pakan: String,
    val populasi: String,
    val zona_wilayah: String
)

@Serializable
data class AllHewanResponse(
    val status: Boolean,
    val message: String,
    val data: List<Hewan>
)
