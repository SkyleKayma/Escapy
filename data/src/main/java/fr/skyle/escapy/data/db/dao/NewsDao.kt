package fr.skyle.escapy.data.db.dao

import androidx.room.*
import fr.skyle.escapy.data.rest.model.RestNews
import fr.skyle.escapy.data.vo.News
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    // Watch

    @Query("SELECT * FROM NEWS WHERE id == :id")
    fun watchNewsWithId(id: String): Flow<News?>

    @Query("SELECT * FROM NEWS")
    fun watchNews(): Flow<List<News>>

    // News Insertions

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: News)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: List<News>)

    // News Transactions

    @Transaction
    suspend fun insertRestNews(restNews: RestNews?) {
        restNews?.toNews()?.let { news ->
            insertNews(news)
        }
    }

    @Transaction
    suspend fun insertRestNewsList(restNewsList: List<RestNews>?) {
        restNewsList?.mapNotNull { it.toNews() }?.distinct()?.let { newsList ->
            // News
            insertNews(newsList)
        }
    }
}