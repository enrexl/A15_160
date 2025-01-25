package com.api.kebunbinatang.di

import com.api.kebunbinatang.model.Hewan
import com.api.kebunbinatang.repo.HewanRepo
import com.api.kebunbinatang.repo.KandangRepo
import com.api.kebunbinatang.repo.MonitoringRepo
import com.api.kebunbinatang.repo.NetworkHewanRepository
import com.api.kebunbinatang.repo.NetworkKandangRepository
import com.api.kebunbinatang.repo.NetworkMonitoringRepository
import com.api.kebunbinatang.repo.NetworkPetugasRepository
import com.api.kebunbinatang.repo.PetugasRepo
import com.api.kebunbinatang.service.HewanService
import com.api.kebunbinatang.service.KandangService
import com.api.kebunbinatang.service.MonitoringService
import com.api.kebunbinatang.service.PetugasService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer{
    val petugasRepo: PetugasRepo
    val hewanRepo: HewanRepo
    val kandangRepo : KandangRepo
    val monitoringRepo: MonitoringRepo
}
class KebunBinatangContainer : AppContainer{
    private val baseUrl = "http://10.0.2.2:3000/api/"
    private val json = Json {
        ignoreUnknownKeys = true
    }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val petugasService: PetugasService by lazy {
        retrofit.create(PetugasService::class.java)
    }
    override val petugasRepo: PetugasRepo by lazy {
        NetworkPetugasRepository(petugasService)
    }

    private val hewanService: HewanService by lazy {
        retrofit.create(HewanService::class.java)
    }
    override val hewanRepo: HewanRepo by lazy {
        NetworkHewanRepository(hewanService)
    }

    private val kandangService: KandangService by lazy {
        retrofit.create(KandangService::class.java)
    }
    override val kandangRepo: KandangRepo by lazy {
        NetworkKandangRepository(kandangService)
    }

    private val monitoringService: MonitoringService by lazy {
        retrofit.create(MonitoringService::class.java)
    }
    override val monitoringRepo: MonitoringRepo by lazy {
        NetworkMonitoringRepository(monitoringService)
    }

}