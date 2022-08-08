package com.example.qiita.view.common

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.example.qiita.R

class Loading {
    companion object {
        private var mDialog: Dialog? = null

        fun setContext(context: Context) {
            mDialog = Dialog(context)
            mDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            mDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mDialog?.setContentView(R.layout.progress)
        }

        fun clearContext() {
            mDialog = null
        }

        fun show() {
            mDialog?.show()
        }

        fun dismiss() {
            if (mDialog != null && mDialog?.isShowing == true) {
                mDialog?.dismiss()
            }
        }
    }
}