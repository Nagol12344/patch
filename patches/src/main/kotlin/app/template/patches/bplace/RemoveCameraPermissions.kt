import app.morphe.patcher.annotation.Patch
import app.morphe.patcher.resource.ResourcePatch
import app.morphe.patcher.resource.ResourceContext
import org.w3c.dom.Document
import org.w3c.dom.Element

@Patch(
    name = "Remove Camera Requirements",
    description = "Strips camera permissions and features from the manifest."
)
class StripCameraPatch : ResourcePatch() {

    override fun execute(context: ResourceContext) {
        // Morphe exposes the parsed XML Document of the AndroidManifest
        val manifestDoc: Document = context.manifest
        val root = manifestDoc.documentElement

        // 1. Remove the <uses-permission android:name="android.permission.CAMERA"/>
        val permissions = root.getElementsByTagName("uses-permission")
        for (i in 0 until permissions.length) {
            val item = permissions.item(i) as Element
            if (item.getAttribute("android:name") == "android.permission.CAMERA") {
                root.removeChild(item)
            }
        }

        // 2. Remove <uses-feature android:name="android.hardware.camera"/>
        val features = root.getElementsByTagName("uses-feature")
        for (i in 0 until features.length) {
            val item = features.item(i) as Element
            if (item.getAttribute("android:name") == "android.hardware.camera") {
                root.removeChild(item)
            }
        }
    }
}
