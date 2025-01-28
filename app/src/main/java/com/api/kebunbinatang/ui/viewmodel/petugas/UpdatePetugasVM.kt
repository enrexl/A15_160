package com.api.kebunbinatang.ui.viewmodel.petugas

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.api.kebunbinatang.model.Petugas
import com.api.kebunbinatang.repo.PetugasRepo
import kotlinx.coroutines.launch


class UpdatePetugasVM(private val petugasR: PetugasRepo) : ViewModel() {

    var uiState by mutableStateOf(UpdateUiState())
        private set


    fun loadPetugas(id_petugas: String) {
        viewModelScope.launch {
            try {
                val petugas = petugasR.getPetugasByID(id_petugas)
                uiState = UpdateUiState(insertUiEvent = petugas.toInsertUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update Mahasiswa data
    fun updatePetugas() {
        viewModelScope.launch {
            try {
                val id_petugas = uiState.insertUiEvent.id_petugas
                val petugas = uiState.insertUiEvent.toPetugas()
                petugasR.updatePetugas(id_petugas,petugas)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateInsertMhsState(insertUiEvent: InsertUiEvent) {
        uiState = UpdateUiState(insertUiEvent = insertUiEvent)
    }
}

data class UpdateUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)