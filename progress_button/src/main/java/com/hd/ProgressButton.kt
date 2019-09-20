package com.hd

import android.content.Context
import android.graphics.PorterDuff
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import kotlinx.android.synthetic.main.layout_button.view.*

class ProgressButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : CardView(context, attrs, defStyle) {

    var text = ""
        set(value) {
            field = value
            button.text = value
        }

    var allowClick: Boolean = true
        set(value) {
            field = value
            button.isClickable = allowClick
        }

    var showProgress = false
        set(value) {
            field = value
            if (showProgress) {
                button.text = ""
                button.isEnabled = false
                progressBar.visibility = View.VISIBLE
            } else {
                button.text = text
                button.isEnabled = true
                progressBar.visibility = View.GONE
            }
        }

    fun setClickListener(listener: OnClickListener) {
        button.setOnClickListener(listener)
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_button, this, true)
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(
                it,
                R.styleable.ProgressButton, 0, 0
            )
            text = resources.getText(
                typedArray.getResourceId(
                    R.styleable.ProgressButton_button_text,
                    R.string.blank
                )
            ).toString()
            showProgress = typedArray.getBoolean(R.styleable.ProgressButton_show_progress, false)
            button.setBackgroundResource(
                typedArray.getResourceId(
                    R.styleable.ProgressButton_button_background,
                    R.color.transparent
                )
            )
            button.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                typedArray.getDimensionPixelSize(
                    R.styleable.ProgressButton_button_text_size,
                    14
                ).toFloat()
            )
            button.setTextColor(
                typedArray.getColor(
                    R.styleable.ProgressButton_button_text_color, ContextCompat.getColor(
                        context,
                        R.color.white
                    )
                )
            )
            radius = typedArray.getDimensionPixelSize(
                R.styleable.ProgressButton_card_radius,
                (height / 2)
            ).toFloat()
            if (radius == 0f)
                cardElevation = 0f
            setCardBackgroundColor(
                typedArray.getColor(
                    R.styleable.ProgressButton_card_color, ContextCompat.getColor(
                        context,
                        R.color.transparent
                    )
                )
            )
            button.setCompoundDrawablesWithIntrinsicBounds(
                typedArray.getResourceId(
                    R.styleable.ProgressButton_left_icon,
                    0
                ), 0, 0, 0
            )
            val progressColor = typedArray.getColor(
                R.styleable.ProgressButton_progress_color, ContextCompat.getColor(
                    context,
                    R.color.white
                )
            )
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                val drawableProgress = DrawableCompat.wrap(progressBar.indeterminateDrawable)
                DrawableCompat.setTint(drawableProgress, progressColor)
                progressBar.indeterminateDrawable = drawableProgress
            } else {
                progressBar.indeterminateDrawable.setColorFilter(
                    progressColor,
                    PorterDuff.Mode.SRC_IN
                )
            }
            typedArray.recycle()
        }
    }

}
