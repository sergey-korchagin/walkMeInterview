package com.skorch.walkmeinterview.ui.webview

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.skorch.walkmeinterview.ui.theme.toHexColor

@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebViewScreen(url: String, navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    val darkTheme = isSystemInDarkTheme()
    var isLoading by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        val primaryColor = MaterialTheme.colorScheme.primary.toHexColor()
        val textColor = MaterialTheme.colorScheme.onBackground.toHexColor()
        val backgroundColor = MaterialTheme.colorScheme.background.toHexColor()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true
                        settings.allowFileAccess = true
                        settings.allowContentAccess = true

                        addJavascriptInterface(WebAppInterface { msg ->
                            message = msg
                            showDialog = true
                        }, "ArticleBridge")

                        webViewClient = object : WebViewClient() {
                            override fun onPageStarted(
                                view: WebView?,
                                url: String?,
                                favicon: Bitmap?
                            ) {
                                super.onPageStarted(view, url, favicon)
                                isLoading = true

                            }

                            override fun onPageFinished(view: WebView?, url: String?) {
                                super.onPageFinished(view, url)
                                isLoading = false
                                val text = "Hi WalkMe!"

                                view?.evaluateJavascript(
                                    """
                                (function() {
                                    let style = document.createElement("style");
                                    style.innerHTML = `
                                        body {
                                            background-color: $backgroundColor !important;
                                            color: $textColor !important;
                                        }
                                        a {
                                            color: $primaryColor !important;
                                            text-decoration: underline;
                                        }
                                        img {
                                            filter: brightness(${if (darkTheme) "0.8" else "1.0"}) contrast(1.2);
                                        }
                                    `;
                                    document.head.appendChild(style);

                                    let messageDiv = document.createElement("div");
                                    messageDiv.innerText = "$text";
                                    messageDiv.style.fontSize = "20px";
                                    messageDiv.style.fontWeight = "bold";
                                    messageDiv.style.padding = "20px";
                                    messageDiv.style.backgroundColor = "$primaryColor";
                                    messageDiv.style.color = "$backgroundColor";
                                    document.body.insertBefore(messageDiv, document.body.firstChild);

                                    setTimeout(() => {
                                        if (typeof ArticleBridge !== "undefined") {
                                            ArticleBridge.pageLoaded();
                                        }
                                    }, 500);
                                })();
                                """, null
                                )
                            }
                        }
                        loadUrl(url)
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
            )

            if (isLoading) {
                CircularProgressIndicator()
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("OK")
                }
            },
            title = { Text(text = "Message from WebView") },
            text = { Text(text = message) }
        )
    }
}



