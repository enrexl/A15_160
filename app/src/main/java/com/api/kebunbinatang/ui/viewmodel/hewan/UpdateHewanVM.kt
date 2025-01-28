package com.api.kebunbinatang.ui.viewmodel.hewan
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.api.kebunbinatang.repo.HewanRepo
import kotlinx.coroutines.launch


class UpdateHewanVM(private val hewanR: HewanRepo) : ViewModel() {

    var uiState by mutableStateOf(UpdateUiState())
        private set


    fun loadHewan(id_hewan: String) {
        viewModelScope.launch {
            try {
                val hewan = hewanR.getHewanByID(id_hewan)
                uiState = UpdateUiState(insertUiEvent = hewan.toInsertUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update Hewan data
    fun updateHewan() {
        viewModelScope.launch {
            try {
                val id_hewan = uiState.insertUiEvent.id_hewan
                val hewan = uiState.insertUiEvent.toHewan()
                hewanR.updateHewan(id_hewan, hewan)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateInsertHewanState(insertUiEvent: InsertUiEvent) {
        uiState = UpdateUiState(insertUiEvent = insertUiEvent)
    }
}

data class UpdateUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)