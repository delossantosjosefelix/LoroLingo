package com.example.lorolingo.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lorolingo.ui.components.ResultadoFinalCard
import com.example.lorolingo.ui.theme.LoroLingoTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class Pregunta(
    val pregunta: String,
    val opciones: List<String>,
    val respuestaCorrecta: String,
    val categoria: String // "colores", "numeros", "vocales"
)

private fun bancoDePreguntas(): List<Pregunta> {
    val preguntasColores = listOf(
        Pregunta("¿Cómo se dice Rojo en inglés?", listOf("Red", "Blue", "Green", "Black"), "Red", "colores"),
        Pregunta("¿Cómo se dice Azul en inglés?", listOf("Yellow", "Blue", "Pink", "Brown"), "Blue", "colores"),
        Pregunta("¿Cómo se dice Verde en inglés?", listOf("Purple", "Orange", "Green", "White"), "Green", "colores"),
        Pregunta("¿Cómo se dice Amarillo en inglés?", listOf("Black", "Yellow", "Orange", "Pink"), "Yellow", "colores"),
        Pregunta("¿Cómo se dice Morado en inglés?", listOf("Purple", "Brown", "Blue", "White"), "Purple", "colores"),
        Pregunta("¿Cómo se dice Negro en inglés?", listOf("White", "Black", "Gray", "Brown"), "Black", "colores"),
        Pregunta("¿Cómo se dice Blanco en inglés?", listOf("White", "Silver", "Gray", "Gold"), "White", "colores"),
        Pregunta("¿Cómo se dice Naranja en inglés?", listOf("Pink", "Purple", "Orange", "Brown"), "Orange", "colores"),
        Pregunta("¿Cómo se dice Rosado en inglés?", listOf("Pink", "Red", "Purple", "Gold"), "Pink", "colores"),
        Pregunta("¿Cómo se dice Marrón en inglés?", listOf("Gray", "Brown", "Black", "Silver"), "Brown", "colores"),
        Pregunta("¿Cómo se dice Gris en inglés?", listOf("Silver", "Gray", "White", "Brown"), "Gray", "colores"),
        Pregunta("¿Cómo se dice Plateado en inglés?", listOf("Gold", "Silver", "Gray", "White"), "Silver", "colores"),
        Pregunta("¿Cómo se dice Dorado en inglés?", listOf("Gold", "Yellow", "Orange", "Silver"), "Gold", "colores"),
        Pregunta("¿Qué color es 'Green' en español?", listOf("Verde", "Azul", "Gris", "Rosado"), "Verde", "colores"),
        Pregunta("¿Qué color es 'Purple' en español?", listOf("Marrón", "Morado", "Negro", "Dorado"), "Morado", "colores")
    )

    val preguntasVocales = listOf(
        Pregunta("¿Cómo se pronuncia la 'A' en inglés?", listOf("Ei", "Ai", "Ah", "I"), "Ei", "vocales"),
        Pregunta("¿Cómo se pronuncia la 'E' en inglés?", listOf("Eh", "I", "Ei", "U"), "I", "vocales"),
        Pregunta("¿Cómo se pronuncia la 'I' en inglés?", listOf("I", "Ei", "Ai", "Ee"), "Ai", "vocales"),
        Pregunta("¿Cómo se pronuncia la 'O' en inglés?", listOf("O", "Ou", "U", "Oi"), "Ou", "vocales"),
        Pregunta("¿Cómo se pronuncia la 'U' en inglés?", listOf("U", "Iu", "Ei", "Ah"), "Iu", "vocales"),
        Pregunta("¿Cuál es un ejemplo para la vocal 'A'?", listOf("Apple", "Elephant", "Island", "Owl"), "Apple", "vocales"),
        Pregunta("¿Cuál es un ejemplo para la vocal 'E'?", listOf("Orange", "Egg", "Ukelele", "Alligator"), "Egg", "vocales"),
        Pregunta("¿Cuál es un ejemplo para la vocal 'I'?", listOf("Ice Cream", "Apple", "Owl", "Egg"), "Ice Cream", "vocales"),
        Pregunta("¿Cuál es un ejemplo para la vocal 'O'?", listOf("Owl", "Elephant", "Ukelele", "Island"), "Owl", "vocales"),
        Pregunta("¿Cuál es un ejemplo para la vocal 'U'?", listOf("Uranus", "Apple", "Orange", "Egg"), "Uranus", "vocales")
    )

    val preguntasNumeros = listOf(
        Pregunta("¿Cómo se escribe el número 1 en inglés?", listOf("One", "Two", "Three", "Four"), "One", "numeros"),
        Pregunta("¿Cómo se escribe el número 2 en inglés?", listOf("Two", "One", "Three", "Six"), "Two", "numeros"),
        Pregunta("¿Cómo se escribe el número 3 en inglés?", listOf("Three", "Thirteen", "Two", "Eight"), "Three", "numeros"),
        Pregunta("¿Cómo se escribe el número 4 en inglés?", listOf("Forty", "Four", "Fourteen", "Five"), "Four", "numeros"),
        Pregunta("¿Cómo se escribe el número 5 en inglés?", listOf("Four", "Six", "Five", "Seven"), "Five", "numeros"),
        Pregunta("¿Cómo se escribe el número 6 en inglés?", listOf("Sixty", "Sixteen", "Six", "Seven"), "Six", "numeros"),
        Pregunta("¿Cómo se escribe el número 7 en inglés?", listOf("Seven", "Seventeen", "Seventy", "Six"), "Seven", "numeros"),
        Pregunta("¿Cómo se escribe el número 8 en inglés?", listOf("Eighteen", "Eight", "Eighty", "Nine"), "Eight", "numeros"),
        Pregunta("¿Cómo se escribe el número 9 en inglés?", listOf("Ninety", "Nineteen", "Nine", "Ten"), "Nine", "numeros"),
        Pregunta("¿Cómo se escribe el número 10 en inglés?", listOf("Ten", "Nine", "Eight", "Eleven"), "Ten", "numeros"),
        Pregunta("¿Cómo se escribe el número 15 en inglés?", listOf("Fifteen", "Fifty", "Fifteenth", "Five"), "Fifteen", "numeros"),
        Pregunta("¿Cómo se escribe el número 20 en inglés?", listOf("Thirty", "Twenty", "Forty", "Ten"), "Twenty", "numeros"),
        Pregunta("¿Cómo se escribe el número 30 en inglés?", listOf("Thirteen", "Thirty", "Forty", "Three"), "Thirty", "numeros"),
        Pregunta("¿Cómo se escribe el número 50 en inglés?", listOf("Fifteen", "Fifty", "Five", "Fifty-One"), "Fifty", "numeros"),
        Pregunta("¿Cómo se escribe el número 100 en inglés?", listOf("One Thousand", "One Hundred", "Hundred One", "Ten Hundred"), "One Hundred", "numeros")
    )

    return preguntasColores + preguntasNumeros + preguntasVocales
}

private fun escogerPreguntasAleatorias(cantidad: Int): List<Pregunta> {
    return bancoDePreguntas().shuffled().take(cantidad)
}

@Composable
fun PantallaCuestionario(
    onVolver: () -> Unit,
    onFinish: (Int, Int, Map<String, Int>) -> Unit = { _, _, _ -> }
) {
    val colorFondo   = Color(0xFF121212)
    val colorFondo2  = Color(0xFF1E1E1E)
    val colorCian    = Color(0xFF00F5D4)
    val colorBlanco  = Color(0xFFFFFFFF)
    val colorGris    = Color(0xFFAAAAAA)
    val colorVerde   = Color(0xFF2E7D32)
    val colorRojo    = Color(0xFFE53935)
    val colorAzulOscuro = Color(0xFF0A66E5)
    val colorAmarillo = Color(0xFFFDD835)

    val preguntas = remember { escogerPreguntasAleatorias(10) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val respuestas = remember {
        mutableStateListOf<String?>().apply {
            repeat(preguntas.size) { add(null) }
        }
    }

    var mostrarResultado by remember { mutableStateOf(false) }
    var puntaje by remember { mutableIntStateOf(0) }

    val respondidas = respuestas.count { it != null }
    val progreso by animateFloatAsState(
        targetValue = respondidas / preguntas.size.toFloat(),
        animationSpec = tween(durationMillis = 400),
        label = "progreso"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorFondo)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp, 24.dp, 24.dp, 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text          = "Cuestionario",
                fontSize      = 32.sp,
                fontWeight    = FontWeight.Bold,
                color         = colorCian,
                letterSpacing = 1.sp,
                modifier      = Modifier.fillMaxWidth(),
                textAlign     = TextAlign.Center
            )
            Text(
                text      = "Colores, Números y Vocales",
                fontSize  = 14.sp,
                color     = colorGris,
                modifier  = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (!mostrarResultado) {
                Text(
                    text     = "$respondidas / ${preguntas.size} respondidas",
                    fontSize = 12.sp,
                    color    = colorGris,
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(
                    progress = { progreso },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = colorCian,
                    trackColor = colorFondo2
                )
            }
        }

        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(preguntas.size) { index ->
                val pregunta = preguntas[index]
                val respuestaSeleccionada = respuestas[index]

                val colorBordeObjetivo = when {
                    !mostrarResultado -> Color.Transparent
                    respuestaSeleccionada == pregunta.respuestaCorrecta -> colorVerde
                    else -> colorRojo
                }

                val colorBorde by animateColorAsState(
                    targetValue = colorBordeObjetivo,
                    animationSpec = tween(durationMillis = 400),
                    label = "colorBorde"
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colorFondo2, RoundedCornerShape(12.dp))
                        .border(2.dp, colorBorde, RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Text(
                        text       = "${index + 1}. ${pregunta.pregunta}",
                        fontWeight = FontWeight.Bold,
                        fontSize   = 16.sp,
                        color      = colorBlanco
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    pregunta.opciones.forEach { opcion ->
                        val esSeleccionada = respuestaSeleccionada == opcion
                        val esCorrecta = opcion == pregunta.respuestaCorrecta

                        val colorFondoOpcion = when {
                            !mostrarResultado && esSeleccionada -> colorAzulOscuro.copy(alpha = 0.3f)
                            mostrarResultado && esCorrecta -> colorVerde.copy(alpha = 0.25f)
                            mostrarResultado && esSeleccionada && !esCorrecta -> colorRojo.copy(alpha = 0.25f)
                            else -> Color.Transparent
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(colorFondoOpcion)
                                .clickable(enabled = !mostrarResultado) {
                                    respuestas[index] = opcion
                                }
                                .padding(vertical = 6.dp, horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = esSeleccionada,
                                onClick = {
                                    if (!mostrarResultado) {
                                        respuestas[index] = opcion
                                    }
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = colorCian,
                                    unselectedColor = colorGris
                                )
                            )

                            Text(
                                text  = opcion,
                                color = colorBlanco,
                                modifier = Modifier.weight(1f)
                            )

                            if (mostrarResultado && (esCorrecta || esSeleccionada)) {
                                val icono = if (esCorrecta) Icons.Filled.Check else Icons.Filled.Close
                                val colorIcono = if (esCorrecta) colorVerde else colorRojo

                                Icon(
                                    imageVector = icono,
                                    contentDescription = null,
                                    tint = colorIcono,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }

                    AnimatedVisibility(
                        visible = mostrarResultado,
                        enter = fadeIn(animationSpec = tween(400)) + expandVertically(),
                        exit  = fadeOut() + shrinkVertically()
                    ) {
                        Column {
                            Spacer(modifier = Modifier.height(8.dp))

                            val mensaje = when {
                                respuestaSeleccionada == pregunta.respuestaCorrecta -> "¡Correcto!"
                                respuestaSeleccionada == null -> "Sin responder. Respuesta correcta: ${pregunta.respuestaCorrecta}"
                                else -> "Incorrecto. Respuesta correcta: ${pregunta.respuestaCorrecta}"
                            }

                            val colorMensaje = if (respuestaSeleccionada == pregunta.respuestaCorrecta) {
                                colorVerde
                            } else {
                                colorRojo
                            }

                            Text(
                                text       = mensaje,
                                color      = colorMensaje,
                                fontWeight = FontWeight.Bold,
                                fontSize   = 13.sp
                            )
                        }
                    }
                }
            }

            // 1. RESULTADO (Aparece primero si ya se calificó)
            item {
                if (mostrarResultado) {
                    ResultadoFinalCard(
                        puntaje = puntaje,
                        total   = preguntas.size,
                        colorCian = colorCian,
                        colorVerde = colorVerde,
                        colorAmarillo = colorAmarillo,
                        colorRojo = colorRojo,
                        colorFondo2 = colorFondo2,
                        colorBlanco = colorBlanco,
                        colorGris = colorGris
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // 2. BOTONES DE ACCIÓN (Calificar y Salir)
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            var score = 0
                            val correctasPorCategoria = mutableMapOf("colores" to 0, "numeros" to 0, "vocales" to 0)
                            
                            preguntas.forEachIndexed { index, pregunta ->
                                if (respuestas[index] == pregunta.respuestaCorrecta) {
                                    score++
                                    correctasPorCategoria[pregunta.categoria] = (correctasPorCategoria[pregunta.categoria] ?: 0) + 1
                                }
                            }
                            puntaje = score
                            mostrarResultado = true
                            
                            // Notificar resultados con desglose por categoría
                            onFinish(score, preguntas.size, correctasPorCategoria)

                            // Auto-scroll para mostrar el resultado arriba de los botones
                            coroutineScope.launch {
                                delay(600)
                                // Scroll a la posición del resultado (que es el item justo después de las preguntas)
                                listState.animateScrollToItem(preguntas.size) 
                            }
                        },
                        enabled = !mostrarResultado,
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .padding(end = 8.dp),
                        shape  = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorAzulOscuro
                        )
                    ) {
                        Text(
                            text       = "Calificar",
                            fontSize   = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color      = colorBlanco
                        )
                    }

                    Button(
                        onClick = onVolver,
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .padding(start = 8.dp),
                        shape  = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorCian
                        )
                    ) {
                        Text(
                            text       = "Salir",
                            fontSize   = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color      = Color(0xFF121212)
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview(name = "Pantalla de Cuestionario", showBackground = true, showSystemUi = true)
@Composable
fun PreviewCuestionario() {
    LoroLingoTheme {
        PantallaCuestionario(onVolver = {})
    }
}
