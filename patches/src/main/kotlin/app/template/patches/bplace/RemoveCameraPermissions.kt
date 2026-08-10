package app.template.patches.bplace

import app.morphe.patcher.patch.Patch
import app.morphe.patcher.context.PatchContext
import org.w3c.dom.Document
import org.w3c.dom.Element

object RemoveCameraPermissions {
    val patch = Patch<PatchContext<*>>(
        name = "Remove Camera Requirements",
        description = "Strips camera permissions and features from the manifest.",
        default = true,
        dependencies = emptySet(),
        compatibility = null,
        options = emptySet(),
        executeBlock = { context ->
            // Access the target manifest document from Morphe's Resource environment
            val manifestDoc: Document = context.resourceContext.manifest
            val root = manifestDoc.documentElement

            // 1. Remove the camera permission node safely by counting down to 0
            val permissions = root.getElementsByTagName("uses-permission")
            for (i in (permissions.length - 1) downTo 0) {
                val item = permissions.item(i) as Element
                if (item.getAttribute("android:name") == "android.permission.CAMERA") {
                    root.removeChild(item)
                }
            }

            // 2. Remove the camera hardware requirement node safely by counting down to 0
            val features = root.getElementsByTagName("uses-feature")
            for (i in (features.length - 1) downTo 0) {
                val item = features.item(i) as Element
                if (item.getAttribute("android:name") == "android.hardware.camera") {
                    root.removeChild(item)
                }
            }
        },
        finalizeBlock = null
    )
}
