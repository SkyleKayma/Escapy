package fr.skyle.escapy.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import fr.skyle.escapy.data.vo.News
import fr.skyle.escapy.ui.home.states.HomeScreenError
import fr.skyle.escapy.ui.home.states.HomeScreenLoaded
import fr.skyle.escapy.ui.home.states.HomeScreenLoading
import org.koin.androidx.compose.getViewModel


@Composable
fun NewsScreen(
    model: HomeScreenViewModel = getViewModel()
) {
    val newsState by model.stateFlow.collectAsState()
    val newsList by model.newsFlow.collectAsState(initial = listOf())

    HomeScreenStateLoader(
        state = newsState,
        newsList = newsList
    )
}

@Composable
fun HomeScreenStateLoader(
    state: HomeScreenViewModel.State,
    newsList: List<News>
) {
    when (state) {
        HomeScreenViewModel.State.Loading ->
            HomeScreenLoading()
        HomeScreenViewModel.State.Loaded ->
            HomeScreenLoaded(newsList = newsList)
        is HomeScreenViewModel.State.Error ->
            HomeScreenError(errorEvent = state.errorEvent)
    }
}