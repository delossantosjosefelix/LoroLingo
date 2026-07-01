package com.example.lorolingo.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lorolingo.R
import com.example.lorolingo.data.local.entities.User

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

    if (esInvitado) {
        // --- VISTA PARA INVITADOS (BLOQUEADA) ---
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
        // --- VISTA PARA USUARIOS REALES ---
        val scrollState = rememberScrollState()
        var showStreakDialog by remember { mutableStateOf(false) }

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
                        Text(
                            text = "¡ENTENDIDO!", 
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
                            .background(Color(0xFFFDD835).copy(alpha = 0.15f), CircleShape)
                            .shadow(10.dp, CircleShape, spotColor = Color(0xFFFDD835)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Stars, 
                            contentDescription = null, 
                            tint = Color(0xFFFDD835), 
                            modifier = Modifier.size(38.dp)
                        )
                    }
                },
                title = { 
                    Text(
                        text = "Puntos de Racha", 
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
                            text = "Los Puntos de Racha se obtienen al practicar diariamente sin faltar. " +
                                   "¡Cada día consecutivo aumenta tu multiplicador y te ayuda a subir de nivel más rápido!",
                            color = colorBlanco.copy(alpha = 0.7f),
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorFondo)
                .verticalScroll(scrollState)
        ) {
            // --- CABECERA HERO CON BURBUJAS (ESTILO LOGIN) ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
                    .clip(RoundedCornerShape(bottomStart = 45.dp, bottomEnd = 45.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(colorAzulHero, colorAzulHero.copy(alpha = 0.8f))
                        )
                    )
            ) {
                // Burbujas Decorativas sutiles
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawCircle(
                        color = colorBlanco.copy(alpha = 0.05f),
                        radius = 200f,
                        center = Offset(size.width * 0.1f, size.height * 0.2f)
                    )
                    drawCircle(
                        color = colorCian.copy(alpha = 0.03f),
                        radius = 350f,
                        center = Offset(size.width * 0.9f, size.height * 0.8f)
                    )
                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Avatar con sombra y borde
                    Surface(
                        modifier = Modifier
                            .size(110.dp)
                            .shadow(12.dp, CircleShape),
                        shape = CircleShape,
                        color = colorBlanco.copy(alpha = 0.1f),
                        border = androidx.compose.foundation.BorderStroke(2.dp, colorCian.copy(alpha = 0.5f))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_profile),
                            contentDescription = "Foto de perfil",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = nombreUsuario,
                        color = colorBlanco,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Maestro LoroLingo • Nvl. ${usuarioReal?.nivel ?: 1}",
                        color = colorCian,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Stats Reales
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        val precision = if ((usuarioReal?.preguntasTotales ?: 0) > 0) {
                            (usuarioReal!!.respuestasCorrectas.toFloat() / usuarioReal.preguntasTotales * 100).toInt()
                        } else 0
                        
                        StatItem(value = "${usuarioReal?.racha ?: 0}", label = "Racha")
                        StatItem(value = "${usuarioReal?.leccionesTotales ?: 0}", label = "Lecciones")
                        StatItem(value = "$precision%", label = "Precisión")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Secciones
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Text(
                    text = "Cuenta",
                    color = colorBlanco,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Surface(
                    color = colorFondoCard,
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        ProfileOptionItem(
                            icon = Icons.Default.Email,
                            title = "Gmail",
                            subtitle = usuarioReal?.gmail ?: "usuario@gmail.com",
                            colorCian = colorCian
                        )
                        HorizontalDivider(color = colorFondo.copy(alpha = 0.5f), thickness = 1.dp)
                        ProfileOptionItem(
                            icon = Icons.Default.Stars,
                            title = "Puntos de Racha",
                            subtitle = "${usuarioReal?.puntosRacha ?: 0} puntos acumulados",
                            colorCian = Color(0xFFFDD835),
                            onClick = { showStreakDialog = true }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

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
