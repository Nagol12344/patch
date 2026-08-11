package app.template.patches.bplace

import app.morphe.patcher.Fingerprint
import com.android.tools.smali.dexlib2.AccessFlags

object OnShowFileChooserFingerprint : Fingerprint(
    definingClass = "LA5/h1;",
    name = "onShowFileChooser",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    returnType = "Z",
    parameters = listOf(
        "Landroid/webkit/WebView;",
        "Landroid/webkit/ValueCallback;",
        "Landroid/webkit/WebChromeClient\$FileChooserParams;"
    )
)