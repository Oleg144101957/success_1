package tv.rasucc.ov.time.presentation.customUI

import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient

class CustomWebViewClient : WebViewClient() {

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)

        Log.d("123123", "The url of the loaded page is $url")

    }
}