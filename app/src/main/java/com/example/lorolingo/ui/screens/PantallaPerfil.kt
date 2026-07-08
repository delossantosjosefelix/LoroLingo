package com.example.lorolingo.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lorolingo.R
import com.example.lorolingo.data.local.AppDatabase
import com.example.lorolingo.data.local.entities.User
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PantallaPerfil(
    nombreUsuario: String,
    esInvitado: Boolean,
    usuarioReal: User? = null,
    onLogout: () -> Unit,
    onIrALogin: () -> Unit
) {
    val colorFondo = Color(0xFF121212)
    val colorAzulHero = Color(0xFF1A237E) 
    val colorCian = Color(0xFF00F5D4)
    val colorFondoCard = Color(0xFF1E1E1E)
    val colorBlanco = Color(0xFFFFFFFF)
    val colorGris = Color(0xFFAAAAAA)
    val colorVerde = Color(0xFF4CAF50)

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    if (esInvitado) {
        Box(
            modifier = Modifier.fillMaxSize().background(colorFondo),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(32.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_loro),
                    contentDescription = null,
                    modifier = Modifier.size(150.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "¡Oh, vaya!",
                    color = colorBlanco,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Para tener tu propio perfil y guardar tus logros, necesitas una cuenta.",
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                Button(
                    onClick = onIrALogin,
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorCian)
                ) {
                    Text("INICIAR SESIÓN", color = colorFondo, fontWeight = FontWeight.Bold)
                }
            }
        }
    } else {
        val scrollState = rememberScrollState()
        var showPasswordDialog by remember { mutableStateOf(false) }
        var showStreakDialog by remember { mutableStateOf(false) }
        var showDeleteDialog by remember { mutableStateOf(false) }

        // ESTADO PARA DIÁLOGOS DE LOGROS
        var selectedAchievement by remember { mutableStateOf<Triple<String, String, Color>?>(null) }

        if (selectedAchievement != null) {
            AlertDialog(
                onDismissRequest = { selectedAchievement = null },
                confirmButton = {
                    Button(
                        onClick = { selectedAchievement = null },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(25.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorCian)
                    ) {
                        Text(text = "¡ENTENDIDO!", color = colorFondo, fontWeight = FontWeight.ExtraBold)
                    }
                },
                icon = {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(selectedAchievement!!.third.copy(alpha = 0.1f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = when(selectedAchievement!!.first) {
                                "Novato" -> Icons.Default.EmojiEvents
                                "Constante" -> Icons.Default.LocalFireDepartment
                                else -> Icons.Default.MilitaryTech
                            },
                            contentDescription = null,
                            tint = selectedAchievement!!.third,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                title = { 
                    Text(
                        text = selectedAchievement!!.first,
                        color = colorBlanco,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                text = {
                    Text(
                        text = selectedAchievement!!.second,
                        color = colorGris,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                containerColor = colorFondoCard,
                shape = RoundedCornerShape(28.dp)
            )
        }

        // DIÁLOGO DE CAMBIO DE CONTRASEÑA PREMIUM (ESTILO RECUPERAR CLAVE)
        if (showPasswordDialog) {
            var newPassword by remember { mutableStateOf("") }
            var updatedSuccessfully by remember { mutableStateOf(false) }
            
            AlertDialog(
                onDismissRequest = { 
                    showPasswordDialog = false 
                    updatedSuccessfully = false
                    newPassword = ""
                },
                confirmButton = {
                    if (!updatedSuccessfully) {
                        Button(
                            onClick = {
                                if (newPassword.length >= 8 && usuarioReal != null) {
                                    scope.launch {
                                        val db = AppDatabase.getDatabase(context)
                                        db.userDao().actualizarPassword(usuarioReal.id, newPassword)
                                        updatedSuccessfully = true
                                    }
                                }
                            },
                            enabled = newPassword.length >= 8,
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            shape = RoundedCornerShape(25.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorCian,
                                disabledContainerColor = colorCian.copy(alpha = 0.3f)
                            )
                        ) {
                            Text("ACTUALIZAR CLAVE", color = colorFondo, fontWeight = FontWeight.ExtraBold)
                        }
                    } else {
                        Button(
                            onClick = { showPasswordDialog = false },
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            shape = RoundedCornerShape(25.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = colorCian)
                        ) {
                            Text("ENTENDIDO", color = colorFondo, fontWeight = FontWeight.Bold)
                        }
                    }
                },
                icon = {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(colorCian.copy(alpha = 0.1f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (!updatedSuccessfully) Icons.Default.LockReset else Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = colorCian,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                title = { 
                    Text(
                        text = if (!updatedSuccessfully) "Nueva Seguridad" else "¡Actualizado!",
                        color = colorBlanco,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                text = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (!updatedSuccessfully) {
                            Text(
                                "Ingresa tu nueva contraseña. Asegúrate de que sea robusta.",
                                color = colorGris,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            OutlinedTextField(
                                value = newPassword,
                                onValueChange = { newPassword = it },
                                placeholder = { Text("Mínimo 8 caracteres", color = colorGris) },
                                leadingIcon = { Icon(Icons.Default.Lock, null, tint = colorCian) },
                                visualTransformation = PasswordVisualTransformation(),
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = colorBlanco,
                                    unfocusedTextColor = colorBlanco,
                                    focusedBorderColor = colorCian,
                                    unfocusedBorderColor = colorGris.copy(alpha = 0.5f)
                                )
                            )
                        } else {
                            Text(
                                "Tu contraseña ha sido cambiada con éxito. ¡Tu cuenta está más segura!",
                                color = colorGris,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                },
                containerColor = colorFondoCard,
                shape = RoundedCornerShape(28.dp)
            )
        }

        if (showStreakDialog) {
            AlertDialog(
                onDismissRequest = { showStreakDialog = false },
                confirmButton = {
                    Button(
                        onClick = { showStreakDialog = false },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(25.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorCian)
                    ) {
                        Text(text = "¡ENTENDIDO!", color = colorFondo, fontWeight = FontWeight.ExtraBold)
                    }
                },
                title = { Text(text = "Puntos de Racha", color = colorBlanco, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) },
                text = {
                    Text(
                        text = "Los Puntos de Racha se obtienen al practicar diariamente. ¡Sigue así para subir de nivel!",
                        color = colorBlanco.copy(alpha = 0.7f),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                },
                containerColor = colorFondoCard,
                shape = RoundedCornerShape(28.dp)
            )
        }

        // DIÁLOGO DE ELIMINAR CUENTA (PREMIUM STYLE)
        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                confirmButton = {
                    Button(
                        onClick = {
                            if (usuarioReal != null) {
                                scope.launch {
                                    val db = AppDatabase.getDatabase(context)
                                    db.userDao().eliminarUsuario(usuarioReal.id)
                                    showDeleteDialog = false
                                    onLogout() // Redirigir al login
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(25.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935))
                    ) {
                        Text("ELIMINAR MI CUENTA", color = Color.White, fontWeight = FontWeight.ExtraBold)
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showDeleteDialog = false },
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                    ) {
                        Text("CANCELAR", color = colorGris, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                    }
                },
                icon = {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(Color(0xFFE53935).copy(alpha = 0.1f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.DeleteForever,
                            contentDescription = null,
                            tint = Color(0xFFE53935),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                title = { 
                    Text(
                        text = "¿Estás seguro?",
                        color = colorBlanco,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                text = {
                    Text(
                        text = "Esta acción es permanente. Perderás todo tu progreso, niveles y logros acumulados.",
                        color = colorGris,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                containerColor = colorFondoCard,
                shape = RoundedCornerShape(28.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorFondo)
                .verticalScroll(scrollState)
        ) {
            // --- CABECERA HERO ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(bottomStart = 45.dp, bottomEnd = 45.dp))
                    .background(Brush.verticalGradient(listOf(colorAzulHero, colorAzulHero.copy(alpha = 0.8f))))
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawCircle(color = colorBlanco.copy(alpha = 0.05f), radius = 200f, center = Offset(size.width * 0.1f, size.height * 0.2f))
                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    val avatarRes = if (usuarioReal != null) {
                        val id = context.resources.getIdentifier(usuarioReal.avatarId, "drawable", context.packageName)
                        if (id != 0) id else R.drawable.img_profile
                    } else {
                        R.drawable.img_profile
                    }

                    Surface(
                        modifier = Modifier.size(110.dp).shadow(12.dp, CircleShape),
                        shape = CircleShape,
                        color = colorBlanco.copy(alpha = 0.1f),
                        border = androidx.compose.foundation.BorderStroke(2.dp, colorCian.copy(alpha = 0.5f))
                    ) {
                        Image(
                            painter = painterResource(id = avatarRes),
                            contentDescription = "Foto de perfil",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = nombreUsuario, color = colorBlanco, fontSize = 26.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Maestro LoroLingo • Nvl. ${usuarioReal?.nivel ?: 0}", color = colorCian, fontSize = 14.sp)

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                        val precision = if ((usuarioReal?.preguntasTotales ?: 0) > 0) (usuarioReal!!.respuestasCorrectas.toFloat() / usuarioReal.preguntasTotales * 100).toInt() else 0
                        StatItem(value = "${usuarioReal?.racha ?: 0}", label = "Racha")
                        StatItem(value = "${usuarioReal?.leccionesTotales ?: 0}", label = "Lecciones")
                        StatItem(value = "$precision%", label = "Precisión")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- SECCIÓN DE PROGRESO ---
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Text(text = "Mi Progreso", color = colorBlanco, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))
                Surface(color = colorFondoCard, shape = RoundedCornerShape(20.dp), modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        ProgressRow("Colores", usuarioReal?.progresoColores ?: 0, colorCian)
                        Spacer(modifier = Modifier.height(12.dp))
                        ProgressRow("Números", usuarioReal?.progresoNumeros ?: 0, Color(0xFFE57A0D))
                        Spacer(modifier = Modifier.height(12.dp))
                        ProgressRow("Vocales", usuarioReal?.progresoVocales ?: 0, Color(0xFF8E24AA))
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // --- SECCIÓN DE LOGROS ---
                Text(text = "Logros", color = colorBlanco, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    AchievementBadge(
                        icon = Icons.Default.EmojiEvents,
                        label = "Novato",
                        unlocked = (usuarioReal?.leccionesTotales ?: 0) >= 1,
                        color = Color(0xFFCD7F32),
                        onClick = {
                            selectedAchievement = Triple(
                                "Novato",
                                "¡Bienvenido a LoroLingo! Este logro se obtiene al completar tu primera lección.",
                                Color(0xFFCD7F32)
                            )
                        }
                    )
                    AchievementBadge(
                        icon = Icons.Default.LocalFireDepartment,
                        label = "Constante",
                        unlocked = (usuarioReal?.racha ?: 0) >= 7,
                        color = Color(0xFFE57A0D),
                        onClick = {
                            selectedAchievement = Triple(
                                "Constante",
                                "¡No te detengas! Este logro se obtiene al alcanzar una racha de 7 días practicando.",
                                Color(0xFFE57A0D)
                            )
                        }
                    )
                    AchievementBadge(
                        icon = Icons.Default.MilitaryTech,
                        label = "Experto",
                        unlocked = (usuarioReal?.nivel ?: 0) >= 5,
                        color = Color(0xFFFFD700),
                        onClick = {
                            selectedAchievement = Triple(
                                "Experto",
                                "¡Todo un maestro! Este logro se obtiene al alcanzar el Nivel 5 de aprendizaje.",
                                Color(0xFFFFD700)
                            )
                        }
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // --- AJUSTES DE CUENTA ---
                Text(text = "Cuenta", color = colorBlanco, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))
                Surface(color = colorFondoCard, shape = RoundedCornerShape(20.dp), modifier = Modifier.fillMaxWidth()) {
                    Column {
                        ProfileOptionItem(icon = Icons.Default.Email, title = "Gmail", subtitle = usuarioReal?.gmail ?: "", colorCian = colorCian)
                        HorizontalDivider(color = colorFondo.copy(alpha = 0.3f))
                        ProfileOptionItem(icon = Icons.Default.Lock, title = "Cambiar Contraseña", subtitle = "Actualiza tu seguridad", colorCian = colorCian, onClick = { showPasswordDialog = true })
                        HorizontalDivider(color = colorFondo.copy(alpha = 0.3f))
                        ProfileOptionItem(
                            icon = Icons.Default.DeleteForever, 
                            title = "Eliminar Cuenta", 
                            subtitle = "Borrar mis datos permanentemente", 
                            colorCian = Color(0xFFE53935),
                            onClick = { showDeleteDialog = true }
                        )
                        HorizontalDivider(color = colorFondo.copy(alpha = 0.3f))
                        val fechaStr = usuarioReal?.let { SimpleDateFormat("MMM yyyy", Locale("es")).format(Date(it.fechaRegistro)) } ?: ""
                        ProfileOptionItem(icon = Icons.Default.CalendarToday, title = "Miembro desde", subtitle = fechaStr, colorCian = Color.Gray)
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = onLogout,
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorFondoCard),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE53935).copy(alpha = 0.5f))
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.AutoMirrored.Filled.Logout, null, tint = Color(0xFFE53935))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Cerrar Sesión", color = Color(0xFFE53935), fontWeight = FontWeight.Bold)
                    }
                }
                
                Spacer(modifier = Modifier.height(120.dp))
            }
        }
    }
}

@Composable
fun ProgressRow(label: String, progress: Int, color: Color) {
    val esDominado = progress >= 100
    val colorBarra = if (esDominado) Color(0xFFFFD700) else color // Dorado si es 100%
    
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(), 
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(label, color = Color.White, fontSize = 13.sp)
                if (esDominado) {
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.Default.EmojiEvents, 
                        contentDescription = null, 
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
            Text(
                text = if (esDominado) "¡DOMINADO!" else "$progress%", 
                color = colorBarra, 
                fontSize = 13.sp, 
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        LinearProgressIndicator(
            progress = { progress / 100f },
            modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
            color = colorBarra,
            trackColor = colorBarra.copy(alpha = 0.1f)
        )
    }
}

@Composable
fun StatItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 12.sp
        )
    }
}

@Composable
fun ProfileOptionItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    colorCian: Color,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(40.dp).background(colorCian.copy(alpha = 0.1f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = colorCian, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Text(subtitle, color = Color.Gray, fontSize = 12.sp)
        }
    }
}

@Composable
fun AchievementBadge(icon: ImageVector, label: String, unlocked: Boolean, color: Color, onClick: () -> Unit) {
    Column(
        modifier = Modifier.width(80.dp).clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(if (unlocked) color.copy(alpha = 0.15f) else Color(0xFF2A2A2A), CircleShape)
                .border(2.dp, if (unlocked) color else Color.Transparent, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (unlocked) color else Color.Gray,
                modifier = Modifier.size(30.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = label, color = if (unlocked) Color.White else Color.Gray, fontSize = 11.sp, textAlign = TextAlign.Center)
    }
}
