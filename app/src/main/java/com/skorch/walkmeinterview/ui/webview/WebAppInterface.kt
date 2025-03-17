package com.skorch.walkmeinterview.ui.webview

import android.webkit.JavascriptInterface

class WebAppInterface(private val onMessageReceived: (String) -> Unit) {

    @JavascriptInterface
    fun showMessage(message: String) {
        onMessageReceived(message)
    }

    @JavascriptInterface
    fun pageLoaded() {
        onMessageReceived("Page loaded!")
    }
}