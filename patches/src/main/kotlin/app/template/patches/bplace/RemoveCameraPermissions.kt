package app.template.patches.bplace

import app.morphe.patcher.patch.ResourcePatch
import app.morphe.patcher.context.PatcherContext
import org.w3c.dom.Document
import org.w3c.dom.Element

// Declare your patch variables directly inside an object initialization block
object RemoveCameraPermissions {
    val patch = ResourcePatch(
        name = "Remove Camera Requirements",
        description = "Strips camera permissions and features from the manifest.",
        default = true,
        executeBlock = { context ->
            // In Morphe, ResourcePatch context exposes the AndroidManifest DOM structure safely
            val manifestDoc: Document = context.manifest
            val root = manifestDoc.documentElement

            // 1. Remove the camera permission node safely by iterating down to 0
            val permissions = root.getElementsByTagName("uses-permission")
            for (i in (permissions.length - 1) downTo 0) {
                val item = permissions.item(i) as Element
                if (item.getAttribute("android:name") == "android.permission.CAMERA") {
                    root.removeChild(item)
                }
            }

            // 2. Remove the camera hardware requirement node safely by iterating down to 0
            val features = root.getElementsByTagName("uses-feature")
            for (i in (features.length - 1) downTo 0) {
                val item = features.item(i) as Element
                if (item.getAttribute("android:name") == "android.hardware.camera") {
                    root.removeChild(item)
                }
            }
        }
    )
}
