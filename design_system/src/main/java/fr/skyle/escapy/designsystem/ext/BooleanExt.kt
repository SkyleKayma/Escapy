package fr.skyle.escapy.designsystem.ext

private val booleanValues = listOf(true, false)
val Boolean.Companion.values: List<Boolean>
    get() = booleanValues