package app.template.patches.bplace

import app.morphe.patcher.extensions.InstructionExtensions.getInstruction
import app.morphe.patcher.extensions.InstructionExtensions.replaceInstruction
import app.morphe.patcher.patch.bytecodePatch
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction

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
        OnShowFileChooserFingerprint.let {
            it.method.apply {
                val insertIndex = it.instructionMatches.first().index
                val register = getInstruction<OneRegisterInstruction>(insertIndex).registerA

                replaceInstruction(
                    insertIndex,
                    "const/4 v$register, 0x0"
                )
            }
        }
    }
}