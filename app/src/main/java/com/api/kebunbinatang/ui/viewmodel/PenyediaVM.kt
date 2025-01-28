package com.api.kebunbinatang.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.api.kebunbinatang.KebunBinatangApp
import com.api.kebunbinatang.ui.viewmodel.hewan.AddHewanVM
import com.api.kebunbinatang.ui.viewmodel.hewan.DetailHewanVM
import com.api.kebunbinatang.ui.viewmodel.hewan.HomeHewanViewModel
import com.api.kebunbinatang.ui.viewmodel.hewan.UpdateHewanVM
import com.api.kebunbinatang.ui.viewmodel.petugas.AddPetugasVM
import com.api.kebunbinatang.ui.viewmodel.petugas.DetailPetugasVM
import com.api.kebunbinatang.ui.viewmodel.petugas.HomePetugasVM
import com.api.kebunbinatang.ui.viewmodel.petugas.UpdatePetugasVM

object PenyediaVM {
    val Factory = viewModelFactory {
        initializer { HomeHewanViewModel(Kebun().container.hewanRepo) }
       initializer { AddHewanVM(Kebun().container.hewanRepo) }
        initializer { UpdateHewanVM(Kebun().container.hewanRepo) }
        initializer { DetailHewanVM(Kebun().container.hewanRepo) }

        initializer { HomePetugasVM(Kebun().container.petugasRepo) }
        initializer { DetailPetugasVM(Kebun().container.petugasRepo) }
        initializer { AddPetugasVM(Kebun().container.petugasRepo) }
        initializer { UpdatePetugasVM(Kebun().container.petugasRepo) }
//        initializer { DelPetugasVM(Kebun().container.petugasRepo) }

//        initializer { HomeKandangVM(Kebun().container.kandangRepo) }
//        initializer { InsertPetugasVM(Kebun().container.kandangRepo) }
//        initializer { UpdateKandangVM(Kebun().container.kandangRepo) }
//        initializer { DelKandangVM(Kebun().container.kandangRepo) }

//        initializer { HomeMonitoringVM(Kebun().container.monitoringRepo) }
//        initializer { InsertMonitoringVM(Kebun().container.monitoringRepo) }
//        initializer { UpdateMonitoringVM(Kebun().container.monitoringRepo) }
//        initializer { DelMonitoringVm(Kebun().container.monitoringRepo) }
    }

    fun CreationExtras.Kebun(): KebunBinatangApp =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KebunBinatangApp)
}