// WebViewClientFingerprint.kt
package app.template.patches.bplace

import app.morphe.patcher.Fingerprint

object OnPageStartedFingerprint : Fingerprint(
    returnType = "V",
    parameters = listOf("Landroid/webkit/WebView;", "Ljava/lang/String;", "Landroid/graphics/Bitmap;"),
    customFingerprint = { method, _ ->
        method.name == "onPageStarted" &&
        method.definingClass.endsWith("C0123k1;") // adjust to full smali path once confirmed
    }
)