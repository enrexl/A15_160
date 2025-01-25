package com.api.kebunbinatang.service

import com.api.kebunbinatang.model.AllKandangResponse
import com.api.kebunbinatang.model.AllMonitoringResponse
import com.api.kebunbinatang.model.Kandang
import com.api.kebunbinatang.model.KandangDetailResponse
import com.api.kebunbinatang.model.Monitoring
import com.api.kebunbinatang.model.MonitoringDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MonitoringService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @GET("monitoring/")
    suspend fun getAllMonitoring(): AllMonitoringResponse

    @GET("/monitoring/{id_monitoring}")
    suspend fun getMonitoringByID(@Path("id_monitoring") id_monitoring: String) : MonitoringDetailResponse

    @POST("/monitoring/add")
    suspend fun insertMonitoring(@Body monitoring: Monitoring)

    @PUT("/monitoring/{id_monitoring}")
    suspend fun updateMonitoring(@Path("id_monitoring") id_monitoring: String, @Body monitoring: Monitoring)

    @DELETE("/monitoring/{id_monitoring}")
    suspend fun deleteMonitoring(@Path("id_monitoring")id_monitoring: String):Response<Void>

}