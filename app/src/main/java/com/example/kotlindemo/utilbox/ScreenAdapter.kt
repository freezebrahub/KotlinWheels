package com.example.kotlindemo.utilbox

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.TextView
import java.lang.Exception

/**
 * 屏幕适配工具类
 */
class ScreenAdapter {
    companion object {
        private var screenWidth: Int = 0
        private var screenHeight: Int = 0

        private var defaultWidth: Int = 0
        private var defaultHeight: Int = 0

        fun getInstance(context: Context): ScreenAdapter{
            val displayMetrics: DisplayMetrics = context.resources.displayMetrics
            screenWidth = displayMetrics.widthPixels

            screenHeight = when (displayMetrics.heightPixels) {
                672 -> {
                    720
                }
                else -> {
                    displayMetrics.heightPixels
                }
            }

            defaultWidth = if (screenWidth > screenHeight) 1920 else 1080
            defaultHeight = if (screenWidth > screenHeight) 1080 else 1920

            return ScreenAdapter()
        }
    }

    fun scaleTextSize(textView: TextView, size: Float) {
        val textSize: Float = size / defaultHeight * screenHeight
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
    }

    fun scaleX(x: Int): Int {
        var scaleX = x * screenWidth / defaultWidth
        if (scaleX == 0 && x != 0) {
            scaleX = if (x < 0) -1 else 1
        }
        return scaleX
    }

    fun scaleY(y: Int): Int {
        var scaleY = y * screenHeight / defaultHeight
        if (scaleY == 0 && y != 0) {
            scaleY = if (y < 0) -1 else 1
        }
        return scaleY
    }

    /**
     * 适配View整体，以及包含的子View
     */
    fun adaptionView(view: View) {
        try {
            adaptWH(view.layoutParams)
            adaptMargin(view.layoutParams)
            adaptPadding(view)
            if (view is TextView) {
                scaleTextSize(view, view.textSize)
                adaptTextViewFeature(view)
            }

            if (view is ViewGroup) {
                for (i in 0..view.childCount) {
                    val childView: View = view.getChildAt(i)
                    adaptionView(childView)
                }
            }
        } catch (e: Exception) {
            Log.e("adaptionView", e.message)
        }
    }

    /**
     * 适配View宽高
     */
    private fun adaptWH(params: ViewGroup.LayoutParams) {
        if (params.width > 0) {
            params.width = scaleX(params.width)
        }
        if (params.height > 0) {
            params.height = scaleY(params.height)
        }
    }

    /**
     * 适配ViewMargin
     */
    private fun adaptMargin(params: ViewGroup.LayoutParams){
        if (params is MarginLayoutParams) {
            params.marginStart = scaleX(params.marginStart)
            params.marginEnd = scaleY(params.marginEnd)
            params.setMargins(scaleX(params.leftMargin), scaleY(params.topMargin), scaleX(params.rightMargin), scaleY(params.bottomMargin))
        }
    }

    /**
     * 适配ViewPadding
     */
    private fun adaptPadding(view: View){
        val paddingLeft = if (view.paddingStart == 0) view.paddingLeft else view.paddingStart
        val paddingRight = if (view.paddingEnd == 0) view.paddingRight else view.paddingEnd
        view.setPaddingRelative(scaleX(paddingLeft), scaleY(view.paddingTop), scaleX(paddingRight), scaleY(view.paddingBottom))
    }

    /**
     * 适配TextView相关特性
     */
    private fun adaptTextViewFeature(textView: TextView){
        val drawablePadding = textView.compoundDrawablePadding
        if (drawablePadding > 0){
            textView.compoundDrawablePadding = scaleX(drawablePadding)
        }
        val maxHeight = textView.maxHeight
        if (maxHeight > 0 && maxHeight != Int.MAX_VALUE){
            textView.maxHeight = scaleY(maxHeight)
        }
        val maxWidth = textView.maxWidth
        if (maxWidth > 0 && maxWidth != Int.MAX_VALUE){
            textView.maxWidth = scaleX(maxWidth)
        }
    }
}