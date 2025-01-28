package com.api.kebunbinatang.ui.viewmodel.petugas

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.api.kebunbinatang.model.Petugas
import com.api.kebunbinatang.repo.PetugasRepo
import com.api.kebunbinatang.ui.navigation.DestDetailPetugas
import com.api.kebunbinatang.ui.navigation.DestDetailPetugas.id_petugas
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed class DetailPetugasUiState {
    data class Success(val petugas: Petugas) : DetailPetugasUiState()
    object Error : DetailPetugasUiState()
    object Loading : DetailPetugasUiState()
}

class DetailPetugasVM(val petugasR: PetugasRepo) : ViewModel() {


    var detailUiState: DetailPetugasUiState by mutableStateOf(DetailPetugasUiState.Loading)


    init {
        getDetailPetugas(id_petugas)
    }

    fun getDetailPetugas(id_petugas: String) {
        viewModelScope.launch {
            detailUiState = DetailPetugasUiState.Loading
            Log.d("DEBUG", "Fetching petugas with ID: $id_petugas")
            try {
                val petugas = petugasR.getPetugasByID(id_petugas)
                detailUiState = DetailPetugasUiState.Success(petugas)
                Log.d("INI DIA","${petugas}")
            }catch (e: HttpException){
                Log.d("ERROR", "HTTP error: ${e.code()} - ${e.message()}")
            }

            catch (e: Exception) {
                detailUiState = DetailPetugasUiState.Error
                Log.d("ERROR","${e.message}\\\"\"", e)
            }
        }
    }

    fun deletePetugas(id_petugas: String){
        viewModelScope.launch{
            try {
                petugasR.deletePetugas(id_petugas)
            } catch (e:IOException){
                DetailPetugasUiState.Error
            }
            catch (e: HttpException){
                DetailPetugasUiState.Error
            }
        }
    }
}