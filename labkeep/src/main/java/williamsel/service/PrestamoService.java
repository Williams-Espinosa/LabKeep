package williamsel.service;

import williamsel.model.Dispositivo;
import williamsel.model.Prestamo;
import williamsel.repository.DispositivoRepository;
import williamsel.repository.PrestamoRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PrestamoService {

    private final PrestamoRepository prestamoRepo;
    private final DispositivoRepository dispositivoRepo;

    public PrestamoService(PrestamoRepository prestamoRepo, DispositivoRepository dispositivoRepo) {
        this.prestamoRepo = prestamoRepo;
        this.dispositivoRepo = dispositivoRepo;
    }

    public List<Prestamo> listar() throws SQLException {
        return prestamoRepo.findAll();
    }

    public List<Prestamo> historialPorDispositivo(int dispositivoId) throws SQLException {
        return prestamoRepo.findByDispositivo(dispositivoId);
    }

    public Prestamo prestar(int dispositivoId, int usuarioId) throws SQLException {
        Optional<Dispositivo> dOpt = dispositivoRepo.findById(dispositivoId);
        if (dOpt.isEmpty()) throw new IllegalArgumentException("Dispositivo no encontrado");

        Dispositivo d = dOpt.get();
        if (!"disponible".equals(d.getEstado())) {
            throw new IllegalStateException("El dispositivo ya está prestado");
        }

        Prestamo p = new Prestamo();
        p.setDispositivoId(dispositivoId);
        p.setUsuarioId(usuarioId);

        int id = prestamoRepo.save(p);
        p.setId(id);
        dispositivoRepo.actualizarEstado(dispositivoId, "prestado");
        return p;
    }


    public boolean devolver(int dispositivoId) throws SQLException {
        Optional<Prestamo> pOpt = prestamoRepo.findPrestamoActivo(dispositivoId);
        if (pOpt.isEmpty()) throw new IllegalStateException("No hay préstamo activo para este dispositivo");

        boolean ok = prestamoRepo.devolver(pOpt.get().getId());
       if (ok) dispositivoRepo.actualizarEstado(dispositivoId, "disponible");
       return ok;
    }
}
