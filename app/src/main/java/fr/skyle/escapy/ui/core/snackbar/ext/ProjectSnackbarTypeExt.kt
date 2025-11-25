package fr.skyle.escapy.ui.core.snackbar.ext

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ui.core.snackbar.ProjectSnackbarDefaults

val ProjectSnackbarDefaults.ProjectSnackbarType.painter: Painter
    @Composable
    get() = when (this) {
        ProjectSnackbarDefaults.ProjectSnackbarType.NEUTRAL ->
            rememberVectorPainter(Icons.Default.Info)

        ProjectSnackbarDefaults.ProjectSnackbarType.SUCCESS ->
            rememberVectorPainter(Icons.Default.CheckCircle)

        ProjectSnackbarDefaults.ProjectSnackbarType.ERROR ->
            rememberVectorPainter(Icons.Default.Error)
    }

val ProjectSnackbarDefaults.ProjectSnackbarType.iconColor: Color
    @Composable
    get() = when (this) {
        ProjectSnackbarDefaults.ProjectSnackbarType.NEUTRAL ->
            ProjectTheme.colors.onSurface

        ProjectSnackbarDefaults.ProjectSnackbarType.SUCCESS ->
            ProjectTheme.colors.success

        ProjectSnackbarDefaults.ProjectSnackbarType.ERROR ->
            ProjectTheme.colors.error
    }