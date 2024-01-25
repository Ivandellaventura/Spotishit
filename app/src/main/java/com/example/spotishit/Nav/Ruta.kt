package com.example.spotishit.Nav

sealed class Ruta(var ruta: String) {
    object Reproductor: Ruta(ruta = "Reproductor")
}