package williamsel.service;

import williamsel.model.Usuario;
import williamsel.repository.UsuarioRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.Optional;

public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public void registrar(Usuario usuario) throws SQLException {
        if (repository.verificarCorreo(usuario.getCorreo())) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado.");
        }

        if (usuario.getContrasena() != null) {
            String hash = BCrypt.hashpw(usuario.getContrasena(), BCrypt.gensalt());
            usuario.setContrasena(hash);
        }

        repository.registrarUsuario(usuario);
    }

    public void recuperarPassword(String correo, String nuevaPassword) throws SQLException {
        if (!repository.verificarCorreo(correo)) {
            throw new IllegalArgumentException("El correo proporcionado no está registrado.");
        }
        String hash = BCrypt.hashpw(nuevaPassword, BCrypt.gensalt());
        repository.updatePassword(correo, hash);
    }

    public Optional<Usuario> obtenerPorCorreo(String correo) throws SQLException {
        return repository.buscarPorCorreo(correo);
    }

}