package app.template.patches.bplace

import app.morphe.patcher.patch.Patch
import app.morphe.patcher.patch.ResourcePatchContext
import org.w3c.dom.Element

object RemoveCameraPermissions {
    val patch = Patch<ResourcePatchContext>(
        name = "Remove Camera Requirements",
        description = "Strips camera permissions and features from the manifest.",
        default = true,
        dependencies = emptySet(),
        compatibility = null,
        options = emptySet(),
        executeBlock = { context ->
            val manifest = context.manifest
            val root = manifest.documentElement

            // Remove CAMERA permission.
            val permissions = root.getElementsByTagName("uses-permission")
            for (i in (permissions.length - 1) downTo 0) {
                val element = permissions.item(i) as? Element ?: continue

                if (element.getAttribute("android:name") ==
                    "android.permission.CAMERA"
                ) {
                    root.removeChild(element)
                }
            }

            // Remove camera hardware requirements.
            val features = root.getElementsByTagName("uses-feature")
            for (i in (features.length - 1) downTo 0) {
                val element = features.item(i) as? Element ?: continue

                if (element.getAttribute("android:name") ==
                    "android.hardware.camera"
                ) {
                    root.removeChild(element)
                }
            }
        },
        finalizeBlock = null
    )
}
