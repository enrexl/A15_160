package com.api.kebunbinatang.repo

import com.api.kebunbinatang.model.AllMonitoringResponse
import com.api.kebunbinatang.model.AllPetugasResponse
import com.api.kebunbinatang.model.Monitoring
import com.api.kebunbinatang.model.Petugas
import com.api.kebunbinatang.service.MonitoringService
import com.api.kebunbinatang.service.PetugasService
import java.io.IOException

interface MonitoringRepo {

    suspend fun getMonitoring(): AllMonitoringResponse
    suspend fun getMonitoringByID(id_monitoring: String): Monitoring
    suspend fun insertMonitoring(monitoring: Monitoring)
    suspend fun updateMonitoring(id_monitoring: String, monitoring: Monitoring)
    suspend fun deleteMonitoring(id_monitoring: String)
}

class NetworkMonitoringRepository(
    private val MonitoringApiService : MonitoringService)
    : MonitoringRepo{

    override suspend fun getMonitoring(): AllMonitoringResponse =
        MonitoringApiService.getAllMonitoring()


    override suspend fun getMonitoringByID(id_monitoring: String): Monitoring {
        return try {
            MonitoringApiService.getMonitoringByID(id_monitoring).data
        } catch (e: Exception){
            println("Error Fetch Petugas by ID: ${e.message}")
            throw e }
    }

    override suspend fun insertMonitoring(monitoring: Monitoring) {
        MonitoringApiService.insertMonitoring(monitoring)
    }

    override suspend fun updateMonitoring(id_monitoring: String, monitoring: Monitoring) {
        MonitoringApiService.updateMonitoring(id_monitoring,monitoring)
    }

    override suspend fun deleteMonitoring(id_monitoring: String) {
        try {
            val response = MonitoringApiService.deleteMonitoring(id_monitoring)
            if (!response.isSuccessful){
                throw IOException("Failer to delete Monitoring. HTTP Status code: " +
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
