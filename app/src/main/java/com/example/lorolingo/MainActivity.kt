package com.example.lorolingo

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lorolingo.ui.theme.LoroLingoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoroLingoTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavegacion()
                }
            }
        }
    }
}

// CONTROLADOR DE NAVEGACION
@Composable
fun AppNavegacion() {

    var pantallaActual by rememberSaveable {
        mutableStateOf("inicio")
    }

    when (pantallaActual) {
        "inicio"  -> PantallaInicio(
            onColores  = { pantallaActual = "colores"  },
            onNumeros  = { pantallaActual = "numeros"  }
        )
        "colores" -> PantallaColores(
            onVolver   = { pantallaActual = "inicio"   }
        )
        "numeros" -> PantallaNumeros(
            onVolver   = { pantallaActual = "inicio"   }
        )
    }
}



// PANTALLA DE INICIO

@Composable
fun PantallaInicio(
    onColores : () -> Unit,
    onNumeros : () -> Unit
) {


    val colorFondo    = Color(0xFF121212)
    val colorFondo2   = Color(0xFF1E1E1E)
    val colorCian     = Color(0xFF00F5D4)
    val colorAzul     = Color(0xFF00B4D8)
    val colorAzulMedio = Color(0xFF22A3F0)
    val colorAzulOscuro = Color(0xFF0A66E5)
    val colorBlanco   = Color(0xFFFFFFFF)
    val colorGris     = Color(0xFFAAAAAA)


    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorFondo)
            .verticalScroll(scrollState)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Logo del loro
        Image(
            painter            = painterResource(id = R.drawable.img_loro),
            contentDescription = "Mascota LoroLingo",
            modifier           = Modifier.size(180.dp),
            contentScale       = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(0.5.dp))

        // Nombre de la aplicacion
        Text(
            text          = "LoroLingo",
            fontSize      = 40.sp,
            fontWeight    = FontWeight.Bold,
            color         = colorCian,
            textAlign     = TextAlign.Center,
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Eslogan
        Text(
            text      = "Duolingo te enseña a hablar. \nSu Primo te enseña a contar.",
            fontSize  = 14.sp,
            color     = colorGris,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Boton para acceder al modulo de colores
        Button(
            onClick  = onColores,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape  = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorAzulMedio
            )
        ) {
            Text(
                text       = "Aprender Colores",
                fontSize   = 16.sp,
                fontWeight = FontWeight.Bold,
                color      = colorBlanco
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Boton para acceder al modulo de numeros
        Button(
            onClick  = onNumeros,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape  = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorAzulOscuro
            )
        ) {
            Text(
                text       = "Aprender Numeros",
                fontSize   = 16.sp,
                fontWeight = FontWeight.Bold,
                color      = colorBlanco
            )
        }

        Spacer(modifier = Modifier.height(24.dp))


        Text(
            text     = "v1.0 - Edicion Educativa",
            fontSize = 11.sp,
            color    = colorGris
        )
    }
}


// PANTALLA DE COLORES
@Composable
fun PantallaColores(onVolver: () -> Unit) {

    val colorFondo  = Color(0xFF121212)
    val colorCian   = Color(0xFF00F5D4)
    val colorBlanco = Color(0xFFFFFFFF)
    val colorGris   = Color(0xFFAAAAAA)

    // Lista de colores
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
        Triple("Marron",    "Brown",   Color(0xFF6D4C41)),
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

        // Titulo
        Text(
            text          = "Colores",
            fontSize      = 32.sp,
            fontWeight    = FontWeight.Bold,
            color         = colorCian,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Subtitulo
        Text(
            text     = "Colors in English",
            fontSize = 14.sp,
            color    = colorGris
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Tarjetas por cada color
        listaColores.forEach { (espanol, ingles, colorVisual) ->
            CardColor(
                espanol      = espanol,
                ingles       = ingles,
                colorVisual  = colorVisual
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Boton para regresar
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


// COMPONENTE REUTILIZABLE: CARD DE COLOR

@Composable
fun CardColor(
    espanol     : String,
    ingles      : String,
    colorVisual : Color
) {

    val colorFondo2 = Color(0xFF1E1E1E)
    val colorBlanco = Color(0xFFFFFFFF)
    val colorGris   = Color(0xFFAAAAAA)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorFondo2, RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(56.dp)
                .background(colorVisual, RoundedCornerShape(8.dp))
                .border(1.dp, Color(0xFF333333), RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {

            Text(
                text       = espanol,
                fontSize   = 18.sp,
                fontWeight = FontWeight.Bold,
                color      = colorBlanco
            )

            Text(
                text     = ingles,
                fontSize = 14.sp,
                color    = colorGris
            )
        }
    }
}

// PANTALLA DE NUMEROS

@Composable
fun PantallaNumeros(onVolver: () -> Unit) {

    val colorFondo  = Color(0xFF121212)
    val colorCian   = Color(0xFF00F5D4)
    val colorGris   = Color(0xFFAAAAAA)

    // Lista de nombres en ingles del 1 al 100
    val nombresIngles = listOf(
        "One", "Two", "Three", "Four", "Five",
        "Six", "Seven", "Eight", "Nine", "Ten",
        "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen",
        "Sixteen", "Seventeen", "Eighteen", "Nineteen", "Twenty",
        "Twenty-One", "Twenty-Two", "Twenty-Three", "Twenty-Four", "Twenty-Five",
        "Twenty-Six", "Twenty-Seven", "Twenty-Eight", "Twenty-Nine", "Thirty",
        "Thirty-One", "Thirty-Two", "Thirty-Three", "Thirty-Four", "Thirty-Five",
        "Thirty-Six", "Thirty-Seven", "Thirty-Eight", "Thirty-Nine", "Forty",
        "Forty-One", "Forty-Two", "Forty-Three", "Forty-Four", "Forty-Five",
        "Forty-Six", "Forty-Seven", "Forty-Eight", "Forty-Nine", "Fifty",
        "Fifty-One", "Fifty-Two", "Fifty-Three", "Fifty-Four", "Fifty-Five",
        "Fifty-Six", "Fifty-Seven", "Fifty-Eight", "Fifty-Nine", "Sixty",
        "Sixty-One", "Sixty-Two", "Sixty-Three", "Sixty-Four", "Sixty-Five",
        "Sixty-Six", "Sixty-Seven", "Sixty-Eight", "Sixty-Nine", "Seventy",
        "Seventy-One", "Seventy-Two", "Seventy-Three", "Seventy-Four", "Seventy-Five",
        "Seventy-Six", "Seventy-Seven", "Seventy-Eight", "Seventy-Nine", "Eighty",
        "Eighty-One", "Eighty-Two", "Eighty-Three", "Eighty-Four", "Eighty-Five",
        "Eighty-Six", "Eighty-Seven", "Eighty-Eight", "Eighty-Nine", "Ninety",
        "Ninety-One", "Ninety-Two", "Ninety-Three", "Ninety-Four", "Ninety-Five",
        "Ninety-Six", "Ninety-Seven", "Ninety-Eight", "Ninety-Nine", "One Hundred"
    )

    // Agrupa los numeros en listas de 10
    val decenas = nombresIngles.chunked(10)

    // Titulos de cada grupo
    val titulosDecenas = listOf(
        "1 - 10", "11 - 20", "21 - 30", "31 - 40", "41 - 50",
        "51 - 60", "61 - 70", "71 - 80", "81 - 90", "91 - 100"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorFondo)
    ) {

        // Encabezado
        Column(
            modifier            = Modifier
                .fillMaxWidth()
                .padding(24.dp, 24.dp, 24.dp, 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text          = "Numeros",
                fontSize      = 32.sp,
                fontWeight    = FontWeight.Bold,
                color         = colorCian,
                letterSpacing = 1.sp,
                modifier      = Modifier.fillMaxWidth(),
                textAlign     = TextAlign.Center
            )
            Text(
                text      = "Numbers in English",
                fontSize  = 14.sp,
                color     = colorGris,
                modifier  = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        // LazyColumn
        LazyColumn(
            modifier            = Modifier
                .weight(1f)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding      = PaddingValues(vertical = 16.dp)
        ) {

            decenas.forEachIndexed { indiceDecena, grupoNumeros ->

                item {
                    Text(
                        text          = titulosDecenas[indiceDecena],
                        fontSize      = 13.sp,
                        fontWeight    = FontWeight.Bold,
                        color         = colorCian,
                        letterSpacing = 1.sp,
                        modifier      = Modifier.padding(top = if (indiceDecena == 0) 0.dp else 8.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                }

                items(grupoNumeros) { nombreIngles ->
                    val numero = nombresIngles.indexOf(nombreIngles) + 1
                    CardNumero(
                        numero  = numero,
                        ingles  = nombreIngles
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorFondo)
                .padding(24.dp)
        ) {
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
        }
    }
}


// COMPONENTE REUTILIZABLE: CARD DE NUMERO

@Composable
fun CardNumero(
    numero : Int,
    ingles : String
) {

    val colorFondo2 = Color(0xFF1E1E1E)
    val colorCian   = Color(0xFF00F5D4)
    val colorBlanco = Color(0xFFFFFFFF)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorFondo2, RoundedCornerShape(10.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment         = Alignment.CenterVertically,
        horizontalArrangement     = Arrangement.SpaceBetween
    ) {

        Text(
            text       = numero.toString(),
            fontSize   = 22.sp,
            fontWeight = FontWeight.Bold,
            color      = colorCian,
            modifier   = Modifier.width(48.dp)
        )

        Text(
            text     = ingles,
            fontSize = 16.sp,
            color    = colorBlanco
        )
    }
}

// PREVIEWS

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewPantallaInicio() {
    LoroLingoTheme {
        PantallaInicio(onColores = {}, onNumeros = {})
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewPantallaColores() {
    LoroLingoTheme {
        PantallaColores(onVolver = {})
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewPantallaNumeros() {
    LoroLingoTheme {
        PantallaNumeros(onVolver = {})
    }
}