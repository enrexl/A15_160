package com.api.kebunbinatang.service

import com.api.kebunbinatang.model.AllHewanResponse
import com.api.kebunbinatang.model.AllPetugasResponse
import com.api.kebunbinatang.model.Hewan
import com.api.kebunbinatang.model.HewanDetailResponse
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

interface HewanService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @GET("hewan/")
    suspend fun getAllHewan(): AllHewanResponse

    @GET("/hewan/{id_hewan}")
    suspend fun getHewanByID(@Path("id_hewan") id_hewan: String) : HewanDetailResponse

    @POST("/hewan/add")
    suspend fun insertHewan(@Body hewan: Hewan)

    @PUT("/hewan/{id_hewan}")
    suspend fun updateHewan(@Path("id_hewan") id_hewanv: String, @Body hewan: Hewan)

    @DELETE("/hewan/{id_hewanv}")
    suspend fun deleteHewan(@Path("id_hewan")id_hewan: String):Response<Void>

}