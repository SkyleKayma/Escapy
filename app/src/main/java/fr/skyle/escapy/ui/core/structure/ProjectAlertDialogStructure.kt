package fr.skyle.escapy.ui.core.structure

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.designsystem.core.button.ProjectButton
import fr.skyle.escapy.designsystem.core.button.ProjectButtonDefaults
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.utils.ProjectComponentPreview

@Composable
fun ProjectAlertDialogStructure(
    title: String,
    buttonText: String,
    onButtonClick: () -> Unit,
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

        ProjectButton(
            modifier = Modifier.fillMaxWidth(),
            text = buttonText,
            size = ProjectButtonDefaults.Size.MEDIUM,
            tint = ProjectButtonDefaults.Tint.PRIMARY,
            style = ProjectButtonDefaults.Style.FILLED,
            onClick = onButtonClick,
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