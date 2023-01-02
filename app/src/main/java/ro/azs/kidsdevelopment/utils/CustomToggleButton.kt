package ro.azs.kidsdevelopment.utils

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatToggleButton

class CustomToggleButton(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : AppCompatToggleButton(context, attrs, defStyleAttr) {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, android.R.attr.buttonStyleToggle)


    override fun setTextOff(textOff: CharSequence?) {
        super.setTextOff(textOff)
        isChecked = isChecked

    }

    override fun setTextOn(textOn: CharSequence?) {
        super.setTextOn(textOn)
        isChecked = isChecked
    }
}