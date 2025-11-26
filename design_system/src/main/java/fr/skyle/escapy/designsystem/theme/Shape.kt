package fr.skyle.escapy.designsystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

val ExtraSmallRadiusToken = 4.dp
val SmallRadiusToken = 8.dp
val MediumRadiusToken = 12.dp
val LargeRadiusToken = 16.dp
val ExtraLargeRadiusToken = 20.dp

val ExtraSmallShapeToken = RoundedCornerShape(ExtraSmallRadiusToken)
val SmallShapeToken = RoundedCornerShape(SmallRadiusToken)
val MediumShapeToken = RoundedCornerShape(MediumRadiusToken)
val LargeShapeToken = RoundedCornerShape(LargeRadiusToken)
val ExtraLargeShapeToken = RoundedCornerShape(ExtraLargeRadiusToken)

@Immutable
data class ProjectShapes(
    val extraSmall: Shape,
    val small: Shape,
    val medium: Shape,
    val large: Shape,
    val extraLarge: Shape,
)

val CustomShapes = ProjectShapes(
    extraSmall = ExtraSmallShapeToken,
    small = SmallShapeToken,
    medium = MediumShapeToken,
    large = LargeShapeToken,
    extraLarge = ExtraLargeShapeToken
)

val LocalShapes = compositionLocalOf<ProjectShapes> {
    error("No ProjectShapes provided")
}