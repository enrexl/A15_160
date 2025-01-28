package com.api.kebunbinatang.service

import com.api.kebunbinatang.model.AllKandangResponse
import com.api.kebunbinatang.model.AllPetugasResponse
import com.api.kebunbinatang.model.Kandang
import com.api.kebunbinatang.model.KandangDetailResponse
import com.api.kebunbinatang.model.Petugas
import com.api.kebunbinatang.model.PetugasDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface KandangService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @GET("kandang/")
    suspend fun getALlKandang(): AllKandangResponse

    @GET("kandang/{id_kandang}")
    suspend fun getKandangByID(@Path("id_kandang") id_kandang: String) : KandangDetailResponse

    @POST("kandang/add")
    suspend fun insertKandang(@Body kandang: Kandang)

    @PUT("kandang/{id_kandang}")
    suspend fun updateKandang(@Path("id_kandang") id_kandang: String, @Body kandang: Kandang)

    @DELETE("kandang/{id_kandang}")
    suspend fun deleteKandang(@Path("id_kandang")id_kandang: String):Response<Void>

}