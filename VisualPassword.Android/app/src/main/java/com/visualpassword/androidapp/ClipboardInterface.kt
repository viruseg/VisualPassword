package com.visualpassword.androidapp

import android.content.ClipboardManager
import android.content.Context
import android.webkit.JavascriptInterface

class ClipboardInterface(private val context: Context) {

    @JavascriptInterface
    fun readClipboard(): String {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = clipboard.primaryClip
        return if (clipData != null && clipData.itemCount > 0) {
            clipData.getItemAt(0).text?.toString() ?: ""
        } else {
            ""
        }
    }
}