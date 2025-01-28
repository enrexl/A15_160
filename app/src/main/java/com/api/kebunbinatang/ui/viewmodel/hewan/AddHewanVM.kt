package com.api.kebunbinatang.ui.viewmodel.hewan

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.api.kebunbinatang.model.Hewan
import com.api.kebunbinatang.repo.HewanRepo
import kotlinx.coroutines.launch


class AddHewanVM (private val hewan: HewanRepo): ViewModel(){
    var uiState by mutableStateOf(InsertUiState())
    private set

    fun updateInsertHewanState(insertUiEvent: InsertUiEvent){
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertHewan(){
        viewModelScope.launch{
            try {
                hewan.insertHewan(uiState.insertUiEvent.toHewan())
                Log.d("Berhasil","")
            }catch (e: Exception){
                e.printStackTrace()
                Log.d("GAGAL ADD HEWAN","")
            }
        }
    }
}

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val id_hewan: String ="",
    val nama_hewan: String = "",
    val tipe_pakan: String = "",
    val populasi: String = "",
    val zona_wilayah: String = ""
)

fun InsertUiEvent.toHewan(): Hewan = Hewan(
    id_hewan = id_hewan,
    nama_hewan = nama_hewan,
    tipe_pakan = tipe_pakan,
    populasi = populasi,
    zona_wilayah = zona_wilayah
)

fun Hewan.toUiStateHewan(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Hewan.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    id_hewan = id_hewan,
    nama_hewan = nama_hewan,
    tipe_pakan = tipe_pakan,
    populasi = populasi,
    zona_wilayah = zona_wilayah
)