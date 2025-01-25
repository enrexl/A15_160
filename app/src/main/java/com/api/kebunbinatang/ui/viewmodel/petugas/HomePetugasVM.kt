package com.api.kebunbinatang.ui.viewmodel.petugas

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.api.kebunbinatang.model.Petugas
import com.api.kebunbinatang.repo.PetugasRepo
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import android.util.Log

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
            Log.d("HomePetugasVM","Fetching petugas...")

            try {
                val response = petugas.getPetugas()
                petugasUiState = HomePetugasUiState.Success(petugas.getPetugas().data)
                Log.d("INI DIA","${response.data}")
            }
            catch (e:IOException){
                petugasUiState = HomePetugasUiState.Error
                Log.d("ERORR SATU","IOException while fetching petugas: ${e.message}\"", e)

            }
            catch (e: HttpException){
                petugasUiState = HomePetugasUiState.Error
                Log.d("ERORR DUA","")
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
}