package fr.skyle.escapy.data.repository.user

import fr.skyle.escapy.data.db.dao.UserDao
import fr.skyle.escapy.data.repository.user.api.UserLocalDataSource
import fr.skyle.escapy.data.vo.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocalDataSourceImpl @Inject constructor(
    private val userDao: UserDao
) : UserLocalDataSource {

    override suspend fun insertUser(user: User) {
        userDao.insert(user)
    }

    override fun watchUser(userId: String): Flow<User?> =
        userDao.watchUser(userId).distinctUntilChanged()
}