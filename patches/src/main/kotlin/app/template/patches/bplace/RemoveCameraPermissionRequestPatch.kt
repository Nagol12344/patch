package app.template.patches.bplace

import app.morphe.patcher.extensions.InstructionExtensions.replaceInstruction
import app.morphe.patcher.patch.bytecodePatch

val removeCameraPermissionRequestPatch = bytecodePatch(
    name = "Remove unnecessary camera permission request",
    description = "Prevents the file picker from proactively requesting CAMERA permission.",
    default = true
) {
    compatibleWith(Compatibility(
            name = "Better Place",
            packageName = "com.bplace",
            appIconColor = 0x2196f2,
            targets = listOf(AppTarget(null), AppTarget("1.0.7"))
        ))

    execute {
        val method = OnShowFileChooserFingerprint.method
        val targetIndex = method.instructions.indexOfFirst {
            it.toString().contains("LA5/k;->z:Z")
        }
        require(targetIndex != -1) { "Could not find LA5/k;->z:Z instruction" }

        method.replaceInstruction(targetIndex, "const/4 p3, 0x0")
    }
}