package com.visualpassword.androidapp

import LocalWebServer
import android.os.Bundle
import android.view.WindowManager
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private lateinit var server: LocalWebServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val port = 24347

        server = LocalWebServer(assets, port).apply { start() }

        val webView: WebView = findViewById(R.id.webView)
        with(webView.settings) {
            javaScriptEnabled = true
            domStorageEnabled = true
            allowFileAccess = true
            allowContentAccess = true
            cacheMode = WebSettings.LOAD_NO_CACHE
        }

        webView.addJavascriptInterface(ClipboardInterface(this), "AndroidClipboard")

        webView.loadUrl("http://localhost:$port/index.html")
    }

    override fun onDestroy() {
        super.onDestroy()
        server.stop()
    }
}
