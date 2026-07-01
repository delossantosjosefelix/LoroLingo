package com.example.lorolingo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.lorolingo.ui.components.CardColor
import com.example.lorolingo.ui.theme.LoroLingoTheme

@Composable
fun PantallaColores(onVolver: () -> Unit, limite: Int? = null) {
    val colorFondo  = Color(0xFF121212)
    val colorDegradado = Color(0xFF1A1A2E)
    val colorCian   = Color(0xFF00F5D4)
    val colorGris   = Color(0xFFAAAAAA)
    val colorNaranja = Color(0xFFE57A0D)

    val listaColoresCompleta = listOf(
        Triple("Rojo",      "Red",     Color(0xFFE53935)),
        Triple("Azul",      "Blue",    Color(0xFF1E88E5)),
        Triple("Amarillo",  "Yellow",  Color(0xFFFDD835)),
        Triple("Verde",     "Green",   Color(0xFF43A047)),
        Triple("Negro",     "Black",   Color(0xFF212121)),
        Triple("Blanco",    "White",   Color(0xFFFFFFFF)),
        Triple("Naranja",   "Orange",  Color(0xFFFB8C00)),
        Triple("Morado",    "Purple",  Color(0xFF8E24AA)),
        Triple("Rosado",    "Pink",    Color(0xFFE91E8C)),
        Triple("Marrón",    "Brown",   Color(0xFF6D4C41)),
        Triple("Gris",      "Gray",    Color(0xFF757575)),
        Triple("Plateado",  "Silver",  Color(0xFFB0BEC5)),
        Triple("Dorado",    "Gold",    Color(0xFFFFD700))
    )
    
    val listaColores = if (limite != null) listaColoresCompleta.take(limite) else listaColoresCompleta

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
                Text(text = "Colores", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = colorCian, textAlign = TextAlign.Center)
                Text(text = "Colors in English", fontSize = 14.sp, color = colorGris, textAlign = TextAlign.Center)
                if (limite != null) {
                    Text(text = "Modo Invitado", color = colorNaranja, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                }
            }

            // CONTENIDO
            LazyColumn(
                modifier = Modifier.weight(1f).padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(listaColores) { (espanol, ingles, colorVisual) ->
                    CardColor(espanol = espanol, ingles = ingles, colorVisual = colorVisual)
                }
                if (limite != null) {
                    item {
                        Text(text = "Inicia sesión para ver los ${listaColoresCompleta.size - limite} restantes.", color = colorGris, fontSize = 13.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(top = 16.dp))
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

@Preview(showBackground = true)
@Composable
fun PreviewColores() {
    LoroLingoTheme { PantallaColores(onVolver = {}) }
}
