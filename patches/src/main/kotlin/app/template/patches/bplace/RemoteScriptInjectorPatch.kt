package app.template.patches.bplace

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility
import app.template.patches.bplace.WebViewClientFingerprint

private const val EXTENSION_CLASS = "Lapp/template/extension/ScriptHook;"

val onPageStartedPatch = bytecodePatch(
    name = "Fix file picker camera prompt",
    description = "Strips the capture attribute from file inputs so the file picker doesn't request camera permission.",
    default = true
) {
    compatibleWith() // fill in package/version
    extendWith("extensions/extension.mpe")

    execute {
        val method = OnPageStartedFingerprint.method
        method.addInstructions(
            0,
            """
            move-object/from16 v0, p1
            invoke-static {v0}, Lapp/template/extension/ScriptHook;->hookWebView(Landroid/webkit/WebView;)V
            """.trimIndent()
        )
    }
}