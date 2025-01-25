package com.api.kebunbinatang.ui.viewmodel.petugas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.api.kebunbinatang.model.Petugas
import com.api.kebunbinatang.repo.PetugasRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed class DetailPetugasUiState {
    data class Success(val petugas: Petugas) : DetailPetugasUiState()
    object Error : DetailPetugasUiState()
    object Loading : DetailPetugasUiState()
}

class DetailPetugasVM(private val petugasR: PetugasRepo) : ViewModel() {

    private val _detailUiState = MutableStateFlow<DetailPetugasUiState>(DetailPetugasUiState.Loading)
    val detailUiState: StateFlow<DetailPetugasUiState> = _detailUiState.asStateFlow()

    fun getDetailPetugas(id_petugas: String) {
        viewModelScope.launch {
            _detailUiState.value = DetailPetugasUiState.Loading
            try {
                val petugas = petugasR.getPetugasByID(id_petugas)
                _detailUiState.value = DetailPetugasUiState.Success(petugas)
            } catch (e: Exception) {
                _detailUiState.value = DetailPetugasUiState.Error
            }
        }
    }
}