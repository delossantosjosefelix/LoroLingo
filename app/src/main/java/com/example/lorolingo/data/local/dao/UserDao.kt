package com.example.lorolingo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.lorolingo.data.local.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun registrarUsuario(user: User)

    @Query("SELECT * FROM users WHERE gmail = :gmail AND password = :password")
    suspend fun login(gmail: String, password: String): User?

    @Query("SELECT password FROM users WHERE gmail = :gmail")
    suspend fun recuperarPassword(gmail: String): String?

    @Query("SELECT * FROM users WHERE gmail = :gmail")
    suspend fun getUserByGmail(gmail: String): User?

    @Query("SELECT * FROM users WHERE nombre = :nombre LIMIT 1")
    suspend fun getUserByName(nombre: String): User?

    @Update
    suspend fun actualizarUsuario(user: User)

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun obtenerUsuarioPorId(userId: Int): User?

    @Query("UPDATE users SET password = :newPassword WHERE id = :userId")
    suspend fun actualizarPassword(userId: Int, newPassword: String)

    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun eliminarUsuario(userId: Int)
}
