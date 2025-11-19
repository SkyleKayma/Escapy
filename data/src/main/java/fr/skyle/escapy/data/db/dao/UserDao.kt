package fr.skyle.escapy.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.skyle.escapy.data.vo.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    // Insert

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    // Read

    @Query("SELECT * FROM User LIMIT 1")
    suspend fun getUser(): User?

    @Query("SELECT * FROM User WHERE uid = :uid LIMIT 1")
    fun watchUser(uid: String): Flow<User?>

    // Delete

    @Query("DELETE FROM User")
    suspend fun deleteAll()
}