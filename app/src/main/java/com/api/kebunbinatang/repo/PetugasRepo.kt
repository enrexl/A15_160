package com.api.kebunbinatang.repo

import com.api.kebunbinatang.model.AllPetugasResponse
import com.api.kebunbinatang.model.Petugas

interface PetugasRepo {

    suspend fun getPetugas(): AllPetugasResponse
    suspend fun getPetugasByID(id_petugas: String): Petugas
    suspend fun insertPetugas(petugas: Petugas)
    suspend fun updatePetugas(id_petugas: String, petugas: Petugas)
    suspend fun deletePetugas(id_petugas: String)
}