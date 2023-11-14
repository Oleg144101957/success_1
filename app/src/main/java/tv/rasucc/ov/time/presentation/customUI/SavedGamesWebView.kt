package tv.rasucc.ov.time.presentation.customUI

import android.content.Context
import android.webkit.CookieManager
import android.webkit.WebView

class SavedGamesWebView(context: Context) : WebView(context){
    init {

        CookieManager.getInstance().setAcceptCookie(true)
        CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
        webViewClient = CustomWebViewClient()
        webChromeClient = CustomWebChromeClient()

        with(settings){
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = false
            userAgentString = userAgentString.replace(" ; wv", "")
        }
        loadUrl("https://ru.wikipedia.org/wiki/ASD")


    }
}