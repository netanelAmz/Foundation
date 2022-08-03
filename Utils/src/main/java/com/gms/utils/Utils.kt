package com.gms.utils

import android.R.attr
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.os.Message
import android.util.AttributeSet

import com.google.android.material.snackbar.Snackbar

import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat

import androidx.core.view.setPadding

import android.graphics.drawable.GradientDrawable
import android.view.*
import android.content.DialogInterface
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginTop



fun View.snackbarFailed(message: String) {
    Snackbar.make(
        this,
        message,
        Snackbar.LENGTH_SHORT
    ).also {
        it.view.setBackgroundColor(Color.parseColor("#D83025"))


    }.also {
        it.anchorView = this
    }.show()

}

fun View.snackbarSuccess(message: String) {
    Snackbar.make(
        this,
        message,
        Snackbar.LENGTH_SHORT
    ).also {
        it.view.setBackgroundColor(Color.parseColor("#15B76C"))

    }.also {
        it.anchorView = this
    }.show()

}


fun Activity.closeKeyBoard(editText: EditText){
    val imm =
        this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(editText.windowToken, 0)
    this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
}


fun Activity.playSoundOk(){
    val mPlayer2: MediaPlayer
    val resID = resources.getIdentifier(
        "file",
        "raw",
        packageName
    )

    mPlayer2 = MediaPlayer.create(this, resID)
    mPlayer2.start()
}

fun Activity.playSoundError(){
    val mPlayer2: MediaPlayer
    val resID = resources.getIdentifier(
        "error",
        "raw",
        packageName
    )

    mPlayer2 = MediaPlayer.create(this, resID)
    mPlayer2.start()
}

fun Activity.getDeviceId(): String {
    val deviceId: String
    deviceId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        Settings.Secure.getString(
            this.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    } else {
        val mTelephony = this.getSystemService(AppCompatActivity.TELEPHONY_SERVICE) as TelephonyManager
        if (mTelephony.deviceId != null) {
            mTelephony.deviceId
        } else {
            Settings.Secure.getString(
                this.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        }
    }
    return deviceId
}




class CustomGmsButton : AppCompatButton {
    override fun setEnabled(enabled: Boolean) {
        if(enabled){
                this.init()

        }else{
            this.initPressed()

        }
        super.setEnabled(enabled)
    }


    constructor(context: Context) : super(context) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init()
    }


    private fun init() {
        // this.background = ContextCompat.getDrawable(context, R.drawable.buttonfocused3);
        this.setPadding(dipToPx(8f))
        this.textSize = 14f
        this.setTextColor(context.getColor(R.color.white))
        this.typeface = Typeface.DEFAULT_BOLD
        this.gravity = Gravity.CENTER or Gravity.BOTTOM
        val shape = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(0xFF4a99bd.toInt(), 0xFF436988.toInt())
        )
        shape.shape = GradientDrawable.RECTANGLE
        shape.cornerRadius = (8f)
        shape.setStroke(3, ContextCompat.getColor(context, R.color.white))
        this.background = shape


    }

    private fun initPressed() {

        // this.background = ContextCompat.getDrawable(context, R.drawable.buttonfocused3);
        this.setPadding(dipToPx(8f))
        this.textSize = 14f
        this.setTextColor(context.getColor(R.color.grayButton))
        this.typeface = Typeface.DEFAULT_BOLD
        this.gravity = Gravity.CENTER or Gravity.BOTTOM
        val shape = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(0xFFa3a3a3.toInt(), 0xFF436988.toInt())
        )
        shape.shape = GradientDrawable.RECTANGLE
        shape.cornerRadii = floatArrayOf(5f, 5f, 5f, 5f, 0f, 0f, 0f, 0f)
        shape.setStroke(1, ContextCompat.getColor(context, R.color.white))

        this.background = shape

    }


    fun dipToPx(dip: Float): Int {
        return (dip * Resources.getSystem().displayMetrics.density).toInt()
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                if(this.isEnabled) {

                    this.initPressed()
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
                if(this.isEnabled) {
                    this.init()
                    invalidate()
                }
            }
        }
        return super.onTouchEvent(event)
    }
}