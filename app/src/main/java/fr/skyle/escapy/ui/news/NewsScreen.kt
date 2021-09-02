package fr.skyle.escapy.ui.news

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import fr.skyle.escapy.data.vo.News
import fr.skyle.escapy.ui.news.states.NewsScreenError
import fr.skyle.escapy.ui.news.states.NewsScreenLoaded
import fr.skyle.escapy.ui.news.states.NewsScreenLoading
import org.koin.androidx.compose.getViewModel


@Composable
fun NewsScreen(
    model: NewsScreenViewModel = getViewModel()
) {
    val newsState by model.stateFlow.collectAsState()
    val newsList by model.newsFlow.collectAsState(initial = listOf())

    NewsScreenStateLoader(
        state = newsState,
        newsList = newsList
    )
}

@Composable
fun NewsScreenStateLoader(
    state: NewsScreenViewModel.State,
    newsList: List<News>
) {
    when (state) {
        NewsScreenViewModel.State.Loading ->
            NewsScreenLoading()
        NewsScreenViewModel.State.Loaded ->
            NewsScreenLoaded(newsList = newsList)
        is NewsScreenViewModel.State.Error ->
            NewsScreenError(errorEvent = state.errorEvent)
    }
}