package williamsel.repository;

import williamsel.model.Dispositivo;
import williamsel.repository.PrestamoRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DispositivoRepository {

    private final DataSource ds;

    public DispositivoRepository(DataSource ds) {
        this.ds = ds;
    }

    public List<Dispositivo> findAll() throws SQLException {
        List<Dispositivo> lista = new ArrayList<>();
        String sql = """
                SELECT d.*, c.nombre AS categoria_nombre
                FROM dispositivo d
                JOIN categoria c ON d.categoria_id = c.id
                ORDER BY d.fecha_creacion DESC
                """;
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public List<Dispositivo> findByNombreOrCategoria(String query) throws SQLException {
        List<Dispositivo> lista = new ArrayList<>();
        String sql = """
                SELECT d.*, c.nombre AS categoria_nombre
                FROM dispositivo d
                JOIN categoria c ON d.categoria_id = c.id
                WHERE LOWER(d.nombre) LIKE ? OR LOWER(c.nombre) LIKE ?
                ORDER BY d.fecha_creacion DESC
                """;
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String param = "%" + query.toLowerCase() + "%";
            ps.setString(1, param);
            ps.setString(2, param);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public Optional<Dispositivo> findById(int id) throws SQLException {
        String sql = """
                SELECT d.*, c.nombre AS categoria_nombre
                FROM dispositivo d
                JOIN categoria c ON d.categoria_id = c.id
                WHERE d.id = ?
                """;
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapear(rs));
            }
        }
        return Optional.empty();
    }

    public int save(Dispositivo d) throws SQLException {
        String sql = """
                INSERT INTO dispositivo (nombre, categoria_id, estado, imagen_url, imagen_public_id)
                VALUES (?, ?, ?, ?, ?)
                RETURNING id
                """;
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getNombre());
            ps.setInt(2, d.getCategoriaId());
            ps.setString(3, d.getEstado() != null ? d.getEstado() : "disponible");
            ps.setString(4, d.getImagenUrl());
            ps.setString(5, d.getImagenPublicId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("id");
        }
        return -1;
    }

    public boolean update(Dispositivo d) throws SQLException {
        String sql = """
                UPDATE dispositivo
                SET nombre = ?, categoria_id = ?, estado = ?
                WHERE id = ?
                """;
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getNombre());
            ps.setInt(2, d.getCategoriaId());
            ps.setString(3, d.getEstado());
            ps.setInt(4, d.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM dispositivo WHERE id = ?";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean actualizarEstado(int id, String estado) throws SQLException {
        String sql = "UPDATE dispositivo SET estado = ? WHERE id = ?";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean actualizarImagen(int id, String imagenUrl, String imagenPublicId) throws SQLException {
        String sql = "UPDATE dispositivo SET imagen_url = ?, imagen_public_id = ? WHERE id = ?";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, imagenUrl);
            ps.setString(2, imagenPublicId);
            ps.setInt(3, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Dispositivo mapear(ResultSet rs) throws SQLException {
        Dispositivo d = new Dispositivo();
        d.setId(rs.getInt("id"));
        d.setNombre(rs.getString("nombre"));
        d.setCategoriaId(rs.getInt("categoria_id"));
        d.setCategoriaNombre(rs.getString("categoria_nombre"));
        d.setEstado(rs.getString("estado"));
        d.setImagenUrl(rs.getString("imagen_url"));
        d.setImagenPublicId(rs.getString("imagen_public_id"));
        var ts = rs.getTimestamp("fecha_creacion");
        if (ts != null) d.setFechaCreacion(ts.toLocalDateTime());
        return d;
    }

}