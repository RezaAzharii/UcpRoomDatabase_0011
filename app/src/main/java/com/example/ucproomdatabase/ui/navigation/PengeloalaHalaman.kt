package com.example.ucproomdatabase.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucproomdatabase.ui.view.dokter.HomeDokView
import com.example.ucproomdatabase.ui.view.dokter.InsertDokView
import com.example.ucproomdatabase.ui.view.jadwal.DetailJadView
import com.example.ucproomdatabase.ui.view.jadwal.HomeJadView
import com.example.ucproomdatabase.ui.view.jadwal.InsertJadView
import com.example.ucproomdatabase.ui.view.jadwal.UpdateJadView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeDok.route){
        composable(
            route = DestinasiHomeDok.route
        ){
            HomeDokView(
                onNavigateToHomeJad = {
                    navController.navigate(DestinasiHomeJad.route)
                },
                onAddDok = {
                    navController.navigate(DestinasiInsertDok.route)
                },
                modifier = modifier
            )
        }
        composable(
            route = DestinasiHomeJad.route
        ){
            HomeJadView(
                onNavigateToHomeDok = {
                    navController.navigate(DestinasiHomeDok.route)
                },
                onCardDetail = {idJ ->
                    navController.navigate("${DestinasiDetailJad.route}/$idJ")
                    println(
                        "Pengelolahan: idJ = $idJ"
                    )
                },
                onAddJad = {
                    navController.navigate(DestinasiInsertJad.route)
                },
                modifier = modifier
            )
        }
        composable(
            route = DestinasiInsertDok.route
        ){
            InsertDokView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }
        composable(
            route = DestinasiInsertJad.route
        ){
            InsertJadView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }
        composable(
            route = DestinasiDetailJad.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailJad.IDJ){
                    type = NavType.IntType
                }
            )
        ){
            val idJ = it.arguments?.getInt(DestinasiDetailJad.IDJ)
            idJ?.let { idj ->
                DetailJadView(
                    onBack ={
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateJad.route}/$it")
                    },
                    modifier = modifier,
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable(
            DestinasiUpdateJad.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateJad.IDJ){
                    type = NavType.IntType
                }
            )
        ){
            UpdateJadView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
            )
        }
    }
}