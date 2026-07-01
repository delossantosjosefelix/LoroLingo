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
    val nivel: Int = 1,
    val racha: Int = 1,
    val leccionesTotales: Int = 0,
    val respuestasCorrectas: Int = 0,
    val preguntasTotales: Int = 0,
    val ultimaConexion: Long = System.currentTimeMillis(),
    val puntosRacha: Int = 0 // Puntos acumulados por completar semanas
)
