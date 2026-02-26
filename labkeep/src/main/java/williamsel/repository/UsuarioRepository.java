package williamsel.repository;

import williamsel.config.DBconfig;
import williamsel.model.Usuario;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

public class UsuarioRepository {

    private final DataSource dataSource;

    public UsuarioRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void registrarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (contrasena, correo) " +
                "VALUES (?, ?)";

        try (Connection conn = DBconfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getContrasena());
            stmt.setString(2, usuario.getCorreo());

            stmt.executeUpdate();
        }
    }

    public boolean verificarCorreo(String correo) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuario WHERE correo = ?";
        try (Connection conn = DBconfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, correo);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    public void updatePassword(String correo, String nuevaPasswordEncriptada) throws SQLException {
        String sql = "UPDATE usuario SET contrasena = ? WHERE correo = ?";
        try (Connection conn = DBconfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nuevaPasswordEncriptada);
            stmt.setString(2, correo);
            stmt.executeUpdate();
        }
    }public Optional<Usuario> buscarPorCorreo(String correo) throws SQLException {
        String sql = "SELECT id, correo, contrasena FROM usuario WHERE correo = ?";
        try (Connection conn = DBconfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, correo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id"));
                u.setCorreo(rs.getString("correo"));
                u.setContrasena(rs.getString("contrasena"));
                return Optional.of(u);
            }
        }
        return Optional.empty();
    }

}