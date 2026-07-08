package com.example.lorolingo.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.example.lorolingo.R
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext

@Composable
fun PantallaRegistro(
    onRegistroSuccess: (User) -> Unit,
    onIrALogin: () -> Unit
) {
    val colorFondo = Color(0xFF121212)
    val colorDegradado = Color(0xFF1A1A2E)
    val colorFondoCard = Color(0xFF1E1E1E)
    val colorCian = Color(0xFF00F5D4)
    val colorBlanco = Color(0xFFFFFFFF)
    val colorGris = Color(0xFFAAAAAA)
    val colorVerde = Color(0xFF4CAF50)

    var nombreUsuario by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var repeatPasswordVisible by remember { mutableStateOf(false) }
    var genero by remember { mutableStateOf("Hombre") }

    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // --- LÓGICA DE VALIDACIÓN ---
    
    // 1. Nombre: Sin caracteres especiales indebidos (solo letras, números y espacios/guiones)
    val regexNombre = Regex("^[a-zA-Z0-0áéíóúÁÉÍÓÚñÑ ]+$")
    val nombreValido = nombreUsuario.isNotEmpty() && regexNombre.matches(nombreUsuario)
    
    // 2. Email: Formato de correo válido (con límite de 4 caracteres en el dominio .com, .net, etc.)
    val regexEmail = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\$")
    val emailValido = email.isNotEmpty() && regexEmail.matches(email)

    // 3. Password: Requerimientos de seguridad
    val tieneLongitud = password.length >= 8
    val tieneMayuscula = password.any { it.isUpperCase() }
    val tieneNumero = password.any { it.isDigit() }
    val passwordRobusta = tieneLongitud && tieneMayuscula && tieneNumero
    
    // 4. Repetir Password
    val contrasenasCoinciden = passwordRobusta && password == repeatPassword && repeatPassword.isNotEmpty()

    // 5. Todo Listo
    val puedeRegistrar = nombreValido && emailValido && passwordRobusta && contrasenasCoinciden

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.Transparent
    ) { padding ->
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
            // Círculos Decorativos sutiles
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
                    .imePadding()
                    .verticalScroll(scrollState)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Espaciador para mantener posición original sin icono superior
            Spacer(modifier = Modifier.height(170.dp))

            Text(
                text = "Crear Cuenta",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = colorBlanco,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
                Text(
                    text = "Únete a la comunidad de LoroLingo.",
                    fontSize = 14.sp,
                    color = colorGris,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = nombreUsuario,
                    onValueChange = { 
                        if (it.length <= 20) nombreUsuario = it 
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Nombre de Usuario", color = colorGris) },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = colorCian) },
                    shape = RoundedCornerShape(28.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = colorFondoCard,
                        unfocusedContainerColor = colorFondoCard,
                        focusedBorderColor = if (nombreUsuario.isEmpty()) colorCian else if (nombreValido) colorVerde else Color.Red,
                        unfocusedBorderColor = Color.Transparent,
                        focusedTextColor = colorBlanco,
                        unfocusedTextColor = colorBlanco
                    ),
                    singleLine = true,
                    isError = nombreUsuario.isNotEmpty() && !nombreValido
                )
                
                if (nombreUsuario.isNotEmpty() && !nombreValido) {
                    Text(
                        "Solo letras y números, sin símbolos especiales.",
                        color = Color.Red,
                        fontSize = 11.sp,
                        modifier = Modifier.fillMaxWidth().padding(start = 12.dp, top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Gmail (ejemplo@gmail.com)", color = colorGris) },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = colorCian) },
                    shape = RoundedCornerShape(28.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = colorFondoCard,
                        unfocusedContainerColor = colorFondoCard,
                        focusedBorderColor = if (email.isEmpty()) colorCian else if (emailValido) colorVerde else Color.Red,
                        unfocusedBorderColor = Color.Transparent,
                        focusedTextColor = colorBlanco,
                        unfocusedTextColor = colorBlanco
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true,
                    isError = email.isNotEmpty() && !emailValido
                )
                
                if (email.isNotEmpty() && !emailValido) {
                    Text(
                        "Formato de correo inválido.",
                        color = Color.Red,
                        fontSize = 11.sp,
                        modifier = Modifier.fillMaxWidth().padding(start = 12.dp, top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // SELECCIÓN DE GÉNERO MEJORADA
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        GeneroCard(
                            label = "Hombre",
                            isSelected = genero == "Hombre",
                            onClick = { genero = "Hombre" },
                            icon = Icons.Default.Male,
                            colorCian = colorCian,
                            avatarRes = R.drawable.img_profile // Placeholder
                        )
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        GeneroCard(
                            label = "Mujer",
                            isSelected = genero == "Mujer",
                            onClick = { genero = "Mujer" },
                            icon = Icons.Default.Female,
                            colorCian = colorCian,
                            avatarRes = R.drawable.img_profile // Placeholder
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Contraseña", color = colorGris) },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = colorCian) },
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
                        focusedBorderColor = colorCian,
                        unfocusedBorderColor = Color.Transparent,
                        focusedTextColor = colorBlanco,
                        unfocusedTextColor = colorBlanco
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Requerimientos Animados
                Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp)) {
                    ValidacionItemAnimada("Mínimo 8 caracteres", tieneLongitud, colorVerde, colorGris)
                    ValidacionItemAnimada("Al menos una mayúscula", tieneMayuscula, colorVerde, colorGris)
                    ValidacionItemAnimada("Al menos un número", tieneNumero, colorVerde, colorGris)
                }

                // Campo Repetir Contraseña Animado
                AnimatedVisibility(
                    visible = passwordRobusta,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut()
                ) {
                    Column {
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = repeatPassword,
                            onValueChange = { repeatPassword = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Repetir Contraseña", color = colorGris) },
                            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = colorCian) },
                            trailingIcon = {
                                val image = if (repeatPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                                IconButton(onClick = { repeatPasswordVisible = !repeatPasswordVisible }) {
                                    Icon(imageVector = image, contentDescription = null, tint = colorGris)
                                }
                            },
                            visualTransformation = if (repeatPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            shape = RoundedCornerShape(28.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = colorFondoCard,
                                unfocusedContainerColor = colorFondoCard,
                                focusedBorderColor = if (contrasenasCoinciden) colorVerde else colorCian,
                                unfocusedBorderColor = Color.Transparent,
                                focusedTextColor = colorBlanco,
                                unfocusedTextColor = colorBlanco
                            ),
                            singleLine = true
                        )
                        
                        if (repeatPassword.isNotEmpty() && !contrasenasCoinciden) {
                            Text(
                                text = "Las contraseñas no coinciden",
                                color = Color.Red,
                                fontSize = 11.sp,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp, top = 4.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        scope.launch {
                            try {
                                val db = AppDatabase.getDatabase(context)
                                val randomId = (1..4).random()
                                val avatarName = if (genero == "Hombre") "male$randomId" else "female$randomId"
                                
                                val nuevoUsuario = User(
                                    nombre = nombreUsuario, 
                                    gmail = email, 
                                    password = password,
                                    genero = genero,
                                    avatarId = avatarName
                                )
                                db.userDao().registrarUsuario(nuevoUsuario)
                                // Recuperamos el usuario con su ID autogenerado
                                val userGuardado = db.userDao().getUserByGmail(email)
                                if (userGuardado != null) {
                                    onRegistroSuccess(userGuardado)
                                }
                            } catch (e: Exception) {
                                snackbarHostState.showSnackbar("Error: El correo ya está registrado o hay un problema.")
                            }
                        }
                    },
                    enabled = puedeRegistrar,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorCian,
                        disabledContainerColor = colorCian.copy(alpha = 0.3f)
                    )
                ) {
                    val textoBoton = if (puedeRegistrar) "REGISTRARSE" else "COMPLETA LOS DATOS"
                    Text(
                        text = textoBoton,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (puedeRegistrar) Color(0xFF121212) else colorGris
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("¿Ya tienes cuenta? ", color = colorGris, fontSize = 14.sp)
                    Text(
                        text = "Inicia Sesión",
                        color = colorCian,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { onIrALogin() }
                    )
                }
                
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun GeneroCard(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    icon: ImageVector,
    colorCian: Color,
    avatarRes: Int
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) colorCian.copy(alpha = 0.1f) else Color(0xFF1E1E1E),
        border = androidx.compose.foundation.BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) colorCian else Color(0xFF333333)
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(if (isSelected) colorCian else Color(0xFF333333), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isSelected) Color(0xFF121212) else Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                color = if (isSelected) colorCian else Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ValidacionItemAnimada(texto: String, cumplido: Boolean, colorCumplido: Color, colorGris: Color) {
    val scale by animateFloatAsState(
        targetValue = if (cumplido) 1.2f else 1.0f,
        animationSpec = spring(dampingRatio = 0.5f, stiffness = 400f),
        label = "scale"
    )
    
    val colorAnim by animateColorAsState(
        targetValue = if (cumplido) colorCumplido else colorGris,
        label = "color"
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(
            imageVector = if (cumplido) Icons.Default.CheckCircle else Icons.Default.Circle,
            contentDescription = null,
            modifier = Modifier
                .size(16.dp)
                .scale(if (cumplido) scale else 1.0f),
            tint = colorAnim
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = texto,
            fontSize = 13.sp,
            color = colorAnim,
            fontWeight = if (cumplido) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegistro() {
    LoroLingoTheme {
        PantallaRegistro({}, {})
    }
}
