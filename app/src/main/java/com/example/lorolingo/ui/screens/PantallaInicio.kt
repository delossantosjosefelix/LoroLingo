package com.example.lorolingo.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lorolingo.R
import com.example.lorolingo.data.local.entities.User
import com.example.lorolingo.ui.components.CardColor
import com.example.lorolingo.ui.components.CardNumero
import com.example.lorolingo.ui.theme.LoroLingoTheme
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.launch

// Forma para la burbuja del Loro (Colita a la izquierda, apuntando al loro)
val BubbleCoachShape = GenericShape { size, _ ->
    val rectRadius = 20.dp.value
    addRoundRect(
        androidx.compose.ui.geometry.RoundRect(
            left = 0f,
            top = 0f,
            right = size.width,
            bottom = size.height - 15f,
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(rectRadius)
        )
    )
    // Flechita a la izquierda inferior (apuntando al loro asomado)
    moveTo(30f, size.height - 15f)
    lineTo(15f, size.height)
    lineTo(45f, size.height - 15f)
    close()
}

@Composable
fun PantallaInicio(
    nombreUsuario: String,
    esInvitado: Boolean,
    usuarioReal: User? = null,
    onColores: () -> Unit,
    onNumeros: () -> Unit,
    onCuestionario: () -> Unit,
    onShowRestriccion: () -> Unit,
    onIrALogin: () -> Unit,
    onIrAPerfil: () -> Unit
) {
    val colorFondo = Color(0xFF121212)
    val colorDegradadoTop = Color(0xFF1A1A2E)
    val colorFondoCard = Color(0xFF1E1E1E)
    val colorCian = Color(0xFF00F5D4)
    val colorBlanco = Color(0xFFFFFFFF)
    val colorGris = Color(0xFFAAAAAA)
    val colorNaranja = Color(0xFFE57A0D)
    val colorAzul = Color(0xFF0A66E5)
    val colorRojo = Color(0xFFE53935)

    var searchText by remember { mutableStateOf("") }
    val isSearching = searchText.isNotEmpty()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Estados para diálogos de explicación
    var showStreakDialog by remember { mutableStateOf(false) }
    var showLevelDialog by remember { mutableStateOf(false) }

    // Lógica para Tip del día dinámico
    val tips = listOf(
        "¡Practicar en voz alta te ayuda a memorizar más rápido!",
        "La constancia es la clave: 5 minutos al día es mejor que 1 hora a la semana.",
        "Trata de usar las palabras que aprendes en frases de tu vida diaria.",
        "¿Sabías que los loros pueden aprender cientos de palabras? ¡Tú también!",
        "Escuchar canciones en inglés ayuda a acostumbrar el oído al ritmo del idioma."
    )
    var currentTipIndex by remember { mutableIntStateOf(0) }
    
    LaunchedEffect(Unit) {
        while(true) {
            kotlinx.coroutines.delay(10000) // Cambia cada 10 segundos
            currentTipIndex = (currentTipIndex + 1) % tips.size
        }
    }

    val sdf = SimpleDateFormat("EEEE, d 'de' MMMM", Locale("es", "ES"))
    val fechaActual = sdf.format(Date()).replaceFirstChar { it.uppercase() }

    val listaColoresCompleta = remember {
        listOf(
            Triple("Rojo", "Red", Color(0xFFE53935)), Triple("Azul", "Blue", Color(0xFF1E88E5)),
            Triple("Amarillo", "Yellow", Color(0xFFFDD835)), Triple("Verde", "Green", Color(0xFF43A047)),
            Triple("Negro", "Black", Color(0xFF212121)), Triple("Blanco", "White", Color(0xFFFFFFFF)),
            Triple("Naranja", "Orange", Color(0xFFFB8C00)), Triple("Morado", "Purple", Color(0xFF8E24AA)),
            Triple("Rosado", "Pink", Color(0xFFE91E8C)), Triple("Marrón", "Brown", Color(0xFF6D4C41)),
            Triple("Gris", "Gray", Color(0xFF757575)), Triple("Plateado", "Silver", Color(0xFFB0BEC5)),
            Triple("Dorado", "Gold", Color(0xFFFFD700))
        )
    }

    val listaNumerosCompleta = remember {
        (1..100).map { i ->
            val nombresEspanol = listOf("Uno", "Dos", "Tres", "Cuatro", "Cinco", "Seis", "Siete", "Ocho", "Nueve", "Diez", "Once", "Doce", "Trece", "Catorce", "Quince", "Dieciséis", "Diecisiete", "Dieciocho", "Diecinueve", "Veinte", "Veintiuno", "Veintidós", "Veintitrés", "Veinticuatro", "Veinticinco", "Veintiséis", "Veintisiete", "Veintiocho", "Veintinueve", "Treinta", "Treinta y uno", "Treinta y dos", "Treinta y tres", "Treinta y cuatro", "Treinta y cinco", "Treinta y seis", "Treinta y siete", "Treinta y ocho", "Treinta y nueve", "Cuarenta", "Cuarenta y uno", "Cuarenta y dos", "Cuarenta y tres", "Cuarenta y cuatro", "Cuarenta y cinco", "Cuarenta y seis", "Cuarenta y siete", "Cuarenta y ocho", "Cuarenta y nueve", "Cincuenta", "Cincuenta y uno", "Cincuenta y dos", "Cincuenta y tres", "Cincuenta y cuatro", "Cincuenta y cinco", "Cincuenta y seis", "Cincuenta y siete", "Cincuenta y ocho", "Cincuenta y nueve", "Sesenta", "Sesenta y uno", "Sesenta y dos", "Sesenta y tres", "Sesenta y cuatro", "Sesenta y cinco", "Sesenta y seis", "Sesenta y siete", "Sesenta y ocho", "Sesenta y nueve", "Setenta", "Setenta y uno", "Setenta y dos", "Setenta y tres", "Setenta y cuatro", "Setenta y cinco", "Setenta y seis", "Setenta y siete", "Setenta y ocho", "Setenta y nueve", "Ochenta", "Ochenta y uno", "Ochenta y dos", "Ochenta y tres", "Ochenta y cuatro", "Ochenta y cinco", "Ochenta y seis", "Ochenta y siete", "Ochenta y ocho", "Ochenta y nueve", "Noventa", "Noventa y uno", "Ninety-Two", "Ninety-Three", "Ninety-Four", "Ninety-Five", "Ninety-Six", "Ninety-Seven", "Ninety-Eight", "Ninety-Nine", "Cien")
            val nombresIngles = listOf("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen", "Twenty", "Twenty-One", "Twenty-Two", "Twenty-Three", "Twenty-Four", "Twenty-Five", "Twenty-Six", "Twenty-Seven", "Twenty-Eight", "Twenty-Nine", "Thirty", "Thirty-One", "Thirty-Two", "Thirty-Three", "Thirty-Four", "Thirty-Five", "Thirty-Six", "Thirty-Seven", "Thirty-Eight", "Thirty-Nine", "Forty", "Forty-One", "Forty-Two", "Forty-Three", "Forty-Four", "Forty-Five", "Forty-Six", "Forty-Seven", "Forty-Eight", "Forty-Nine", "Fifty", "Fifty-One", "Fifty-Two", "Fifty-Three", "Fifty-Four", "Fifty-Five", "Fifty-Six", "Fifty-Seven", "Fifty-Eight", "Fifty-Nine", "Sixty", "Sixty-One", "Sixty-Two", "Sixty-Three", "Sixty-Four", "Sixty-Five", "Sixty-Six", "Sixty-Seven", "Sixty-Eight", "Sixty-Nine", "Seventy", "Seventy-One", "Seventy-Two", "Seventy-Three", "Seventy-Four", "Seventy-Five", "Seventy-Six", "Seventy-Seven", "Seventy-Eight", "Seventy-Nine", "Eighty", "Eighty-One", "Eighty-Two", "Eighty-Three", "Eighty-Four", "Eighty-Five", "Eighty-Six", "Eighty-Seven", "Eighty-Eight", "Eighty-Nine", "Ninety", "Ninety-One", "Ninety-Two", "Ninety-Three", "Ninety-Four", "Ninety-Five", "Ninety-Six", "Ninety-Seven", "Ninety-Eight", "Ninety-Nine", "One Hundred")
            Triple(i, nombresEspanol[i - 1], nombresIngles[i - 1])
        }
    }

    val resultadosColores = (if (esInvitado) listaColoresCompleta.take(3) else listaColoresCompleta).filter { 
        it.first.contains(searchText, ignoreCase = true) || it.second.contains(searchText, ignoreCase = true)
    }
    val resultadosNumeros = (if (esInvitado) listaNumerosCompleta.take(5) else listaNumerosCompleta).filter { 
        it.first.toString() == searchText || it.second.contains(searchText, ignoreCase = true) || it.third.contains(searchText, ignoreCase = true)
    }

    // --- DIÁLOGOS DE EXPLICACIÓN MEJORADOS (ESTILO PREMIUM) ---
    if (showStreakDialog) {
        AlertDialog(
            onDismissRequest = { showStreakDialog = false },
            confirmButton = {
                Button(
                    onClick = { if (esInvitado) onIrALogin() else showStreakDialog = false },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorCian)
                ) {
                    Text(
                        text = if (esInvitado) "CREAR CUENTA AHORA" else "¡ENTENDIDO!", 
                        color = colorFondo, 
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                }
            },
            icon = {
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .background(colorNaranja.copy(alpha = 0.15f), CircleShape)
                        .shadow(10.dp, CircleShape, spotColor = colorNaranja),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.LocalFireDepartment, 
                        contentDescription = null, 
                        tint = colorNaranja, 
                        modifier = Modifier.size(38.dp)
                    )
                }
            },
            title = { 
                Text(
                    text = "Racha de Loro", 
                    color = colorBlanco, 
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold, 
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                ) 
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = if (esInvitado) 
                            "La racha mide tu constancia diaria aprendiendo. Como invitado no podemos guardar tus logros. ¡Inicia sesión para empezar tu camino al éxito hoy!" 
                            else "Mantienes tu racha practicando al menos una vez al día. ¡Si completas una racha de 7 días recibirás un Punto de Racha extra!",
                        color = colorGris,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp
                    )
                }
            },
            containerColor = colorFondoCard,
            shape = RoundedCornerShape(28.dp)
        )
    }

    if (showLevelDialog) {
        AlertDialog(
            onDismissRequest = { showLevelDialog = false },
            confirmButton = {
                Button(
                    onClick = { if (esInvitado) onIrALogin() else showLevelDialog = false },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorCian)
                ) {
                    Text(
                        text = if (esInvitado) "UNIRME A LOROLINGO" else "¡GENIAL!", 
                        color = colorFondo, 
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                }
            },
            icon = {
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .background(colorAzul.copy(alpha = 0.15f), CircleShape)
                        .shadow(10.dp, CircleShape, spotColor = colorAzul),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome, 
                        contentDescription = null, 
                        tint = colorAzul, 
                        modifier = Modifier.size(38.dp)
                    )
                }
            },
            title = { 
                Text(
                    text = "Nivel de Maestro", 
                    color = colorBlanco, 
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold, 
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                ) 
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = if (esInvitado) 
                            "Tu nivel sube al completar lecciones y cuestionarios. ¡Regístrate para desbloquear todos los niveles y convertirte en un experto de LoroLingo!" 
                            else "Subes de nivel automáticamente cada 5 lecciones terminadas con éxito. ¡Sigue así para desbloquear nuevos rangos!",
                        color = colorGris,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp
                    )
                }
            },
            containerColor = colorFondoCard,
            shape = RoundedCornerShape(28.dp)
        )
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Card(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 90.dp) // Añadido vertical de 90dp para subirlo
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = colorFondoCard),
                    border = androidx.compose.foundation.BorderStroke(1.dp, colorRojo)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Warning, null, tint = colorRojo)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = data.visuals.message,
                            color = colorBlanco,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(colorDegradadoTop, colorFondo)
                    )
                )
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                // --- IDENTIDAD DE LA APP ---
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Loro",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black,
                        color = colorCian,
                        letterSpacing = (-1).sp
                    )
                    Text(
                        text = "Lingo",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black,
                        color = colorCian,
                        letterSpacing = (-1).sp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Cabecera: Avatar Izquierda (Gris si es invitado) + Saludo
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .background(colorFondoCard, CircleShape)
                            .clip(CircleShape)
                            .clickable { if (esInvitado) onIrALogin() else onIrAPerfil() },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_profile),
                            contentDescription = "Foto de perfil",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            colorFilter = if (esInvitado) {
                                ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
                            } else null
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = "¡Hola, $nombreUsuario!",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorBlanco
                        )
                        Text(
                            text = fechaActual,
                            fontSize = 12.sp,
                            color = colorGris,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // BARRA DE BÚSQUEDA
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    modifier = Modifier.fillMaxWidth().shadow(10.dp, RoundedCornerShape(28.dp)),
                    placeholder = { Text("Busca colores o números...", color = colorGris, fontSize = 14.sp) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = colorCian) },
                    trailingIcon = {
                        if (isSearching) {
                            IconButton(onClick = { searchText = "" }) {
                                Icon(Icons.Default.Close, contentDescription = null, tint = colorGris)
                            }
                        }
                    },
                    shape = RoundedCornerShape(28.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = colorFondoCard,
                        unfocusedContainerColor = colorFondoCard,
                        focusedBorderColor = colorCian,
                        unfocusedBorderColor = Color.Transparent,
                        focusedTextColor = colorBlanco,
                        unfocusedTextColor = colorBlanco
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(32.dp))

                if (isSearching) {
                    // RESULTADOS
                    Text("Resultados", color = colorCian, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))

                    if (resultadosColores.isEmpty() && resultadosNumeros.isEmpty()) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                            Image(
                                painter = painterResource(id = R.drawable.img_lorotriste),
                                contentDescription = null,
                                modifier = Modifier.size(130.dp)
                            )
                            Text(
                                "¡Caww! No hay resultados permitidos o no encontré lo que buscas.",
                                color = colorGris,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(24.dp)
                            )
                        }
                    } else {
                        resultadosColores.forEach { (esp, ing, col) ->
                            CardColor(esp, ing, col)
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                        resultadosNumeros.forEach { (num, esp, ing) ->
                            CardNumero(num, esp, ing)
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(100.dp))
                } else {
                    // LORO COACH
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(modifier = Modifier.width(130.dp).offset(x = (-20).dp)) {
                            Image(
                                painter = painterResource(id = R.drawable.img_loro),
                                contentDescription = null,
                                modifier = Modifier.size(130.dp),
                                contentScale = ContentScale.Fit
                            )
                        }
                        
                        Surface(
                            color = colorFondoCard,
                            shape = BubbleCoachShape,
                            modifier = Modifier
                                .weight(1f)
                                .padding(top = 15.dp)
                                .shadow(8.dp, BubbleCoachShape)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = if (esInvitado) "¡Caww! Inicia sesión para tener acceso a todo el contenido." else "¡Caww! Hoy vas excelente con los números.",
                                    fontSize = 13.sp, color = colorBlanco, lineHeight = 18.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // RACHA SEMANAL
                    WeeklyStreakCard(esInvitado, rachaReal = usuarioReal?.racha ?: 0, onClick = { showStreakDialog = true })

                    Spacer(modifier = Modifier.height(24.dp))

                    // NIVEL DINÁMICO
                    StatusCardPremium(
                        label = "Nivel Actual",
                        value = if (esInvitado) "Nvl. 0" else "Nvl. ${usuarioReal?.nivel ?: 1}",
                        icon = Icons.Default.AutoAwesome,
                        colorMain = colorAzul,
                        modifier = Modifier.fillMaxWidth().clickable { showLevelDialog = true },
                        progreso = if (esInvitado) 0f else {
                            val leccionesEnEsteNivel = (usuarioReal?.leccionesTotales ?: 0) % 5
                            leccionesEnEsteNivel / 5f
                        },
                        sublabel = if (esInvitado) "" else "Faltan ${5 - ((usuarioReal?.leccionesTotales ?: 0) % 5)} lecciones para el sig. nivel"
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Text("Lecciones", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = colorBlanco)
                    Spacer(modifier = Modifier.height(16.dp))

                    ModernLessonCard("Colores", "Aprende los colores", Icons.Default.Palette, Brush.horizontalGradient(listOf(colorAzul, colorAzul.copy(alpha = 0.6f))), onColores)
                    Spacer(modifier = Modifier.height(12.dp))
                    ModernLessonCard("Números", "Cuenta del 1 al 100", Icons.Default.Filter1, Brush.horizontalGradient(listOf(colorNaranja, colorNaranja.copy(alpha = 0.6f))), onNumeros)

                    Spacer(modifier = Modifier.height(12.dp))

                    // CUESTIONARIO
                    Surface(
                        modifier = Modifier.fillMaxWidth().height(70.dp).clickable { 
                            if (esInvitado) {
                                scope.launch {
                                    snackbarHostState.showSnackbar("El modo invitado no permite cuestionarios.")
                                }
                            } else onCuestionario() 
                        },
                        color = if (esInvitado) colorFondoCard else colorCian,
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Row(modifier = Modifier.padding(horizontal = 20.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Quiz, null, tint = if (esInvitado) colorGris else colorFondo)
                            Spacer(modifier = Modifier.width(16.dp))
                            Text("Cuestionario de Evaluación", color = if (esInvitado) colorBlanco else colorFondo, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.weight(1f))
                            if (esInvitado) Icon(Icons.Default.Lock, null, tint = colorGris)
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // TIP
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = colorFondoCard,
                        shape = RoundedCornerShape(20.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, colorCian.copy(alpha = 0.2f))
                    ) {
                        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Lightbulb, null, tint = colorCian)
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text("Tip del día", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = colorCian)
                                AnimatedContent(
                                    targetState = tips[currentTipIndex],
                                    transitionSpec = {
                                        fadeIn(tween(800)) togetherWith fadeOut(tween(800))
                                    },
                                    label = "tip_animation"
                                ) { tipText ->
                                    Text(tipText, fontSize = 13.sp, color = colorBlanco, fontStyle = FontStyle.Italic)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(120.dp))
                }
            }
        }
    }
}

@Composable
fun WeeklyStreakCard(esInvitado: Boolean, rachaReal: Int = 0, onClick: () -> Unit = {}) {
    val diasLabels = listOf("L", "M", "M", "J", "V", "S", "D")
    val colorCian = Color(0xFF00F5D4)
    val colorNaranja = Color(0xFFE57A0D)
    
    val calendar = Calendar.getInstance()
    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    val hoyIndex = if (dayOfWeek == Calendar.SUNDAY) 6 else dayOfWeek - 2

    Surface(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        color = Color(0xFF1E1E1E),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocalFireDepartment, null, tint = colorNaranja, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Racha Semanal", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.weight(1f))
                if (rachaReal >= 7) {
                    Icon(Icons.Default.Stars, null, tint = colorCian, modifier = Modifier.size(18.dp))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                diasLabels.forEachIndexed { index, dia ->
                    val esHoy = index == hoyIndex
                    val esPasado = index < hoyIndex
                    val enRacha = index <= hoyIndex && (hoyIndex - index) < rachaReal
                    
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .background(
                                    when {
                                        enRacha -> colorCian
                                        esPasado -> Color(0xFF332222)
                                        else -> Color(0xFF333333)
                                    }, 
                                    CircleShape
                                )
                                .shadow(if (enRacha) 8.dp else 0.dp, CircleShape, spotColor = colorCian),
                            contentAlignment = Alignment.Center
                        ) {
                            when {
                                enRacha -> Icon(Icons.Default.Check, null, tint = Color(0xFF121212), modifier = Modifier.size(18.dp))
                                esPasado -> Icon(Icons.Default.Lock, null, tint = Color(0xFF664444), modifier = Modifier.size(14.dp))
                                esHoy && !enRacha -> Icon(Icons.Default.PriorityHigh, null, tint = colorNaranja, modifier = Modifier.size(16.dp))
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            dia, 
                            color = when {
                                enRacha -> colorCian
                                esHoy -> colorNaranja
                                else -> Color.Gray
                            }, 
                            fontSize = 11.sp, 
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatusCardPremium(
    label: String, 
    value: String, 
    icon: ImageVector, 
    colorMain: Color, 
    modifier: Modifier,
    progreso: Float = 0f,
    sublabel: String = ""
) {
    Surface(
        modifier = modifier,
        color = Color(0xFF1E1E1E),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(50.dp).background(colorMain.copy(alpha = 0.1f), CircleShape), contentAlignment = Alignment.Center) {
                    Icon(icon, null, tint = colorMain, modifier = Modifier.size(26.dp))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(label, color = Color(0xFFAAAAAA), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Text(value, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
                }
            }
            
            if (progreso > 0f || sublabel.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                LinearProgressIndicator(
                    progress = { progreso },
                    modifier = Modifier.fillMaxWidth().height(6.dp).clip(CircleShape),
                    color = colorMain,
                    trackColor = colorMain.copy(alpha = 0.1f)
                )
                if (sublabel.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(sublabel, color = Color.Gray, fontSize = 11.sp)
                }
            }
        }
    }
}

@Composable
fun ModernLessonCard(title: String, subtitle: String, icon: ImageVector, gradient: Brush, onClick: () -> Unit) {
    Surface(modifier = Modifier.fillMaxWidth().height(90.dp).clickable { onClick() }, shape = RoundedCornerShape(20.dp), color = Color.Transparent) {
        Box(modifier = Modifier.background(gradient)) {
            Row(modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp), verticalAlignment = Alignment.CenterVertically) {
                Surface(modifier = Modifier.size(50.dp), color = Color.White.copy(alpha = 0.2f), shape = RoundedCornerShape(12.dp)) {
                    Box(contentAlignment = Alignment.Center) { Icon(icon, null, tint = Color.White, modifier = Modifier.size(28.dp)) }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(title, color = Color.White, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                    Text(subtitle, color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
                }
                Icon(Icons.Default.ChevronRight, null, tint = Color.White.copy(alpha = 0.5f))
            }
        }
    }
}
