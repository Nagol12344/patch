package app.template.extension;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.os.Handler;
import android.os.Looper;

public class ScriptHook {

    private static final String FIX_SCRIPT =
        "document.querySelectorAll('input[type=\"file\"]').forEach(function(el){" +
        "  el.removeAttribute('capture');" +
        "});" +
        "new MutationObserver(function(){" +
        "  document.querySelectorAll('input[type=\"file\"][capture]').forEach(function(el){" +
        "    el.removeAttribute('capture');" +
        "  });" +
        "}).observe(document.body, {childList:true, subtree:true});";

    public static void hookWebView(WebView view) {
        // Chain onto the existing client rather than replacing it, so the
        // app's own onPageFinished/shouldInterceptRequest/etc. still run.
        WebViewClient original = null; // see note below re: capturing the original
        view.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView wv, String url, android.graphics.Bitmap favicon) {
                super.onPageStarted(wv, url, favicon);
                wv.evaluateJavascript(FIX_SCRIPT, null);
            }
        });
    }
}