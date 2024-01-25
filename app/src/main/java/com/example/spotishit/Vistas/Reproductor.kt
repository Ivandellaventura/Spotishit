package com.example.spotishit.Vistas

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.spotishit.CancionesManejo.ExoPlayerViewModel

import kotlinx.coroutines.flow.collect

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Reproductor(context: Context, navController: NavController) {
    val contexto = context
    val exoPlayerViewModel: ExoPlayerViewModel = viewModel()

    val actual by remember { exoPlayerViewModel.actual }.collectAsState()
    val progreso by exoPlayerViewModel.progreso.collectAsState()
    val duracion by exoPlayerViewModel.duracion.collectAsState()

    LaunchedEffect(Unit) {
        exoPlayerViewModel.crearExoPlayer(contexto)
        exoPlayerViewModel.hacerSonarMusica(contexto)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            text = "Currently playing",
            color = Color.Gray,
        )
        Text(
            text = actual.title,
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,

        )

        Image(
            painter = painterResource(id = actual.imageResId),
            contentDescription = "Foto",
            modifier = Modifier
                .size(300.dp)
                .shadow(15.dp, shape = MaterialTheme.shapes.medium)
        )

        Slider(
            value = progreso.toFloat(), modifier = Modifier.padding(5.dp,10.dp,5.dp,0.dp ),
            onValueChange = { nuevaPosicion ->
                exoPlayerViewModel.irAPosicion(nuevaPosicion.toInt())
            },
            valueRange = 0f..duracion.toFloat(),
            steps = 100,
            colors = SliderDefaults.colors(
                thumbColor = Color(63, 81, 181, 255),
                activeTickColor = Color(63, 81, 181, 255),
                activeTrackColor =Color(63, 81, 181, 255),
                inactiveTickColor = Color.Gray,
                inactiveTrackColor = Color.Gray
            )
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp, 0.dp, 15.dp, 20.dp)
        ) {
            Text(
                text = formatMillis(progreso),
                color = Color.Black
            )
            Text(
                text = formatMillis(duracion),
                color = Color.Black
            )
        }


        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { exoPlayerViewModel.toglearRandom() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                modifier = Modifier
                    .size(72.dp) // Ajusta el tamaño del botón
                    .background(Color.Transparent, CircleShape) // Usa CircleShape como forma

            ) {
                Icon(Icons.Default.Shuffle, contentDescription = "Random", tint = if (exoPlayerViewModel.random.value) Color.Black else Color.Gray, modifier = Modifier.size(36.dp))
            }
            Button(
                onClick = { exoPlayerViewModel.retrocederCancion(contexto) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                modifier = Modifier
                    .size(72.dp) // Ajusta el tamaño del botón
                    .background(Color.Transparent, CircleShape) // Usa CircleShape como forma

            ) {
                Icon(
                    Icons.Default.SkipPrevious,
                    contentDescription = "Previous",
                    tint = Color.Black,
                    modifier = Modifier.size(36.dp)
                )
            }
            Button(
                onClick = { exoPlayerViewModel.PausarOSeguirMusica() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(63, 81, 181, 255)),
                modifier = Modifier
                    .size(72.dp) // Ajusta el tamaño del botón
                    .background(Color.Transparent, CircleShape) // Usa CircleShape como forma

            ) {
                val isPlaying = exoPlayerViewModel.exoPlayer.value?.isPlaying ?: false
                if (isPlaying) {
                    Icon(Icons.Default.Pause, contentDescription = "Pause", tint = Color.Black, modifier = Modifier.size(36.dp))
                } else {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.Black, modifier = Modifier.size(36.dp))
                }
            }
            Button(
                onClick = { exoPlayerViewModel.CambiarCancion(contexto) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                modifier = Modifier
                    .size(72.dp) // Ajusta el tamaño del botón
                    .background(Color.Transparent, CircleShape) // Usa CircleShape como forma

            ) {
                Icon(Icons.Default.SkipNext, contentDescription = "Next", tint = Color.Black, modifier = Modifier.size(36.dp))
            }
            Button(
                onClick = { exoPlayerViewModel.toglearRepetir() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                modifier = Modifier
                    .size(72.dp) // Ajusta el tamaño del botón
                    .background(Color.Transparent, CircleShape) // Usa CircleShape como forma

            ) {
                Icon(Icons.Default.Repeat, contentDescription = "Repeat", tint = if (exoPlayerViewModel.repetir.value) Color.Black else Color.Gray, modifier = Modifier.size(36.dp))
            }
        }
    }
}

private fun formatMillis(millis: Int): String {
    val seconds = millis / 1000
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}
