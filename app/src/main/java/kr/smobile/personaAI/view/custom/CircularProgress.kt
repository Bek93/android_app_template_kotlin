package net.wepla.campus_planet.view.custom

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import net.wepla.campus_planet.R
import net.wepla.campus_planet.databinding.DialogLoadingBinding

class CircularProgress(context: Context) : Dialog(context) {

    lateinit var binding: DialogLoadingBinding

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_loading)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


}