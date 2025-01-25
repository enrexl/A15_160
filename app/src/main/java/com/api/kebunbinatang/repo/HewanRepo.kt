package com.api.kebunbinatang.repo

import com.api.kebunbinatang.model.AllHewanResponse
import com.api.kebunbinatang.model.AllPetugasResponse
import com.api.kebunbinatang.model.Hewan
import com.api.kebunbinatang.model.Petugas
import com.api.kebunbinatang.service.HewanService
import com.api.kebunbinatang.service.PetugasService
import java.io.IOException

interface HewanRepo {

    suspend fun getHewan(): AllHewanResponse
    suspend fun getHewanByID(id_hewan: String): Hewan
    suspend fun insertHewan(hewan: Hewan)
    suspend fun updateHewan(id_hewan: String, hewan: Hewan)
    suspend fun deleteHewan(id_hewan: String)
}

class NetworkHewanRepository(
    private val HewanApiService : HewanService)
    : HewanRepo{

    override suspend fun getHewan(): AllHewanResponse =
        HewanApiService.getAllHewan()


    override suspend fun getHewanByID(id_hewan: String): Hewan {
        return try {
            HewanApiService.getHewanByID(id_hewan).data
        } catch (e: Exception){
            println("Error Fetch hewan by ID: ${e.message}")
            throw e }
    }

    override suspend fun insertHewan(hewan: Hewan) {
        HewanApiService.insertHewan(hewan)
    }

    override suspend fun updateHewan(id_hewan: String, hewan: Hewan) {
        HewanApiService.updateHewan(id_hewan,hewan)
    }

    override suspend fun deleteHewan(id_hewan: String) {
        try {
            val response = HewanApiService.deleteHewan(id_hewan)
            if (!response.isSuccessful){
                throw IOException("Failer to delete Hewan. HTTP Status code: " +
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

