package fr.skyle.escapy.data.repository

import fr.skyle.escapy.data.db.dao.NewsDao
import fr.skyle.escapy.data.vo.News
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class NewsRepository(
    private val newsDao: NewsDao
) {

    // DB

    fun watchNews(): Flow<List<News>> =
        newsDao.watchNews()

    fun watchNewsWithId(id: String?): Flow<News?> =
        id?.let { newsDao.watchNewsWithId(it) } ?: flow { }

    // WS

//    suspend fun fetchNewsWithId(id: String) {
//        val news = oxApi.getNewsWithId(id)
//        newsDao.insertRestNews(news)
//    }
//
//    suspend fun fetchNews() {
//        val newsList = oxApi.getNews()
//        newsDao.insertRestNewsList(newsList)
//    }
}