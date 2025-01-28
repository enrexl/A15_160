package com.api.kebunbinatang.ui.viewmodel.hewan

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.api.kebunbinatang.model.Hewan
import com.api.kebunbinatang.repo.HewanRepo
import com.api.kebunbinatang.ui.navigation.DestDetailHewan
import com.api.kebunbinatang.ui.navigation.DestDetailHewan.id_hewan
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed class DetailHewanUiState {
    data class Success(val hewan: Hewan) : DetailHewanUiState()
    object Error : DetailHewanUiState()
    object Loading : DetailHewanUiState()
}

class DetailHewanVM(private val hewan: HewanRepo) : ViewModel() {

    private val _detailUiState = MutableStateFlow<DetailHewanUiState>(DetailHewanUiState.Loading)


    var hewanDetailState: DetailHewanUiState by mutableStateOf(DetailHewanUiState.Loading)
        private set

    init {
        getDetailHewan(id_hewan)
    }
    fun getDetailHewan(id_hewan:String) {
        viewModelScope.launch {
            hewanDetailState = DetailHewanUiState.Loading
            Log.d("DEBUG", "Fetching petugas with ID: $id_hewan")
            try {
                val hewan = hewan.getHewanByID(id_hewan)
                hewanDetailState = DetailHewanUiState.Success(hewan)
            } catch (e: Exception) {
                hewanDetailState = DetailHewanUiState.Error
            }
        }
    }



    fun deleteHewan(id_hewan:String) {
        viewModelScope.launch {
            try {
                hewan.deleteHewan(id_hewan)
            }catch (e:IOException){
                DetailHewanUiState.Error
            }catch (e: HttpException){
                DetailHewanUiState.Error
            }
        }
    }
}