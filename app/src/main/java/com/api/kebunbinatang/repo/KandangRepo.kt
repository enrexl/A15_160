package com.api.kebunbinatang.repo

import com.api.kebunbinatang.model.AllHewanResponse
import com.api.kebunbinatang.model.AllKandangResponse
import com.api.kebunbinatang.model.Hewan
import com.api.kebunbinatang.model.Kandang
import com.api.kebunbinatang.service.HewanService
import com.api.kebunbinatang.service.KandangService
import java.io.IOException

interface KandangRepo {

    suspend fun getKandang(): AllKandangResponse
    suspend fun getKandangByID(id_kandang: String): Kandang
    suspend fun insertKandang(kandang: Kandang)
    suspend fun updateKandang(id_kandang: String, kandang: Kandang)
    suspend fun deleteKandang(id_kandang: String)
}

class NetworkKandangRepository(
    private val KandangApiService : KandangService)
    : KandangRepo{

    override suspend fun getKandang(): AllKandangResponse =
        KandangApiService.getALlKandang()


    override suspend fun getKandangByID(id_kandang: String): Kandang {
        return try {
            KandangApiService.getKandangByID(id_kandang).data
        } catch (e: Exception){
            println("Error Fetch Kandang by ID: ${e.message}")
            throw e }
    }

    override suspend fun insertKandang(kandang: Kandang) {
        KandangApiService.insertKandang(kandang)
    }

    override suspend fun updateKandang(id_kandang: String, kandang: Kandang) {
        KandangApiService.updateKandang(id_kandang, kandang)
    }

    override suspend fun deleteKandang(id_kandang: String) {
        try {
            val response = KandangApiService.deleteKandang(id_kandang)
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