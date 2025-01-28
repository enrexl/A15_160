package com.api.kebunbinatang.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.api.kebunbinatang.ui.view.MainMenu
import com.api.kebunbinatang.ui.view.hewan.DetailScreenHewan
import com.api.kebunbinatang.ui.view.hewan.EntryHewanScreen

import com.api.kebunbinatang.ui.view.hewan.HomeHewanScreen
import com.api.kebunbinatang.ui.view.hewan.UpdateHewanScreen
import com.api.kebunbinatang.ui.view.petugas.DetailPetugasScreen
import com.api.kebunbinatang.ui.view.petugas.DetailPetugasScreen
import com.api.kebunbinatang.ui.view.petugas.EntryPetugasScreen
import com.api.kebunbinatang.ui.view.petugas.PetugasHomeScreen
import com.api.kebunbinatang.ui.view.petugas.UpdatePetugasScreen


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
                navigateToItemEntry = {navController.navigate(DestInsertPetugas.route)},
                modifier = Modifier,
                onDetailClick = {id_petugas ->
                    navController.navigate("${DestDetailPetugas.route}/$id_petugas")}
            )
        }
        //detail Petugas
        composable(DestDetailPetugas.routeWithArg,
            arguments = listOf(
                navArgument(DestDetailPetugas.id_petugas) { type = NavType.StringType }
            )){
            val id_petugas = it.arguments?.getString(DestDetailPetugas.id_petugas)
            id_petugas?.let {
                DetailPetugasScreen(
                    id_petugas = id_petugas,
                    navigateBack = { navController.popBackStack() },
                    onUpdateClick = { navController.navigate("${DestUpdPetugas.route}/$id_petugas") },
                )
            }
        }
        //add Petugas
        composable(DestInsertPetugas.route) {
            EntryPetugasScreen(
                navigateBack = {
                    navController.navigate(DestHomePetugas.route) {
                        popUpTo(DestHomePetugas.route) { inclusive = true }
                    }
                }
            )
        }
        //update Petugas
        composable(DestUpdPetugas.routeWithArg,
            arguments = listOf(navArgument("id_petugas"){type = NavType.StringType}))
        {
            backstackEntry ->
            val id_petugas = backstackEntry.arguments?.getString("id_petugas")//ambil id_petugas
            id_petugas?.let {
                UpdatePetugasScreen( id_petugas = it, navigateBack = {navController.popBackStack()})
            }
        }



    //home Hewan
        composable(DestHomeHewan.route){
            HomeHewanScreen(
                navigateToItemEntry = {navController.navigate(DestInsertHewan.route)},
                modifier = Modifier,
                onDetailClick = {id_hewan ->
                    navController.navigate("${DestDetailHewan.route}/$id_hewan")},
                navigateBack = {navController.popBackStack()}

            )
        }
    //add Hewan
        composable(DestInsertHewan.route) {
            EntryHewanScreen(
                navigateBack = {
                    navController.navigate(DestHomeHewan.route) {
                        popUpTo(DestHomeHewan.route) { inclusive = true }
                    }
                }
            )
        }
    //detail hewan
        composable(DestDetailHewan.routeWithArg, arguments = listOf(navArgument(DestDetailHewan.id_hewan) {
            type = NavType.StringType })
        ){
            val id_hewan = it.arguments?.getString(DestDetailHewan.id_hewan)
            id_hewan?.let { id_hewan ->
                DetailScreenHewan(
                    navigateToItemUpdate = { navController.navigate("${DestUpdHewan.route}/$id_hewan") },
                    navigateBack = { navController.navigate(DestHomeHewan.route) {
                        popUpTo(DestHomeHewan.route) { inclusive = true }
                    }
                    },
                    id_hewan = id_hewan,

                )
            }
        }
        //update hewan
        composable(DestUpdHewan.routeWithArg,
            arguments = listOf(navArgument("id_hewan"){type = NavType.StringType}))
        {
            backstackEntry ->
            val id_hewan = backstackEntry.arguments?.getString("id_hewan")//ambil id_hewan
            id_hewan?.let {
                UpdateHewanScreen( id_hewan = it, navigateBack = {navController.popBackStack()})
            }
        }
    }
}