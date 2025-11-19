package fr.skyle.escapy.data.enums

enum class Avatar(
    val type: Int
) {
    AVATAR_01(type = 1),
    AVATAR_02(type = 2),
    AVATAR_03(type = 3),
    AVATAR_04(type = 4),
    AVATAR_05(type = 5),
    AVATAR_06(type = 6),
    AVATAR_07(type = 7),
    AVATAR_08(type = 8),
    AVATAR_09(type = 9),
    AVATAR_10(type = 10);

    companion object {
        fun fromType(value: Int?): Avatar? =
            entries.find { it.type == value }
    }
}