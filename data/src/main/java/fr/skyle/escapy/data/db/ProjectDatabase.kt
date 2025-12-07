package fr.skyle.escapy.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fr.skyle.escapy.data.db.converter.StringListConverter
import fr.skyle.escapy.data.db.dao.LobbyDao
import fr.skyle.escapy.data.db.dao.UserDao
import fr.skyle.escapy.data.vo.Lobby
import fr.skyle.escapy.data.vo.User
import fr.skyle.escapy.data.vo.junction.LobbyParticipantCrossRef

@Database(
    entities = [
        User::class,
        Lobby::class,
        LobbyParticipantCrossRef::class,
    ],
    version = 1
)
@TypeConverters(StringListConverter::class)
abstract class ProjectDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun lobbyDao(): LobbyDao

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