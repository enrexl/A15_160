package com.api.kebunbinatang.model

import kotlinx.serialization.Serializable

@Serializable
data class Petugas(
    val id_petugas: String,
    val nama_petugas: String,
    val jabatan: String
)

@Serializable
data class AllPetugasResponse(
    val status: Boolean,
    val message: String,
    val data: List<Petugas>
)

@Serializable
data class PetugasDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Petugas
)