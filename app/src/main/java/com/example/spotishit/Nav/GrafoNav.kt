package com.example.spotishit.Nav

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.spotishit.Vistas.Reproductor

@Composable
fun GrafoNav(context: Context){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Reproductor"){

        composable(Ruta.Reproductor.ruta){
            Reproductor(context = context, navController = navController )
          }


    }

}