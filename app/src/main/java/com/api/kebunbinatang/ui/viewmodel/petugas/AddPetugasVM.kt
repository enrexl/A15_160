package com.api.kebunbinatang.ui.viewmodel.petugas

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.api.kebunbinatang.model.Petugas
import com.api.kebunbinatang.repo.PetugasRepo
import kotlinx.coroutines.launch


class AddPetugasVM(private val petugas: PetugasRepo) : ViewModel(){
    var uiState by mutableStateOf(InsertUiState())
        private set


    fun updateInsertPetugasState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }


    suspend fun insertPetugas() {
        viewModelScope.launch() {
            try {
                petugas.insertPetugas(uiState.insertUiEvent.toPetugas())
        }catch (e:Exception){
            e.printStackTrace()
            }
        }
    }
}

data class InsertUiState(
    val insertUiEvent:InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val id_petugas:String = "",
    val nama_petugas:String = "",
    val jabatan:String = ""

)

fun InsertUiEvent.toPetugas(): Petugas = Petugas(
    id_petugas = id_petugas,
    nama_petugas = nama_petugas,
    jabatan = jabatan

)

fun  Petugas.toUiStatePetugas():InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Petugas.toInsertUiEvent():InsertUiEvent = InsertUiEvent(
    id_petugas = id_petugas,
    nama_petugas = nama_petugas,
    jabatan = jabatan
)