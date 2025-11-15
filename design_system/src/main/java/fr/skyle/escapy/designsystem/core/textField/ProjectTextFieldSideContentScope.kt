package fr.skyle.escapy.designsystem.core.textField

enum class ProjectTextFieldContentPosition {
    LEADING,
    TRAILING
}

/**
 * Scope for the side content of [ProjectTextField].
 */
interface ProjectTextFieldSideContentScope {
    val position: ProjectTextFieldContentPosition
    val textFieldColors: ProjectTextFieldDefaults.Colors
    val isEnabled: Boolean
}

data class ProjectTextFieldSideContentScopeImpl(
    override val position: ProjectTextFieldContentPosition,
    override val textFieldColors: ProjectTextFieldDefaults.Colors,
    override val isEnabled: Boolean
) : ProjectTextFieldSideContentScope