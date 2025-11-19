package fr.skyle.escapy.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import fr.skyle.escapy.R
import fr.skyle.escapy.data.enums.Avatar

val Avatar.icon: Painter?
    @Composable
    get() = when (this) {
        Avatar.AVATAR_01 ->
            painterResource(R.drawable.avatar_01)

        Avatar.AVATAR_02 ->
            painterResource(R.drawable.avatar_02)

        Avatar.AVATAR_03 ->
            painterResource(R.drawable.avatar_03)

        Avatar.AVATAR_04 ->
            painterResource(R.drawable.avatar_04)

        Avatar.AVATAR_05 ->
            painterResource(R.drawable.avatar_05)

        Avatar.AVATAR_06 ->
            painterResource(R.drawable.avatar_06)

        Avatar.AVATAR_07 ->
            painterResource(R.drawable.avatar_07)

        Avatar.AVATAR_08 ->
            painterResource(R.drawable.avatar_08)

        Avatar.AVATAR_09 ->
            painterResource(R.drawable.avatar_09)

        Avatar.AVATAR_10 ->
            painterResource(R.drawable.avatar_10)
    }