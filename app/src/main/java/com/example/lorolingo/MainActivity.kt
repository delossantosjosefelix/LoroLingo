package com.example.lorolingo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lorolingo.ui.screens.PantallaColores
import com.example.lorolingo.ui.screens.PantallaCuestionario
import com.example.lorolingo.ui.screens.PantallaInicio
import com.example.lorolingo.ui.screens.PantallaNumeros
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

@Composable
fun AppNavegacion() {
    var pantallaActual by rememberSaveable {
        mutableStateOf("inicio")
    }

    if (pantallaActual != "inicio") {
        BackHandler {
            pantallaActual = "inicio"
        }
    }

    AnimatedContent(
        targetState = pantallaActual,
        transitionSpec = {
            if (targetState != "inicio") {
                (slideInHorizontally { it } + fadeIn(tween(300)))
                    .togetherWith(slideOutHorizontally { -it } + fadeOut(tween(300)))
            } else {
                (slideInHorizontally { -it } + fadeIn(tween(300)))
                    .togetherWith(slideOutHorizontally { it } + fadeOut(tween(300)))
            }
        },
        label = "transicion_pantallas"
    ) { pantalla ->
        when (pantalla) {
            "inicio" -> PantallaInicio(
                onColores = { pantallaActual = "colores" },
                onNumeros = { pantallaActual = "numeros" },
                onCuestionario = { pantallaActual = "cuestionario" }
            )
            "colores" -> PantallaColores(
                onVolver = { pantallaActual = "inicio" }
            )
            "numeros" -> PantallaNumeros(
                onVolver = { pantallaActual = "inicio" }
            )
            "cuestionario" -> PantallaCuestionario(
                onVolver = { pantallaActual = "inicio" }
            )
        }
    }
}

// PREVIEWS


@Preview(name = "Pantalla de Inicio", showBackground = true, showSystemUi = true)
@Composable
fun PreviewInicio() {
    LoroLingoTheme {
        PantallaInicio(onColores = {}, onNumeros = {}, onCuestionario = {})
    }
}

@Preview(name = "Pantalla de Colores", showBackground = true, showSystemUi = true)
@Composable
fun PreviewColores() {
    LoroLingoTheme {
        PantallaColores(onVolver = {})
    }
}

@Preview(name = "Pantalla de Números", showBackground = true, showSystemUi = true)
@Composable
fun PreviewNumeros() {
    LoroLingoTheme {
        PantallaNumeros(onVolver = {})
    }
}

@Preview(name = "Pantalla de Cuestionario", showBackground = true, showSystemUi = true)
@Composable
fun PreviewCuestionario() {
    LoroLingoTheme {
        PantallaCuestionario(onVolver = {})
    }
}
