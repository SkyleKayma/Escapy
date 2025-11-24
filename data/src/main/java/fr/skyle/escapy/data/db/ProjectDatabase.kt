package fr.skyle.escapy.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fr.skyle.escapy.data.db.dao.UserDao
import fr.skyle.escapy.data.vo.User

@Database(
    entities = [
        User::class,
    ],
    version = 1
)
abstract class ProjectDatabase : RoomDatabase() {

    abstract fun currentUserDao(): UserDao

    companion object {
        fun databaseBuilder(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ProjectDatabase::class.java,
                DATABASE_NAME
            )
                .fallbackToDestructiveMigration(dropAllTables = true) // TODO Do real migration when in production

        private const val DATABASE_NAME = "escapy.db"
    }
}