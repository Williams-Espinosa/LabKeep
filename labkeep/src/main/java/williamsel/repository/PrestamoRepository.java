package williamsel.repository;


import williamsel.model.Prestamo;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PrestamoRepository {

    private final DataSource ds;

    public PrestamoRepository(DataSource ds) {
        this.ds = ds;
    }

    public List<Prestamo> findAll() throws SQLException {
        List<Prestamo> lista = new ArrayList<>();
        String sql = """
                SELECT p.*, d.nombre AS dispositivo_nombre, u.correo AS usuario_correo
                FROM prestamo p
                JOIN dispositivo d ON p.dispositivo_id = d.id
                JOIN usuario u ON p.usuario_id = u.id
                ORDER BY p.fecha_prestamo DESC
                """;
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public List<Prestamo> findByDispositivo(int dispositivoId) throws SQLException {
        List<Prestamo> lista = new ArrayList<>();
        String sql = """
                SELECT p.*, d.nombre AS dispositivo_nombre, u.correo AS usuario_correo
                FROM prestamo p
                JOIN dispositivo d ON p.dispositivo_id = d.id
                JOIN usuario u ON p.usuario_id = u.id
                WHERE p.dispositivo_id = ?
                ORDER BY p.fecha_prestamo DESC
                """;
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, dispositivoId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public Optional<Prestamo> findPrestamoActivo(int dispositivoId) throws SQLException {
        String sql = """
                SELECT p.*, d.nombre AS dispositivo_nombre, u.correo AS usuario_correo
                FROM prestamo p
                JOIN dispositivo d ON p.dispositivo_id = d.id
                JOIN usuario u ON p.usuario_id = u.id
                WHERE p.dispositivo_id = ? AND p.fecha_devolucion IS NULL
                """;
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, dispositivoId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(mapear(rs));
        }
        return Optional.empty();
    }

    public int save(Prestamo p) throws SQLException {
        String sql = "INSERT INTO prestamo (dispositivo_id, usuario_id) VALUES (?, ?) RETURNING id";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getDispositivoId());
            ps.setInt(2, p.getUsuarioId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("id");
        }
        return -1;
    }

    public boolean devolver(int prestamoId) throws SQLException {
        String sql = "UPDATE prestamo SET fecha_devolucion = CURRENT_TIMESTAMP WHERE id = ? AND fecha_devolucion IS NULL";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, prestamoId);
            return ps.executeUpdate() > 0;
        }
    }

    private Prestamo mapear(ResultSet rs) throws SQLException {
        Prestamo p = new Prestamo();
        p.setId(rs.getInt("id"));
        p.setDispositivoId(rs.getInt("dispositivo_id"));
        p.setUsuarioId(rs.getInt("usuario_id"));
        p.setDispositivoNombre(rs.getString("dispositivo_nombre"));
        p.setUsuarioCorreo(rs.getString("usuario_correo"));

        Timestamp fp = rs.getTimestamp("fecha_prestamo");
        if (fp != null) p.setFechaPrestamo(fp.toLocalDateTime());

        Timestamp fd = rs.getTimestamp("fecha_devolucion");
        if (fd != null) p.setFechaDevolucion(fd.toLocalDateTime());

        return p;
    }
}
