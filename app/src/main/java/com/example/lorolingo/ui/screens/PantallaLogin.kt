package com.example.lorolingo.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lorolingo.data.local.AppDatabase
import com.example.lorolingo.data.local.entities.User
import com.example.lorolingo.ui.theme.LoroLingoTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PantallaLogin(
    onLoginSuccess: (User) -> Unit,
    onGuestLogin: () -> Unit,
    onIrARegistro: () -> Unit,
    onRecuperarPassword: () -> Unit
) {
    val colorFondo = Color(0xFF121212)
    val colorDegradado = Color(0xFF1A1A2E) 
    val colorFondoCard = Color(0xFF1E1E1E)
    val colorCian = Color(0xFF00F5D4)
    val colorBlanco = Color(0xFFFFFFFF)
    val colorGris = Color(0xFFAAAAAA)
    val colorRojo = Color(0xFFE53935)
    val colorVerde = Color(0xFF4CAF50)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var recordarme by remember { mutableStateOf(false) }
    
    // Estado para recuperar contraseña
    var showForgotDialog by remember { mutableStateOf(false) }
    var emailRecuperacion by remember { mutableStateOf("") }
    var passwordRecuperada by remember { mutableStateOf<String?>(null) }
    var errorRecuperacion by remember { mutableStateOf(false) }
    
    val emailRecuperacionValido = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\$").matches(emailRecuperacion)

    // Estado de error para el borde rojo
    var isError by remember { mutableStateOf(false) }
    
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                // NOTIFICACIÓN PERSONALIZADA PREMIUM
                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = colorFondoCard),
                    border = androidx.compose.foundation.BorderStroke(1.dp, if (isError) colorRojo else colorCian)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (isError) Icons.Default.Warning else Icons.Default.Lock,
                            contentDescription = null,
                            tint = if (isError) colorRojo else colorCian
                        )
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
    ) { padding ->
        // DIALOGO DE RECUPERAR CONTRASEÑA MEJORADO
        if (showForgotDialog) {
            AlertDialog(
                onDismissRequest = { 
                    showForgotDialog = false
                    passwordRecuperada = null
                    emailRecuperacion = ""
                    errorRecuperacion = false
                },
                confirmButton = {
                    if (passwordRecuperada == null) {
                        Button(
                            onClick = {
                                scope.launch {
                                    val db = AppDatabase.getDatabase(context)
                                    val user = db.userDao().getUserByGmail(emailRecuperacion)
                                    if (user != null) {
                                        passwordRecuperada = user.password
                                        errorRecuperacion = false
                                    } else {
                                        errorRecuperacion = true
                                    }
                                }
                            },
                            enabled = emailRecuperacionValido,
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            shape = RoundedCornerShape(25.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorCian,
                                disabledContainerColor = colorCian.copy(alpha = 0.3f)
                            )
                        ) {
                            Icon(Icons.Default.Search, null, tint = if (emailRecuperacionValido) colorFondo else colorGris, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("BUSCAR MI CLAVE", color = if (emailRecuperacionValido) colorFondo else colorGris, fontWeight = FontWeight.ExtraBold, fontSize = 14.sp)
                        }
                    } else {
                        Button(
                            onClick = { 
                                showForgotDialog = false
                                passwordRecuperada = null
                                emailRecuperacion = ""
                            },
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
                            imageVector = if (passwordRecuperada == null) Icons.Default.LockReset else Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = colorCian,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                title = { 
                    Text(
                        text = if (passwordRecuperada == null) "Recuperar Acceso" else "¡Clave Encontrada!",
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
                        if (passwordRecuperada == null) {
                            Text(
                                "Ingresa tu correo registrado para que podamos recordarte tu contraseña.",
                                color = colorGris,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            OutlinedTextField(
                                value = emailRecuperacion,
                                onValueChange = { 
                                    emailRecuperacion = it
                                    errorRecuperacion = false
                                },
                                placeholder = { Text("tu-correo@gmail.com", color = colorGris) },
                                leadingIcon = { Icon(Icons.Default.Email, null, tint = colorCian) },
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = colorFondo.copy(alpha = 0.3f),
                                    unfocusedContainerColor = colorFondo.copy(alpha = 0.3f),
                                    focusedTextColor = colorBlanco,
                                    unfocusedTextColor = colorBlanco,
                                    focusedBorderColor = if (emailRecuperacion.isEmpty()) colorCian else if (emailRecuperacionValido) colorVerde else Color.Red,
                                    unfocusedBorderColor = colorGris.copy(alpha = 0.5f)
                                ),
                                isError = emailRecuperacion.isNotEmpty() && !emailRecuperacionValido,
                                singleLine = true
                            )
                            if (emailRecuperacion.isNotEmpty() && !emailRecuperacionValido) {
                                Text("Formato de correo no válido.", color = colorRojo, fontSize = 11.sp, modifier = Modifier.padding(top = 4.dp))
                            }
                            if (errorRecuperacion) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(top = 8.dp)
                                ) {
                                    Icon(Icons.Default.Error, null, tint = colorRojo, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Ese correo no existe en LoroLingo.", color = colorRojo, fontSize = 12.sp)
                                }
                            }
                        } else {
                            Text(
                                "Hemos encontrado tu cuenta con éxito.",
                                color = colorGris,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            Surface(
                                color = colorCian.copy(alpha = 0.05f),
                                shape = RoundedCornerShape(16.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, colorCian.copy(alpha = 0.2f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text("TU CONTRASEÑA ES:", color = colorCian, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = passwordRecuperada!!,
                                        color = colorBlanco,
                                        fontSize = 28.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        letterSpacing = 2.sp
                                    )
                                }
                            }
                        }
                    }
                },
                containerColor = colorFondoCard,
                shape = RoundedCornerShape(28.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(colorDegradado, colorFondo),
                        radius = 2000f,
                        center = Offset(0.5f, 0.5f)
                    )
                )
                .padding(padding)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    color = colorCian.copy(alpha = 0.05f),
                    radius = 300.dp.toPx(),
                    center = Offset(size.width * 0.9f, size.height * 0.1f)
                )
                drawCircle(
                    color = Color.Blue.copy(alpha = 0.03f),
                    radius = 400.dp.toPx(),
                    center = Offset(size.width * 0.1f, size.height * 0.9f)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(190.dp))

                Text(
                    text = "Iniciar Sesión",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorBlanco,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "Por favor, inicia sesión para continuar.",
                    fontSize = 14.sp,
                    color = colorGris,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Campo Email con Borde Rojo si hay error
                OutlinedTextField(
                    value = email,
                    onValueChange = { 
                        email = it
                        isError = false // Reset error al escribir
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Correo Electrónico (Gmail)", color = colorGris) },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = if (isError) colorRojo else colorCian) },
                    shape = RoundedCornerShape(28.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = colorFondoCard,
                        unfocusedContainerColor = colorFondoCard,
                        focusedBorderColor = if (isError) colorRojo else colorCian,
                        unfocusedBorderColor = if (isError) colorRojo.copy(alpha = 0.5f) else Color.Transparent,
                        focusedTextColor = colorBlanco,
                        unfocusedTextColor = colorBlanco
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true,
                    isError = isError
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo Contraseña con Borde Rojo si hay error
                OutlinedTextField(
                    value = password,
                    onValueChange = { 
                        password = it
                        isError = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Contraseña", color = colorGris) },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = if (isError) colorRojo else colorCian) },
                    trailingIcon = {
                        val image = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, contentDescription = null, tint = colorGris)
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    shape = RoundedCornerShape(28.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = colorFondoCard,
                        unfocusedContainerColor = colorFondoCard,
                        focusedBorderColor = if (isError) colorRojo else colorCian,
                        unfocusedBorderColor = if (isError) colorRojo.copy(alpha = 0.5f) else Color.Transparent,
                        focusedTextColor = colorBlanco,
                        unfocusedTextColor = colorBlanco
                    ),
                    singleLine = true,
                    isError = isError
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Switch(
                            checked = recordarme,
                            onCheckedChange = { recordarme = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = colorCian,
                                checkedTrackColor = colorCian.copy(alpha = 0.5f)
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Recordarme", color = colorGris, fontSize = 12.sp)
                    }
                    
                    Text(
                        text = "¿Olvidaste tu contraseña?",
                        color = colorCian,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { showForgotDialog = true }
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        scope.launch {
                            val db = AppDatabase.getDatabase(context)
                            val user = db.userDao().login(email, password)
                            if (user != null) {
                                isError = false
                                onLoginSuccess(user)
                            } else {
                                isError = true
                                snackbarHostState.showSnackbar("Correo o contraseña incorrectos")
                                // Pequeña vibración visual (delay)
                                delay(2000)
                                isError = false 
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = if (isError) colorRojo else colorCian)
                ) {
                    Text(
                        text = "ENTRAR",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF121212)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton(
                    onClick = onGuestLogin,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, colorCian.copy(alpha = 0.5f)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = colorCian)
                ) {
                    Text(
                        text = "ENTRAR COMO INVITADO",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("¿No tienes cuenta? ", color = colorGris, fontSize = 14.sp)
                    Text(
                        text = "Regístrate",
                        color = colorCian,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { onIrARegistro() }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLogin() {
    LoroLingoTheme {
        PantallaLogin({}, {}, {}, {})
    }
}
