package fr.skyle.escapy.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fr.skyle.escapy.data.db.dao.NewsDao
import fr.skyle.escapy.data.vo.News

@Database(
    entities = [
        News::class
    ],
    version = 1
)

abstract class EscapyDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

    companion object {
        fun databaseBuilder(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                EscapyDatabase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration()

        private const val DATABASE_NAME = "Escapy.db"
    }
}