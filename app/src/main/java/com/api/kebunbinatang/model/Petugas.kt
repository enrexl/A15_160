package com.api.kebunbinatang.model

import kotlinx.serialization.Serializable

@Serializable
data class Petugas(
    val id_petugas: String,
    val nama_petugas: String,
    val jabatan: String
)