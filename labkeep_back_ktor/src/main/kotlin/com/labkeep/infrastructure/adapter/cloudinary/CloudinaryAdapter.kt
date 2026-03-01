package com.labkeep.infrastructure.adapter.cloudinary

import com.cloudinary.Cloudinary
import com.labkeep.domain.port.output.ImagenResult
import com.labkeep.domain.port.output.ImagenStorage
import io.github.cdimascio.dotenv.dotenv

class CloudinaryAdapter : ImagenStorage {

    private val cloudinary: Cloudinary

    init {
        val env = dotenv { ignoreIfMissing = true }
        cloudinary = Cloudinary(
            mapOf(
                "cloud_name" to env["CLOUDINARY_CLOUD_NAME"],
                "api_key"    to env["CLOUDINARY_API_KEY"],
                "api_secret" to env["CLOUDINARY_API_SECRET"],
                "secure"     to true
            )
        )
    }

    override fun subir(bytes: ByteArray, nombreArchivo: String): ImagenResult {
        val result = cloudinary.uploader().upload(
            bytes,
            mapOf(
                "folder"        to "labkeep/dispositivos",
                "resource_type" to "image"
            )
        )
        return ImagenResult(
            url      = result["secure_url"] as String,
            publicId = result["public_id"]  as String
        )
    }

    override fun eliminar(publicId: String) {
        runCatching { cloudinary.uploader().destroy(publicId, emptyMap<String, Any>()) }
            .onFailure { println("Error eliminando imagen de Cloudinary: ${it.message}") }
    }
}
