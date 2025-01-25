package com.api.kebunbinatang.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.api.kebunbinatang.KebunBinatangApp
import com.api.kebunbinatang.ui.viewmodel.hewan.HomeHewanViewModel
import com.api.kebunbinatang.ui.viewmodel.petugas.HomePetugasVM

object PenyediaVM {
    val Factory = viewModelFactory {
        initializer { HomeHewanViewModel(Kebun().container.hewanRepo) }
      // initializer { InsertHewanVM(Kebun().container.hewanRepo) }
      //  initializer { UpdateHewanVM(Kebun().container.hewanRepo) }
      //  initializer { DelHewanVM(Kebun().container.hewanRepo) }

        initializer { HomePetugasVM(Kebun().container.petugasRepo) }
//        initializer { InsertPetugasVM(Kebun().container.petugasRepo) }
//        initializer { UpdatePetugasVM(Kebun().container.petugasRepo) }
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