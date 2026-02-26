package williamsel.model;

import java.time.LocalDateTime;

public class Prestamo {
    private int id;
    private int dispositivoId;
    private int usuarioId;
    private LocalDateTime fechaPrestamo;
    private LocalDateTime fechaDevolucion;

    private String dispositivoNombre;
    private String usuarioCorreo;

    public Prestamo() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getDispositivoId() { return dispositivoId; }
    public void setDispositivoId(int dispositivoId) { this.dispositivoId = dispositivoId; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public LocalDateTime getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDateTime fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }

    public LocalDateTime getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(LocalDateTime fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }

    public String getDispositivoNombre() { return dispositivoNombre; }
    public void setDispositivoNombre(String dispositivoNombre) { this.dispositivoNombre = dispositivoNombre; }

    public String getUsuarioCorreo() { return usuarioCorreo; }
    public void setUsuarioCorreo(String usuarioCorreo) { this.usuarioCorreo = usuarioCorreo; }
}

