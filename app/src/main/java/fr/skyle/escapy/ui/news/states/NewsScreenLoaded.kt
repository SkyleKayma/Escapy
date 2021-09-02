package fr.skyle.escapy.ui.news.states

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.R
import fr.skyle.escapy.data.vo.News


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsScreenLoaded(
    newsList: List<News>
) {
    if (newsList.isNotEmpty()) {
        NewsContainer(
            newsList = newsList
        )
    } else NewsScreenEmptyView()
}

/**
 * Default mode is [ListComponent.ListMode.list]
 */
@Composable
private fun NewsContainer(
    newsList: List<News>
) {
//    NewsContainerListFirstHighlighted(
//        newsList = newsList,
//        content = listComponent.content
//    )
}

// TODO
@Composable
fun NewsScreenEmptyView() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(42.dp)
    ) {
        Text(
            text = stringResource(id = R.string.error_no_connection),
            style = MaterialTheme.typography.h4,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(28.dp))
    }
}

@Preview("Loaded Empty")
@Composable
private fun PreviewNewsScreenLoadedEmpty() {
    NewsScreenLoaded(
        newsList = listOf()
    )
}
//
//@Preview("Loaded")
//@Composable
//private fun PreviewNewsScreenLoaded() {
//    NewsScreenLoaded(
//        newsList = getMockListNewsWithDetails()
//    )
//}