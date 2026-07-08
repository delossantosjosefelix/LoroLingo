package com.example.lorolingo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.lorolingo.data.local.dao.UserDao
import com.example.lorolingo.data.local.entities.User

@Database(entities = [User::class], version = 7, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        val MIGRATION_6_7 = object : Migration(6, 7) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE users ADD COLUMN sesionBloqueada INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE users ADD COLUMN sessionInicioTimestamp INTEGER NOT NULL DEFAULT 0")
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "lorolingo_database"
                )
                    .addMigrations(MIGRATION_6_7)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
