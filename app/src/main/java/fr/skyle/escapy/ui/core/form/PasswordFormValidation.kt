package fr.skyle.escapy.ui.core.form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.R
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.utils.ProjectComponentPreview

@Composable
fun PasswordFormValidation(
    containsUppercase: Boolean,
    isLongEnough: Boolean,
    containsDigit: Boolean,
    containsSpecialCharacter: Boolean,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        PasswordFormValidationRow(
            modifier = Modifier.fillMaxWidth(),
            isValid = containsUppercase,
            text = stringResource(id = R.string.generic_password_rule_uppercase)
        )

        Spacer(modifier = Modifier.height(4.dp))

        PasswordFormValidationRow(
            modifier = Modifier.fillMaxWidth(),
            isValid = isLongEnough,
            text = stringResource(id = R.string.generic_password_rule_min_length)
        )

        Spacer(modifier = Modifier.height(4.dp))

        PasswordFormValidationRow(
            modifier = Modifier.fillMaxWidth(),
            isValid = containsDigit,
            text = stringResource(id = R.string.generic_password_rule_digit)
        )

        Spacer(modifier = Modifier.height(4.dp))

        PasswordFormValidationRow(
            modifier = Modifier.fillMaxWidth(),
            isValid = containsSpecialCharacter,
            text = stringResource(id = R.string.generic_password_rule_special)
        )
    }
}

@ProjectComponentPreview
@Composable
private fun PasswordFormValidationPreview() {
    ProjectTheme {
        PasswordFormValidation(
            containsUppercase = false,
            isLongEnough = false,
            containsDigit = false,
            containsSpecialCharacter = false,
        )
    }
}

@ProjectComponentPreview
@Composable
private fun PasswordFormValidationAllValidPreview() {
    ProjectTheme {
        PasswordFormValidation(
            containsUppercase = true,
            isLongEnough = true,
            containsDigit = true,
            containsSpecialCharacter = true,
        )
    }
}