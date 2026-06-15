package com.example.lorolingo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lorolingo.ui.components.CardColor
import com.example.lorolingo.ui.theme.LoroLingoTheme

@Composable
fun PantallaColores(onVolver: () -> Unit) {
    val colorFondo  = Color(0xFF121212)
    val colorCian   = Color(0xFF00F5D4)
    val colorGris   = Color(0xFFAAAAAA)

    val listaColores = listOf(
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

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorFondo)
            .verticalScroll(scrollState)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text          = "Colores",
            fontSize      = 32.sp,
            fontWeight    = FontWeight.Bold,
            color         = colorCian,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text     = "Colors in English",
            fontSize = 14.sp,
            color    = colorGris
        )

        Spacer(modifier = Modifier.height(24.dp))

        listaColores.forEach { (espanol, ingles, colorVisual) ->
            CardColor(
                espanol      = espanol,
                ingles       = ingles,
                colorVisual  = colorVisual
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick  = onVolver,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape  = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorCian
            )
        ) {
            Text(
                text       = "Volver al Inicio",
                fontSize   = 15.sp,
                fontWeight = FontWeight.Bold,
                color      = Color(0xFF121212)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}


@Preview(name = "Pantalla de Colores", showBackground = true, showSystemUi = true)
@Composable
fun PreviewColores() {
    LoroLingoTheme {
        PantallaColores(onVolver = {})
    }
}
