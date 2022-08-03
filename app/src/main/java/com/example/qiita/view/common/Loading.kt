package com.example.qiita.view.common

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager
import com.example.qiita.R

class Loading {
    companion object {
        private var mDialog: Dialog? = null

        fun setContext(context: Context) {
            mDialog = Dialog(context)
        }

        fun clearContext() {
            mDialog = null
        }

        fun show() {
            mDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            mDialog?.window?.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
            )
            mDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mDialog?.setContentView(R.layout.progress)
            mDialog?.show()
        }

        fun dismiss() {
            if (mDialog != null && mDialog?.isShowing == true) {
                mDialog?.dismiss()
                mDialog = null
            }
        }
    }
}