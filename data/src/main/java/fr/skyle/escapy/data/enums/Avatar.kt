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
    AVATAR_10(type = 10),
    AVATAR_11(type = 11),
    AVATAR_12(type = 12),
    AVATAR_13(type = 13),
    AVATAR_14(type = 14),
    AVATAR_15(type = 15),
    AVATAR_16(type = 16),
    AVATAR_17(type = 17),
    AVATAR_18(type = 18),
    AVATAR_19(type = 19),
    AVATAR_20(type = 20),
    AVATAR_21(type = 21),
    AVATAR_22(type = 22),
    AVATAR_23(type = 23),
    AVATAR_24(type = 24),
    AVATAR_25(type = 25),
    AVATAR_26(type = 26),
    AVATAR_27(type = 27),
    AVATAR_28(type = 28),
    AVATAR_29(type = 29),
    AVATAR_30(type = 30),
    AVATAR_31(type = 31),
    AVATAR_32(type = 32),
    AVATAR_33(type = 33),
    AVATAR_34(type = 34),
    AVATAR_35(type = 35),
    AVATAR_36(type = 36),
    AVATAR_37(type = 37),
    AVATAR_38(type = 38),
    AVATAR_39(type = 39),
    AVATAR_40(type = 40),
    AVATAR_41(type = 41),
    AVATAR_42(type = 42),
    AVATAR_43(type = 43),
    AVATAR_44(type = 44),
    AVATAR_45(type = 45),
    AVATAR_46(type = 46),
    AVATAR_47(type = 47),
    AVATAR_48(type = 48),
    AVATAR_49(type = 49),
    AVATAR_50(type = 50);

    companion object {
        fun fromType(value: Int?): Avatar? =
            entries.find { it.type == value }
    }
}