package com.example.lorolingo.data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    indices = [Index(value = ["gmail"], unique = true)]
)
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val gmail: String,
    val password: String,
    val nivel: Int = 0,
    val racha: Int = 1,
    val leccionesTotales: Int = 0,
    val respuestasCorrectas: Int = 0,
    val preguntasTotales: Int = 0,
    val ultimaConexion: Long = System.currentTimeMillis(),
    val puntosRacha: Int = 0,
    val genero: String = "Otro", // "Hombre", "Mujer", "Otro"
    val avatarId: String = "img_profile",
    val fechaRegistro: Long = System.currentTimeMillis(),
    // Progreso por categorías (0 a 100)
    val progresoColores: Int = 0,
    val progresoNumeros: Int = 0,
    val progresoVocales: Int = 0,
    // Gamificación: Sesiones y Examen
    val xpActual: Int = 0, // 0 a 100
    val sesionesTotales: Int = 0,
    val examenDisponible: Boolean = false,
    val sesionBloqueada: Boolean = false,
    val sessionInicioTimestamp: Long = 0L
)
