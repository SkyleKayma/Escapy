package fr.skyle.escapy.ui.core.form

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.R
import fr.skyle.escapy.designsystem.theme.ProjectTheme

@Composable
fun PasswordFormValidationRow(
    isValid: Boolean,
    text: String,
    modifier: Modifier = Modifier
) {
    val circleColor by animateColorAsState(
        targetValue = if (isValid) {
            ProjectTheme.colors.success
        } else {
            ProjectTheme.colors.grey500
        }
    )

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(circleColor),
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = text,
            style = ProjectTheme.typography.p3,
            color = ProjectTheme.colors.grey500,
        )
    }
}

@Preview
@Composable
private fun PasswordFormValidationRowPreview() {
    ProjectTheme {
        PasswordFormValidationRow(
            isValid = false,
            text = stringResource(R.string.generic_password_rule_uppercase)
        )
    }
}

@Preview
@Composable
private fun PasswordFormValidationRowValidPreview() {
    ProjectTheme {
        PasswordFormValidationRow(
            isValid = true,
            text = stringResource(R.string.generic_password_rule_uppercase)
        )
    }
}