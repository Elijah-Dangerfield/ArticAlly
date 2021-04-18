package com.dangerfield.artically.data.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dangerfield.artically.data.cache.model.TopHeadlineCacheEntity

@Database(
    entities = [TopHeadlineCacheEntity::class], version = 2, exportSchema = false
)
abstract class ArticallyDatabase : RoomDatabase() {

    abstract fun topHeadlinesDao(): TopHeadlinesDao

    companion object {
        @Volatile
        private var instance: ArticallyDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            ArticallyDatabase::class.java, "artically.db"
        )
            .build()
    }
}