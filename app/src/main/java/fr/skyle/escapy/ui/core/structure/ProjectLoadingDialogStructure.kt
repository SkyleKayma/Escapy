package fr.skyle.escapy.ui.core.structure

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.utils.ProjectComponentPreview

@Composable
fun ProjectLoadingDialogStructure(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            style = ProjectTheme.typography.b2,
            color = ProjectTheme.colors.onSurface,
            textAlign = TextAlign.Center,
        )

        subtitle?.let {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = subtitle,
                style = ProjectTheme.typography.p2,
                color = ProjectTheme.colors.onSurface,
                textAlign = TextAlign.Center,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // TODO Add to DesignSystem
        CircularProgressIndicator(
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.CenterHorizontally),
            color = ProjectTheme.colors.primary,
            trackColor = ProjectTheme.colors.surfaceContainerHigh,
            strokeWidth = 3.dp
        )
    }
}

@Composable
@ProjectComponentPreview
private fun ProjectAlertDialogStructurePreview() {
    ProjectTheme {
        ProjectAlertDialogStructure(
            title = "Title",
            subtitle = "Subtitle",
            buttonText = "Button",
            onButtonClick = {},
        )
    }
}