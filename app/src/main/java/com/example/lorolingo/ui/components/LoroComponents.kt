package com.example.lorolingo.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lorolingo.R

@Composable
fun StudyTimer(
    onSessionComplete: () -> Unit
) {
    var secondsLeft by remember { mutableIntStateOf(180) } // 3 minutos
    var sessionAwarded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (secondsLeft > 0) {
            kotlinx.coroutines.delay(1000)
            // Verificar si el componente sigue activo antes de restar
            if (secondsLeft > 0) {
                secondsLeft--
            }
        }
        // Solo premiamos si el tiempo realmente llegó a 0 y no se ha premiado aún
        if (secondsLeft == 0 && !sessionAwarded) {
            sessionAwarded = true
            onSessionComplete()
        }
    }

    val minutes = secondsLeft / 60
    val seconds = secondsLeft % 60
    val progress = secondsLeft / 180f

    Surface(
        color = Color(0xFF1E1E1E),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF00F5D4).copy(alpha = 0.3f)),
        modifier = Modifier
            .fillMaxWidth() // Extendida a todo el ancho
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.size(40.dp),
                    color = Color(0xFF00F5D4),
                    trackColor = Color.Gray.copy(alpha = 0.2f),
                    strokeWidth = 3.dp
                )
                Icon(
                    imageVector = Icons.Default.Timer,
                    contentDescription = null,
                    tint = Color(0xFF00F5D4),
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = if (secondsLeft > 0) "Sesión de estudio" else "¡Sesión Completada!",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if (secondsLeft > 0) 
                        String.format("%02d:%02d para +1 Sesión", minutes, seconds)
                        else "¡Has ganado +1 sesión!",
                    color = if (secondsLeft > 0) Color.Gray else Color(0xFF00F5D4),
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
fun CardVocal(
    vocal: String,
    pronunciacion: String,
    ejemplo1: String,
    icono1: Int,
    ejemplo2: String,
    icono2: Int,
    colorVisual: Color
) {
    val colorFondo2 = Color(0xFF1E1E1E)
    val colorBlanco = Color(0xFFFFFFFF)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorFondo2, RoundedCornerShape(24.dp))
            .border(1.dp, colorVisual.copy(alpha = 0.2f), RoundedCornerShape(24.dp))
            .padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(colorVisual.copy(alpha = 0.15f), CircleShape)
                    .border(2.dp, colorVisual.copy(alpha = 0.4f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = vocal,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black,
                    color = colorVisual
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "Vowel $vocal",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorBlanco
                )
                Text(
                    text = "Pronunciation: $pronunciacion",
                    fontSize = 14.sp,
                    color = colorVisual.copy(alpha = 0.8f),
                    fontWeight = FontWeight.Medium
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(), 
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                VocalExampleItem(ejemplo1, icono1)
            }
            Box(modifier = Modifier.weight(1f)) {
                VocalExampleItem(ejemplo2, icono2)
            }
        }
    }
}

@Composable
fun VocalExampleItem(nombre: String, icono: Int) {
    Surface(
        color = Color.White.copy(alpha = 0.03f),
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.1f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(12.dp)
        ) {
            Image(
                painter = painterResource(id = icono),
                contentDescription = null,
                modifier = Modifier
                    .size(110.dp) // Aumentado significativamente
                    .padding(4.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = nombre,
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

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

@Composable
fun CardNumero(
    numero  : Int,
    espanol : String,
    ingles  : String
) {
    val colorFondo2 = Color(0xFF1E1E1E)
    val colorCian   = Color(0xFF00F5D4)
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
                .background(colorCian.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                .border(1.dp, colorCian.copy(alpha = 0.3f), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text       = numero.toString(),
                fontSize   = 20.sp,
                fontWeight = FontWeight.Bold,
                color      = colorCian
            )
        }

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

@Composable
fun ResultadoFinalCard(
    puntaje: Int,
    total: Int,
    colorCian: Color,
    colorVerde: Color,
    colorAmarillo: Color,
    colorRojo: Color,
    colorFondo2: Color,
    colorBlanco: Color,
    colorGris: Color
) {
    val porcentaje = puntaje.toFloat() / total.toFloat()

    val progresoAnimado by animateFloatAsState(
        targetValue = porcentaje,
        animationSpec = tween(durationMillis = 800),
        label = "progresoFinal"
    )

    val colorResultado = when {
        puntaje == 10 -> colorVerde
        puntaje >= 7 -> colorVerde
        puntaje >= 3 -> colorAmarillo
        else -> colorRojo
    }

    val mensajeResultado = when (puntaje) {
        10 -> "¡Perfecto!"
        in 7..9 -> "¡Muy bien!"
        in 3..6 -> "¡Puedes mejorar!"
        else -> "¡No te rindas!"
    }

    val mensajeMotivador = when (puntaje) {
        10 -> "¡Felicidades! Tienes un desempeño excelente. ¡Eres un maestro!"
        in 7..9 -> "¡Muy bien hecho! Sigue así, estás muy cerca de la perfección."
        in 3..6 -> "¡Buen intento! Te motivo a intentar de nuevo para mejorar tu puntaje."
        else -> "¡No te rindas! Sigue practicando y verás cómo mejoras pronto."
    }

    val imagenLoro = when (puntaje) {
        10 -> R.drawable.img_lorotrofeo
        in 7..9 -> R.drawable.img_lorofeliz
        in 3..6 -> R.drawable.img_lorodecepcionado
        else -> R.drawable.img_lorotriste
    }

    AnimatedVisibility(
        visible = true,
        enter = fadeIn(animationSpec = tween(600)) + expandVertically()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorFondo2, RoundedCornerShape(16.dp))
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = imagenLoro),
                contentDescription = "Loro resultado",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 12.dp)
            )

            Box(
                modifier = Modifier.size(140.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = { progresoAnimado },
                    modifier = Modifier.size(140.dp),
                    strokeWidth = 10.dp,
                    color = colorResultado,
                    trackColor = Color(0xFF333333)
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text       = "$puntaje/$total",
                        fontSize   = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color      = colorBlanco
                    )
                    Text(
                        text     = "${(porcentaje * 100).toInt()}%",
                        fontSize = 13.sp,
                        color    = colorGris
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text       = mensajeResultado,
                fontSize   = 20.sp,
                fontWeight = FontWeight.Bold,
                color      = colorResultado
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text      = mensajeMotivador,
                fontSize  = 14.sp,
                color     = colorBlanco,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text      = "Obtuviste $puntaje de $total respuestas correctas.",
                fontSize  = 12.sp,
                color     = colorGris,
                textAlign = TextAlign.Center
            )
        }
    }
}
