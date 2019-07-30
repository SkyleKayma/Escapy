package fr.skyle.escapy.util

import android.content.Context
import android.graphics.Rect
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import com.nightonke.boommenu.BoomButtons.HamButton
import com.nightonke.boommenu.BoomButtons.OnBMClickListener
import com.nightonke.boommenu.Util
import fr.skyle.escapy.R

object BoomMenuBuilderUtils {

    fun getBuilder(
        context: Context,
        imageId: Int,
        pieceColorRes: Int,
        normalColorRes: Int,
        highlightedColorRes: Int,
        @StringRes normalText: Int,
        @StringRes subNormalText: Int,
        listener: OnBMClickListener
    ): HamButton.Builder {
        return HamButton.Builder()
            .normalImageDrawable(context.getDrawable(imageId))
            .rotateImage(false)
            .imagePadding(Rect(Util.dp2px(20f), Util.dp2px(20f), Util.dp2px(20f), Util.dp2px(20f)))
            .pieceColorRes(pieceColorRes)
            .normalColorRes(normalColorRes)
            .highlightedColorRes(highlightedColorRes)
            .normalText(context.getString(normalText))
            .normalTextColorRes(R.color.blue_grey_dark)
            .subNormalText(context.getString(subNormalText))
            .subNormalTextColorRes(R.color.blue_grey_dark)
            .typeface(ResourcesCompat.getFont(context, R.font.exo2_regular))
            .subTypeface(ResourcesCompat.getFont(context, R.font.exo2_regular))
            .listener(listener)
    }
}