package com.api.kebunbinatang.repo

import com.api.kebunbinatang.model.AllPetugasResponse
import com.api.kebunbinatang.model.Petugas
import com.api.kebunbinatang.service.PetugasService
import java.io.IOException

interface PetugasRepo {

    suspend fun getPetugas(): AllPetugasResponse
    suspend fun getPetugasByID(id_petugas: String): Petugas
    suspend fun insertPetugas(petugas: Petugas)
    suspend fun updatePetugas(id_petugas: String, petugas: Petugas)
    suspend fun deletePetugas(id_petugas: String)
}

class NetworkPetugasRepository(
    private val PetugasApiService : PetugasService)
    : PetugasRepo{

    override suspend fun getPetugas(): AllPetugasResponse =
        PetugasApiService.getAllPetugas()


    override suspend fun getPetugasByID(id_petugas: String): Petugas {
     return try {
        PetugasApiService.getPetugasByID(id_petugas).data
    } catch (e: Exception){
        println("Error Fetch Petugas by ID: ${e.message}")
         throw e }
    }

    override suspend fun insertPetugas(petugas: Petugas) {
        PetugasApiService.insertPetugas(petugas)
    }

    override suspend fun updatePetugas(id_petugas: String, petugas: Petugas) {
        PetugasApiService.updatePetugas(id_petugas,petugas)
    }

    override suspend fun deletePetugas(id_petugas: String) {
        try {
            val response = PetugasApiService.deletePetugas(id_petugas)
            if (!response.isSuccessful){
                throw IOException("Failer to delete Petugas. HTTP Status code: " +
                        "${response.code()}")
            }
                else{
                    response.message()
                println(response.message())
            }
        }
        catch (e: Exception){
            throw e
        }
    }
    }
