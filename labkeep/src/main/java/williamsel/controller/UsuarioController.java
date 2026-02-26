package williamsel.controller;

import io.javalin.http.Context;
import williamsel.model.Usuario;
import williamsel.service.UsuarioService;
import org.mindrot.jbcrypt.BCrypt;
import java.util.Optional;

public class UsuarioController {
    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    public void registrar(Context ctx) {
        try {
            Usuario usuario = ctx.bodyAsClass(Usuario.class);

            if (usuario.getCorreo() == null || usuario.getCorreo().isEmpty()) {
                ctx.status(400).result("El correo es obligatorio.");
                return;
            }
            service.registrar(usuario);
            ctx.status(201).result("Usuario registrado exitosamente");
        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Error interno: " + e.getMessage());
        }
    }
    public static class LoginRequest {
        private String correo;
        private String contrasena;

        public String getCorreo() { return correo; }
        public void setCorreo(String correo) { this.correo = correo; }
        public String getContrasena() { return contrasena; }
        public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    }

    public void login(Context ctx) {
        try {
            LoginRequest loginRequest = ctx.bodyAsClass(LoginRequest.class);

            Optional<Usuario> usuarioOpt = service.obtenerPorCorreo(loginRequest.getCorreo());

            if (usuarioOpt.isPresent()) {
                Usuario u = usuarioOpt.get();
                if (BCrypt.checkpw(loginRequest.getContrasena(), u.getContrasena())) {
                    ctx.status(200).result("Login exitoso");
                    return;
                }
            }

            ctx.status(401).result("Correo o contraseña incorrectos");
        } catch (Exception e) {
            ctx.status(400).result("Formato de solicitud inválido: " + e.getMessage());
        }
    }
}