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
        Avatar.AVATAR_01 -> R.drawable.avatar_01
        Avatar.AVATAR_02 -> R.drawable.avatar_02
        Avatar.AVATAR_03 -> R.drawable.avatar_03
        Avatar.AVATAR_04 -> R.drawable.avatar_04
        Avatar.AVATAR_05 -> R.drawable.avatar_05
        Avatar.AVATAR_06 -> R.drawable.avatar_06
        Avatar.AVATAR_07 -> R.drawable.avatar_07
        Avatar.AVATAR_08 -> R.drawable.avatar_08
        Avatar.AVATAR_09 -> R.drawable.avatar_09
        Avatar.AVATAR_10 -> R.drawable.avatar_10
        Avatar.AVATAR_11 -> R.drawable.avatar_11
        Avatar.AVATAR_12 -> R.drawable.avatar_12
        Avatar.AVATAR_13 -> R.drawable.avatar_13
        Avatar.AVATAR_14 -> R.drawable.avatar_14
        Avatar.AVATAR_15 -> R.drawable.avatar_15
        Avatar.AVATAR_16 -> R.drawable.avatar_16
        Avatar.AVATAR_17 -> R.drawable.avatar_17
        Avatar.AVATAR_18 -> R.drawable.avatar_18
        Avatar.AVATAR_19 -> R.drawable.avatar_19
        Avatar.AVATAR_20 -> R.drawable.avatar_20
        Avatar.AVATAR_21 -> R.drawable.avatar_21
        Avatar.AVATAR_22 -> R.drawable.avatar_22
        Avatar.AVATAR_23 -> R.drawable.avatar_23
        Avatar.AVATAR_24 -> R.drawable.avatar_24
        Avatar.AVATAR_25 -> R.drawable.avatar_25
        Avatar.AVATAR_26 -> R.drawable.avatar_26
        Avatar.AVATAR_27 -> R.drawable.avatar_27
        Avatar.AVATAR_28 -> R.drawable.avatar_28
        Avatar.AVATAR_29 -> R.drawable.avatar_29
        Avatar.AVATAR_30 -> R.drawable.avatar_30
        Avatar.AVATAR_31 -> R.drawable.avatar_31
        Avatar.AVATAR_32 -> R.drawable.avatar_32
        Avatar.AVATAR_33 -> R.drawable.avatar_33
        Avatar.AVATAR_34 -> R.drawable.avatar_34
        Avatar.AVATAR_35 -> R.drawable.avatar_35
        Avatar.AVATAR_36 -> R.drawable.avatar_36
        Avatar.AVATAR_37 -> R.drawable.avatar_37
        Avatar.AVATAR_38 -> R.drawable.avatar_38
        Avatar.AVATAR_39 -> R.drawable.avatar_39
        Avatar.AVATAR_40 -> R.drawable.avatar_40
        Avatar.AVATAR_41 -> R.drawable.avatar_41
        Avatar.AVATAR_42 -> R.drawable.avatar_42
        Avatar.AVATAR_43 -> R.drawable.avatar_43
        Avatar.AVATAR_44 -> R.drawable.avatar_44
        Avatar.AVATAR_45 -> R.drawable.avatar_45
        Avatar.AVATAR_46 -> R.drawable.avatar_46
        Avatar.AVATAR_47 -> R.drawable.avatar_47
        Avatar.AVATAR_48 -> R.drawable.avatar_48
        Avatar.AVATAR_49 -> R.drawable.avatar_49
        Avatar.AVATAR_50 -> R.drawable.avatar_50
    }

val Avatar.icon: Painter
    @Composable
    get() = painterResource(iconId)