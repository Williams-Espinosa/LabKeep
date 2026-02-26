package williamsel.service;

import io.javalin.http.UploadedFile;
import williamsel.model.Dispositivo;
import williamsel.repository.DispositivoRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DispositivoService {

    private final DispositivoRepository repo;
    private final ClouudinaryService cloudinary;

    public DispositivoService(DispositivoRepository repo, ClouudinaryService cloudinary) {
        this.repo = repo;
        this.cloudinary = cloudinary;
    }

    public List<Dispositivo> listar(String query) throws SQLException {
        if (query != null && !query.isBlank()) {
            return repo.findByNombreOrCategoria(query);
        }
        return repo.findAll();
    }

    public Optional<Dispositivo> getById(int id) throws SQLException {
        return repo.findById(id);
    }

    public Dispositivo crear(String nombre, int categoriaId, UploadedFile imagen) throws Exception {
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("El nombre es obligatorio");

        Dispositivo d = new Dispositivo();
        d.setNombre(nombre);
        d.setCategoriaId(categoriaId);
        d.setEstado("disponible");

        if (imagen != null) {
            Map<String, String> result = cloudinary.subirImagen(imagen);
            d.setImagenUrl(result.get("url"));
            d.setImagenPublicId(result.get("public_id"));
        }

        int id = repo.save(d);
        d.setId(id);
        return d;
    }

    public boolean actualizar(int id, Dispositivo d) throws SQLException {
        d.setId(id);
        return repo.update(d);
    }

    public String actualizarImagen(int id, UploadedFile imagen) throws Exception {
        Optional<Dispositivo> opt = repo.findById(id);
        opt.ifPresent(d -> {
            if (d.getImagenPublicId() != null) {
                cloudinary.eliminarImagen(d.getImagenPublicId());
            }
        });

        Map<String, String> result = cloudinary.subirImagen(imagen);
        repo.actualizarImagen(id, result.get("url"), result.get("public_id"));
        return result.get("url");
    }

   public boolean cambiarEstado(int id, String estado) throws SQLException {
        if (!estado.equals("disponible") && !estado.equals("prestado"))
            throw new IllegalArgumentException("Estado inválido: usa 'disponible' o 'prestado'");
        return repo.actualizarEstado(id, estado);
    }

    public boolean eliminar(int id) throws SQLException {
        Optional<Dispositivo> opt = repo.findById(id);
        opt.ifPresent(d -> {
            if (d.getImagenPublicId() != null) {
                cloudinary.eliminarImagen(d.getImagenPublicId());
            }
        });
        return repo.delete(id);
    }
}
