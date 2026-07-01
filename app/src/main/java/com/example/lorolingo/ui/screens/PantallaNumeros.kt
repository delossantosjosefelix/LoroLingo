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
import com.example.lorolingo.ui.components.CardNumero
import com.example.lorolingo.ui.theme.LoroLingoTheme

@Composable
fun PantallaNumeros(onVolver: () -> Unit, limite: Int? = null) {
    val colorFondo  = Color(0xFF121212)
    val colorDegradado = Color(0xFF1A1A2E)
    val colorCian   = Color(0xFF00F5D4)
    val colorGris   = Color(0xFFAAAAAA)
    val colorNaranja = Color(0xFFE57A0D)

    val nombresEspanol = listOf(
        "Uno", "Dos", "Tres", "Cuatro", "Cinco", "Seis", "Siete", "Ocho", "Nueve", "Diez",
        "Once", "Doce", "Trece", "Catorce", "Quince", "Dieciséis", "Diecisiete", "Dieciocho", "Diecinueve", "Veinte",
        "Veintiuno", "Veintidós", "Veintitrés", "Veinticuatro", "Veinticinco", "Veintiséis", "Veintisiete", "Veintiocho", "Veintinueve", "Treinta",
        "Treinta y uno", "Treinta y dos", "Treinta y tres", "Treinta y cuatro", "Treinta y cinco", "Treinta y seis", "Treinta y siete", "Treinta y ocho", "Treinta y nueve", "Cuarenta",
        "Cuarenta y uno", "Cuarenta y dos", "Cuarenta y tres", "Cuarenta y cuatro", "Cuarenta y cinco", "Cuarenta y seis", "Cuarenta y siete", "Cuarenta y ocho", "Cuarenta y nueve", "Cincuenta",
        "Cincuenta y uno", "Cincuenta y dos", "Cincuenta y tres", "Cincuenta y cuatro", "Cincuenta y cinco", "Cincuenta y seis", "Cincuenta y siete", "Cincuenta y ocho", "Cincuenta y nueve", "Sesenta",
        "Sesenta y uno", "Sesenta y dos", "Sesenta y tres", "Sesenta y cuatro", "Sesenta y cinco", "Sesenta y seis", "Sesenta y siete", "Sesenta y ocho", "Sesenta y nueve", "Setenta",
        "Setenta y uno", "Setenta y dos", "Setenta y tres", "Setenta y cuatro", "Setenta y cinco", "Setenta y seis", "Setenta y siete", "Setenta y ocho", "Setenta y nueve", "Ochenta",
        "Ochenta y uno", "Ochenta y dos", "Ochenta y tres", "Ochenta y cuatro", "Ochenta y cinco", "Ochenta y seis", "Ochenta y siete", "Ochenta y ocho", "Ochenta y nueve", "Noventa",
        "Noventa y uno", "Noventa y dos", "Noventa y tres", "Noventa y cuatro", "Noventa y cinco", "Noventa y seis", "Noventa y siete", "Noventa y ocho", "Noventa y nueve", "Cien"
    )

    val nombresIngles = listOf(
        "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
        "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen", "Twenty",
        "Twenty-One", "Twenty-Two", "Twenty-Three", "Twenty-Four", "Twenty-Five", "Twenty-Six", "Twenty-Seven", "Twenty-Eight", "Twenty-Nine", "Thirty",
        "Thirty-One", "Thirty-Two", "Thirty-Three", "Thirty-Four", "Thirty-Five", "Thirty-Six", "Thirty-Seven", "Thirty-Eight", "Thirty-Nine", "Forty",
        "Forty-One", "Forty-Two", "Forty-Three", "Forty-Four", "Forty-Five", "Forty-Six", "Forty-Seven", "Forty-Eight", "Forty-Nine", "Fifty",
        "Fifty-One", "Fifty-Two", "Fifty-Three", "Fifty-Four", "Fifty-Five", "Fifty-Six", "Fifty-Seven", "Fifty-Eight", "Fifty-Nine", "Sixty",
        "Sixty-One", "Sixty-Two", "Sixty-Three", "Sixty-Four", "Sixty-Five", "Sixty-Six", "Sixty-Seven", "Sixty-Eight", "Sixty-Nine", "Seventy",
        "Seventy-One", "Seventy-Two", "Seventy-Three", "Seventy-Four", "Seventy-Five", "Seventy-Six", "Seventy-Seven", "Seventy-Eight", "Seventy-Nine", "Eighty",
        "Eighty-One", "Eighty-Two", "Eighty-Three", "Eighty-Four", "Eighty-Five", "Eighty-Six", "Eighty-Seven", "Eighty-Eight", "Eighty-Nine", "Ninety",
        "Ninety-One", "Ninety-Two", "Ninety-Three", "Ninety-Four", "Ninety-Five", "Ninety-Six", "Ninety-Seven", "Ninety-Eight", "Ninety-Nine", "One Hundred"
    )

    val listRange = if (limite != null) (1..limite) else (1..100)
    val datosNumeros = listRange.map { i ->
        Triple(i, nombresEspanol[i-1], nombresIngles[i-1])
    }.chunked(10)

    val titulosDecenas = listOf(
        "1 - 10", "11 - 20", "21 - 30", "31 - 40", "41 - 50",
        "51 - 60", "61 - 70", "71 - 80", "81 - 90", "91 - 100"
    )

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
                Text(text = "Números", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = colorCian, textAlign = TextAlign.Center)
                Text(text = "Numbers in English", fontSize = 14.sp, color = colorGris, textAlign = TextAlign.Center)
                if (limite != null) {
                    Text(text = "Modo Invitado", color = colorNaranja, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                }
            }

            // CONTENIDO
            LazyColumn(
                modifier = Modifier.weight(1f).padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                datosNumeros.forEachIndexed { indiceDecena, grupo ->
                    item {
                        Text(text = titulosDecenas[indiceDecena], fontSize = 13.sp, fontWeight = FontWeight.Bold, color = colorCian, modifier = Modifier.padding(top = if (indiceDecena == 0) 0.dp else 8.dp))
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                    items(grupo) { (numero, espanol, ingles) ->
                        CardNumero(numero = numero, espanol = espanol, ingles = ingles)
                    }
                }
                if (limite != null) {
                    item {
                        Text(text = "Inicia sesión para ver los ${100 - limite} restantes.", color = colorGris, fontSize = 13.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(top = 16.dp))
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
fun PreviewNumeros() {
    LoroLingoTheme { PantallaNumeros(onVolver = {}) }
}
