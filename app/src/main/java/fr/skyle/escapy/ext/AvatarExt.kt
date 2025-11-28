package fr.skyle.escapy.ext

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import fr.skyle.escapy.R
import fr.skyle.escapy.data.enums.Avatar

val Avatar.iconId: Int
    @DrawableRes
    get() = when (this) {
        Avatar.AVATAR_01 -> R.drawable.avatar_01_mexican
        Avatar.AVATAR_02 -> R.drawable.avatar_02_man
        Avatar.AVATAR_03 -> R.drawable.avatar_03_pirates
        Avatar.AVATAR_04 -> R.drawable.avatar_04_girl
        Avatar.AVATAR_05 -> R.drawable.avatar_05_boy
        Avatar.AVATAR_06 -> R.drawable.avatar_06_chinese
        Avatar.AVATAR_07 -> R.drawable.avatar_07_man
        Avatar.AVATAR_08 -> R.drawable.avatar_08_police
        Avatar.AVATAR_09 -> R.drawable.avatar_09_french
        Avatar.AVATAR_10 -> R.drawable.avatar_10_girl
        Avatar.AVATAR_11 -> R.drawable.avatar_11_girl
        Avatar.AVATAR_12 -> R.drawable.avatar_12_arab
        Avatar.AVATAR_13 -> R.drawable.avatar_13_rock
        Avatar.AVATAR_14 -> R.drawable.avatar_14_boy
        Avatar.AVATAR_15 -> R.drawable.avatar_15_chinese
        Avatar.AVATAR_16 -> R.drawable.avatar_16_girl
        Avatar.AVATAR_17 -> R.drawable.avatar_17_nun
        Avatar.AVATAR_18 -> R.drawable.avatar_18_baby
        Avatar.AVATAR_19 -> R.drawable.avatar_19_vietnam
        Avatar.AVATAR_20 -> R.drawable.avatar_20_clown
        Avatar.AVATAR_21 -> R.drawable.avatar_21_indian
        Avatar.AVATAR_22 -> R.drawable.avatar_22_portuguese
        Avatar.AVATAR_23 -> R.drawable.avatar_23_oldster
        Avatar.AVATAR_24 -> R.drawable.avatar_24_girl
        Avatar.AVATAR_25 -> R.drawable.avatar_25_armenian
        Avatar.AVATAR_26 -> R.drawable.avatar_26_japan
        Avatar.AVATAR_27 -> R.drawable.avatar_27_ninja
        Avatar.AVATAR_28 -> R.drawable.avatar_28_soldier
        Avatar.AVATAR_29 -> R.drawable.avatar_29_girl
        Avatar.AVATAR_30 -> R.drawable.avatar_30_viking
        Avatar.AVATAR_31 -> R.drawable.avatar_31_boy
        Avatar.AVATAR_32 -> R.drawable.avatar_32_arab
        Avatar.AVATAR_33 -> R.drawable.avatar_33_indian
        Avatar.AVATAR_34 -> R.drawable.avatar_34_oldster
        Avatar.AVATAR_35 -> R.drawable.avatar_35_armenian
        Avatar.AVATAR_36 -> R.drawable.avatar_36_user
        Avatar.AVATAR_37 -> R.drawable.avatar_37_dj
        Avatar.AVATAR_38 -> R.drawable.avatar_38_girl
        Avatar.AVATAR_39 -> R.drawable.avatar_39_girl
        Avatar.AVATAR_40 -> R.drawable.avatar_40_safari
        Avatar.AVATAR_41 -> R.drawable.avatar_41_cowboy
        Avatar.AVATAR_42 -> R.drawable.avatar_42_boy
        Avatar.AVATAR_43 -> R.drawable.avatar_43_santa_clause
        Avatar.AVATAR_44 -> R.drawable.avatar_44_native
        Avatar.AVATAR_45 -> R.drawable.avatar_45_doctor
        Avatar.AVATAR_46 -> R.drawable.avatar_46_russia
        Avatar.AVATAR_47 -> R.drawable.avatar_47_scientist
        Avatar.AVATAR_48 -> R.drawable.avatar_48_girl
        Avatar.AVATAR_49 -> R.drawable.avatar_49_bellboy
        Avatar.AVATAR_50 -> R.drawable.avatar_50_king
    }

val Avatar.icon: Painter
    @Composable
    get() = painterResource(iconId)