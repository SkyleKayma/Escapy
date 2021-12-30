package fr.skyle.escapy.ui.home.states


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.R
import fr.skyle.escapy.ui.home.HomeScreenViewModel
import org.koin.androidx.compose.getViewModel


@Composable
fun HomeScreenError(
    model: HomeScreenViewModel = getViewModel(),
    errorEvent: HomeScreenViewModel.ErrorEvent
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(42.dp)
    ) {
        val errorTextId = getErrorData(errorEvent)

        Image(
            painter = painterResource(id = R.drawable.ic_face_annoyed),
            contentDescription = "",
            colorFilter = ColorFilter.tint(MaterialTheme.colors.error)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(id = errorTextId),
            style = MaterialTheme.typography.h4,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Button(onClick = { model.newsFlow }) {
            Text(text = stringResource(id = R.string.generic_retry))
        }
    }
}

/**
 * @return errorTextId to errorImageId
 */
private fun getErrorData(errorEvent: HomeScreenViewModel.ErrorEvent): Int =
    when (errorEvent) {
        HomeScreenViewModel.ErrorEvent.NetworkIssue ->
            R.string.error_no_connection
        HomeScreenViewModel.ErrorEvent.OtherIssue ->
            R.string.error_generic_issue
    }

@Preview("Network issue")
@Composable
private fun PreviewHomeScreenErrorNetworkIssue() {
    HomeScreenError(errorEvent = HomeScreenViewModel.ErrorEvent.NetworkIssue)
}

@Preview("Other issue")
@Composable
private fun PreviewHomeScreenErrorOtherIssue() {
    HomeScreenError(errorEvent = HomeScreenViewModel.ErrorEvent.OtherIssue)
}