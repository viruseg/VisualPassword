import fi.iki.elonen.NanoHTTPD
import android.content.res.AssetManager
import java.io.InputStream

class LocalWebServer(
    private val assetManager: AssetManager,
    port: Int
) : NanoHTTPD(port) {
    override fun serve(session: IHTTPSession): Response {
        val uri = session.uri.removePrefix("/") // "index.html"
        val fileName = if (uri.isEmpty()) "index.html" else uri
        return try {
            val input: InputStream = assetManager.open(fileName)
            newFixedLengthResponse(Response.Status.OK, "text/html", input, input.available().toLong())
        } catch (e: Exception) {
            newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain", "404 Not Found")
        }
    }
}
