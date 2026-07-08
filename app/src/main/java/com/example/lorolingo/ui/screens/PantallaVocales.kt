package com.example.lorolingo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lorolingo.R
import com.example.lorolingo.ui.components.CardVocal
import com.example.lorolingo.ui.components.StudyTimer
import com.example.lorolingo.ui.theme.LoroLingoTheme

@Composable
fun PantallaVocales(
    onVolver: () -> Unit, 
    limite: Int? = null,
    onSessionEarned: () -> Unit = {}
) {
    val colorFondo = Color(0xFF121212)
    val colorDegradado = Color(0xFF1A1A2E)
    val colorCian = Color(0xFF00F5D4)
    val colorGris = Color(0xFFAAAAAA)
    val colorNaranja = Color(0xFFE57A0D)

    val vocalesCompleta = listOf(
        VocalData("A", "Ei", "Apple", R.drawable.apple, "Alligator", R.drawable.alligator, Color(0xFFE53935)),
        VocalData("E", "I", "Egg", R.drawable.egg, "Elephant", R.drawable.elephant, Color(0xFF43A047)),
        VocalData("I", "Ai", "Ice Cream", R.drawable.icecream, "Island", R.drawable.island, Color(0xFF1E88E5)),
        VocalData("O", "Ou", "Orange", R.drawable.orange, "Owl", R.drawable.owl, Color(0xFFFB8C00)),
        VocalData("U", "Iu", "Ukelele", R.drawable.ukelele, "Uranus", R.drawable.uranus, Color(0xFFFDD835))
    )

    val listaVocales = if (limite != null) vocalesCompleta.take(limite) else vocalesCompleta

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.radialGradient(colors = listOf(colorDegradado, colorFondo), radius = 2000f))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // ENCABEZADO
            Column(
                modifier = Modifier.fillMaxWidth().padding(24.dp, 40.dp, 24.dp, 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Vocales", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = colorCian, textAlign = TextAlign.Center)
                Text(text = "Vowels in English", fontSize = 14.sp, color = colorGris, textAlign = TextAlign.Center)
                if (limite != null) {
                    Text(text = "Modo Invitado", color = colorNaranja, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                }
            }

            if (limite == null) {
                StudyTimer(onSessionComplete = onSessionEarned)
            }

            // CONTENIDO
            LazyColumn(
                modifier = Modifier.weight(1f).padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(listaVocales) { vocal ->
                    CardVocal(
                        vocal = vocal.letra,
                        pronunciacion = vocal.pronunciacion,
                        ejemplo1 = vocal.ejemplo1,
                        icono1 = vocal.icon1,
                        ejemplo2 = vocal.ejemplo2,
                        icono2 = vocal.icon2,
                        colorVisual = vocal.color
                    )
                }
                if (limite != null) {
                    item {
                        Text(text = "Inicia sesión para ver las restantes.", color = colorGris, fontSize = 13.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(top = 16.dp))
                    }
                }
            }

            // BOTÓN
            Box(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
                Button(onClick = onVolver, modifier = Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(12.dp), colors = ButtonDefaults.buttonColors(containerColor = colorCian)) {
                    Text(text = "Volver al Inicio", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF121212))
                }
            }
        }
    }
}

data class VocalData(val letra: String, val pronunciacion: String, val ejemplo1: String, val icon1: Int, val ejemplo2: String, val icon2: Int, val color: Color)

@Preview(showBackground = true)
@Composable
fun PreviewVocales() {
    LoroLingoTheme { PantallaVocales(onVolver = {}) }
}
