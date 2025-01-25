package com.api.kebunbinatang.ui.viewmodel.petugas

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.api.kebunbinatang.model.Petugas
import com.api.kebunbinatang.repo.PetugasRepo
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class HomePetugasUiState{
    data class Success(val petugas: List<Petugas>) : HomePetugasUiState()
    object Error: HomePetugasUiState()
    object Loading: HomePetugasUiState()
}

class HomePetugasVM (private val petugas: PetugasRepo) : ViewModel(){

    var petugasUiState: HomePetugasUiState by mutableStateOf(HomePetugasUiState.Loading)
        private set

    fun getPetugas(){
        viewModelScope.launch{
            petugasUiState = HomePetugasUiState.Loading

            try {
                petugasUiState = HomePetugasUiState.Success(petugas.getPetugas().data)
            }
            catch (e:IOException){
                petugasUiState = HomePetugasUiState.Error
            }
            catch (e: HttpException){
                petugasUiState = HomePetugasUiState.Error
            }
        }
    }

    fun deletePetugas(id_petugas: String){
        viewModelScope.launch{
            try {
                petugas.deletePetugas(id_petugas)
            } catch (e:IOException){
                petugasUiState = HomePetugasUiState.Error
            }
            catch (e: HttpException){
                petugasUiState = HomePetugasUiState.Error
        }
    }
}