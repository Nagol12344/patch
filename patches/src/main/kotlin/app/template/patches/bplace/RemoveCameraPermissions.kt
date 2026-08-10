package app.template.patches.bplace

import app.morphe.patcher.annotation.Patch
import app.morphe.patcher.patch.ResourcePatch
import app.morphe.patcher.data.ResourceContext
import org.w3c.dom.Document
import org.w3c.dom.Element

@Patch(
    name = "Remove Camera Requirements",
    description = "Strips camera permissions and features from the manifest."
)
class RemoveCameraPermissions : ResourcePatch() {

    override fun execute(context: ResourceContext) {
        // Correctly fetch the open AndroidManifest XML context via Morphe
        val manifestDoc: Document = context.manifest
        val root = manifestDoc.documentElement

        // 1. Remove <uses-permission android:name="android.permission.CAMERA"/>
        val permissions = root.getElementsByTagName("uses-permission")
        for (i in (permissions.length - 1) downTo 0) {
            val item = permissions.item(i) as Element
            if (item.getAttribute("android:name") == "android.permission.CAMERA") {
                root.removeChild(item)
            }
        }

        // 2. Remove <uses-feature android:name="android.hardware.camera"/>
        val features = root.getElementsByTagName("uses-feature")
        for (i in (features.length - 1) downTo 0) {
            val item = features.item(i) as Element
            if (item.getAttribute("android:name") == "android.hardware.camera") {
                root.removeChild(item)
            }
        }
    }
}
