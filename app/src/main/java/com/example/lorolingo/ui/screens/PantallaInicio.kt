package com.example.lorolingo.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lorolingo.R
import com.example.lorolingo.ui.theme.LoroLingoTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Forma para la "colita" de la burbuja
val BubbleShape = GenericShape { size, _ ->
    val rectRadius = 16.dp.value
    addRoundRect(
        androidx.compose.ui.geometry.RoundRect(
            left = 0f,
            top = 0f,
            right = size.width,
            bottom = size.height - 15f,
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(rectRadius)
        )
    )
    // Triángulo (colita)
    moveTo(size.width / 2 - 20f, size.height - 15f)
    lineTo(size.width / 2, size.height)
    lineTo(size.width / 2 + 20f, size.height - 15f)
    close()
}

@Composable
fun PantallaInicio(
    onColores : () -> Unit,
    onNumeros : () -> Unit,
    onCuestionario : () -> Unit
) {
    val colorFondo    = Color(0xFF121212)
    val colorCian     = Color(0xFF00F5D4)
    val colorAzulMedio = Color(0xFF22A3F0)
    val colorAzulOscuro = Color(0xFF0A66E5)
    val colorVerde    = Color(0xFF2E7D32)

    val colorNaranjaOscuro = Color(0xFFE57A0D)
    val colorBlanco   = Color(0xFFFFFFFF)
    val colorGris     = Color(0xFFAAAAAA)
    val colorFondoCard = Color(0xFF1E1E1E)

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    // Lógica para la burbuja de diálogo interactiva
    var mostrarBurbuja by remember { mutableStateOf(false) }
    
    // Función para manejar el clic en el loro
    val alTocarLoro = {
        if (!mostrarBurbuja) {
            mostrarBurbuja = true
            coroutineScope.launch {
                delay(10000)
                mostrarBurbuja = false
            }
        } else {
            mostrarBurbuja = false
        }
    }

    //Frases Aleatorias
    val frases = remember {
        listOf(
            "¡El que persevera, alcanza!",
            "Aprender un idioma abre mil puertas.",
            "¿Sabías que el LoroLingo es primo de Duolingo?",
            "Practica 5 minutos al día y verás la diferencia.",
            "Los colores en inglés son la base de todo.",
            "¡Hoy es un gran día para aprender algo nuevo!"
        )
    }
    val fraseDelDia = remember { frases.random() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorFondo)
            .verticalScroll(scrollState)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Burbuja de diálogo
        AnimatedVisibility(
            visible = mostrarBurbuja,
            enter = fadeIn(tween(600)) + expandVertically(tween(600)) + scaleIn(tween(600)),
            exit = fadeOut(tween(500)) + shrinkVertically(tween(500)) + scaleOut(tween(500))
        ) {
            Surface(
                color = colorFondoCard,
                shape = BubbleShape,
                border = androidx.compose.foundation.BorderStroke(1.5.dp, colorCian.copy(alpha = 0.6f)),
                modifier = Modifier
                    .widthIn(max = 260.dp)
                    .padding(bottom = 12.dp)
                    .shadow(8.dp, BubbleShape)
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontSize = 14.sp, color = colorBlanco)) {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = colorNaranjaOscuro)) {
                                append("Duolingo")
                            }
                            append(" te enseña a hablar.\n")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = colorCian)) {
                                append("Su Primo ")
                            }
                            append("te enseña a contar.")
                        }
                    },
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )
            }
        }

        Image(
            painter            = painterResource(id = R.drawable.img_loro),
            contentDescription = "Mascota LoroLingo",
            modifier           = Modifier
                .size(180.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null // Sin efecto visual gris al tocar para que sea más natural
                ) {
                    alTocarLoro()
                },
            contentScale       = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(0.5.dp))

        Text(
            text          = "LoroLingo",
            fontSize      = 40.sp,
            fontWeight    = FontWeight.Bold,
            color         = colorCian,
            textAlign     = TextAlign.Center,
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(48.dp))

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
                text       = "Aprender Números",
                fontSize   = 16.sp,
                fontWeight = FontWeight.Bold,
                color      = colorBlanco
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick  = onCuestionario,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape  = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorNaranjaOscuro
            )
        ) {
            Text(
                text       = "Cuestionario",
                fontSize   = 16.sp,
                fontWeight = FontWeight.Bold,
                color      = colorBlanco
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Card de Frase del Día
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)),
            color = colorFondoCard
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = null,
                    tint = colorCian,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Tip del día",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorCian
                    )
                    Text(
                        text = fraseDelDia,
                        fontSize = 13.sp,
                        color = colorBlanco,
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text     = "v1.1.0 - Edición Educativa",
            fontSize = 11.sp,
            color    = colorGris
        )
    }
}

@Preview(name = "Pantalla de Inicio", showBackground = true, showSystemUi = true)
@Composable
fun PreviewInicio() {
    LoroLingoTheme {
        PantallaInicio(onColores = {}, onNumeros = {}, onCuestionario = {})
    }
}
