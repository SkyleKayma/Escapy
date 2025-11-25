package fr.skyle.escapy.ui.screens.home.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleRight
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.R
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ext.boxCardStyle
import fr.skyle.escapy.utils.ProjectComponentPreview

@Composable
fun HomeActionCell(
    text: String,
    icon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .boxCardStyle()
            .clickable(onClick = onClick)
            .padding(12.dp),
    ) {
        Icon(
            modifier = Modifier
                .clip(CircleShape)
                .background(ProjectTheme.colors.black.copy(alpha = 0.2f))
                .size(48.dp)
                .padding(8.dp),
            painter = icon,
            contentDescription = null,
            tint = ProjectTheme.colors.onSurface
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.weight(1f),
                text = text,
                style = ProjectTheme.typography.b1,
                color = ProjectTheme.colors.onSurface
            )

            Spacer(modifier = Modifier.width(12.dp))

            Icon(
                modifier = Modifier.size(24.dp),
                painter = rememberVectorPainter(Icons.Default.ArrowCircleRight),
                contentDescription = null,
                tint = ProjectTheme.colors.primary
            )
        }
    }
}

@ProjectComponentPreview
@Composable
private fun HomeActionCellPreview() {
    ProjectTheme {
        HomeActionCell(
            text = stringResource(R.string.home_join_a_room_via_qrcode),
            icon = rememberVectorPainter(Icons.Default.QrCode),
            onClick = {}
        )
    }
}