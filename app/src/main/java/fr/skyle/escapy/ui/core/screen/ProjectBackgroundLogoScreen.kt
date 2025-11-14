package fr.skyle.escapy.ui.core.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.R
import fr.skyle.escapy.designsystem.theme.ProjectTheme

@Composable
fun ProjectBackgroundLogoScreen(
    content: @Composable () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.width(375.dp),
            painter = painterResource(id = R.drawable.background_image_2),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        content()
    }
}

@Preview
@Composable
private fun ProjectBackgroundLogoScreenPreview() {
    ProjectTheme {
        ProjectBackgroundLogoScreen(
            content = {}
        )
    }
}