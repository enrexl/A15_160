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
import java.io.IOException
import kotlin.math.log

sealed class HomeHewanUiState {
    data class Success(val hewan: List<Hewan>) : HomeHewanUiState()
    object Error : HomeHewanUiState()
    object Loading : HomeHewanUiState()
}

class HomeHewanViewModel(private val hewanRepo: HewanRepo) : ViewModel() {

    var hewanUIState: HomeHewanUiState by mutableStateOf(HomeHewanUiState.Loading)
        private set

    private val _hewanUiState = mutableStateOf<HomeHewanUiState>(HomeHewanUiState.Loading)
    //val hewanUiState: HomeHewanUiState get() = _hewanUiState.value
    private var allHewanList: List<Hewan> = emptyList()

    fun getHewan() {
        viewModelScope.launch {
            _hewanUiState.value = HomeHewanUiState.Loading

            try {
                val response = hewanRepo.getHewan()
                allHewanList = response.data
                hewanUIState = HomeHewanUiState.Success(hewanRepo.getHewan().data)
                Log.d("INI DIA","${response.data}")
            } catch (e: IOException) {
                hewanUIState = HomeHewanUiState.Error
                Log.d("SATUUU","}")
            } catch (e: Exception) {
                hewanUIState = HomeHewanUiState.Error
                Log.d("DUAAAA","}")
            }
        }
    }

    fun deleteHewan(id_hewan: String) {
        viewModelScope.launch {
            try {
                hewanRepo.deleteHewan(id_hewan)
                getHewan()
            } catch (e: IOException) {
                hewanUIState = HomeHewanUiState.Error
          } catch (e: Exception) {
                hewanUIState = HomeHewanUiState.Error
            }
        }
    }

    fun filterHewan(query: String){
        val filteredList =
            if(query.isBlank()){ allHewanList }
            else {allHewanList.filter { it.id_hewan.contains(query) }}
        hewanUIState = HomeHewanUiState.Success(filteredList)

        println("All hewan list: $allHewanList")
        println("filtered: $filteredList")
    }
}
