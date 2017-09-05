/*
 * Copyright 2017 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.chunsheng.kotlinlearn.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.annotation.IntRange
import android.support.design.widget.BottomNavigationView
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.ViewTreeObserver
import me.chunsheng.kotlinlearn.R


/**
 * A (rough and ready) extension to [BottomNavigationView] which shows a badge over a menu
 * item to indicate new content therein.
 */
class BadgedBottomNavigationView(context: Context, attrs: AttributeSet) : BottomNavigationView(context, attrs) {

    private var badgePosition = NO_BADGE_POSITION
    private val badgePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val radius: Float
    private var listener: BottomNavigationView.OnNavigationItemSelectedListener? = null
    private val drawListener = ViewTreeObserver.OnDrawListener {
        if (badgePosition > NO_BADGE_POSITION) {
            postInvalidateOnAnimation()
        }
    }

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.BadgedBottomNavigationView)
        badgePaint.color = a.getColor(R.styleable.BadgedBottomNavigationView_badgeColor, Color.TRANSPARENT)
        radius = a.getDimension(R.styleable.BadgedBottomNavigationView_badgeRadius, 0f)
        a.recycle()

        // we need to listen to tab selection to re-position the badge so set our own listener
        // wrapping any provided listener.
        super.setOnNavigationItemSelectedListener(OnNavigationItemSelectedListener { item ->
            if (badgePosition > NO_BADGE_POSITION) {
                // use a pre-draw listener to invalidate ourselves when the badged item moves
                viewTreeObserver.addOnDrawListener(drawListener)
            }
            if (listener != null) return@OnNavigationItemSelectedListener listener!!.onNavigationItemSelected(item)
            false
        })
    }

    fun showBadge(@IntRange(from = 0) itemPosition: Int) {
        badgePosition = itemPosition
        invalidate()
    }

    fun clearBadge() {
        badgePosition = NO_BADGE_POSITION
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (badgePosition > NO_BADGE_POSITION) {
            val menuView = getChildAt(0) as ViewGroup
            val menuItem = menuView.getChildAt(badgePosition) as ViewGroup
            val icon = menuItem.getChildAt(0) ?: return

            val cx = (menuView.left + menuItem.right - icon.left).toFloat()
            canvas.drawCircle(cx, icon.top.toFloat(), radius, badgePaint)
        }
    }

    override fun setOnNavigationItemSelectedListener(
            listener: BottomNavigationView.OnNavigationItemSelectedListener?) {
        this.listener = listener
    }

    override fun onDetachedFromWindow() {
        viewTreeObserver.removeOnDrawListener(drawListener)
        super.onDetachedFromWindow()
    }

    companion object {

        private val NO_BADGE_POSITION = -1
    }
}
