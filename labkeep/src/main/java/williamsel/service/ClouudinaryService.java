package williamsel.service;

import io.javalin.http.UploadedFile;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;


import java.io.InputStream;
import java.util.Map;

public class ClouudinaryService {
    private final Cloudinary cloudinary;

    public ClouudinaryService() {
        Dotenv dotenv = Dotenv.load();
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", dotenv.get("CLOUDINARY_CLOUD_NAME"),
                "api_key",    dotenv.get("CLOUDINARY_API_KEY"),
                "api_secret", dotenv.get("CLOUDINARY_API_SECRET"),
                "secure",     true
        ));
    }

    public Map<String, String> subirImagen(UploadedFile archivo) throws Exception {
        try (InputStream is = archivo.content()) {
            byte[] bytes = is.readAllBytes();

            Map resultado = cloudinary.uploader().upload(bytes, ObjectUtils.asMap(
                    "folder", "labkeep/dispositivos",
                    "resource_type", "image"
            ));

            String url      = (String) resultado.get("secure_url");
            String publicId = (String) resultado.get("public_id");

            System.out.println("Imagen subida a Cloudinary: " + url);
            return Map.of("url", url, "public_id", publicId);
        }
    }

    public void eliminarImagen(String publicId) {
        if (publicId == null || publicId.isEmpty()) return;
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            System.out.println("Imagen eliminada de Cloudinary: " + publicId);
        } catch (Exception e) {
            System.err.println("Error al eliminar imagen de Cloudinary: " + e.getMessage());
        }
    }
}
