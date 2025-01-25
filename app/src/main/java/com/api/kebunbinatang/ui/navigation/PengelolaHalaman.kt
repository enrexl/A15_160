package com.api.kebunbinatang.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.api.kebunbinatang.ui.view.MainMenu
import com.api.kebunbinatang.ui.view.hewan.HomeHewanScreen
import com.api.kebunbinatang.ui.view.petugas.PetugasHomeScreen
import com.api.kebunbinatang.ui.view.petugas.PetugasHomeStatus

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()){
    NavHost(
        navController = navController,
        startDestination = DestMain.route,
        modifier = Modifier
    ){
        //mainMenu
        composable(DestMain.route){
            MainMenu(
                toPetugasClick = {navController.navigate(DestHomePetugas.route)},
                toHewanClick = {navController.navigate(DestHomeHewan.route)},
                //toKandangClick = {navController.navigate()},
                //toMonitoringClick = {navController.navigate()}

            )
        }

        //home petugas
        composable(DestHomePetugas.route){
            PetugasHomeScreen(
                navigateToItemEntry = {},
                modifier = Modifier,
                onDetailClick = {navController.navigate("${DestDetailPetugas.route}/$it")}
            )
        }

    //home Hewan
        composable(DestHomeHewan.route){
            HomeHewanScreen(
                navigateToItemEntry = {},
                modifier = Modifier,
                onDetailClick = {},
                navigateBack = {navController.popBackStack()}

            )
        }
    }
}