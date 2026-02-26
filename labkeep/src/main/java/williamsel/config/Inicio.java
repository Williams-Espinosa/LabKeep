package williamsel.config;

import javax.sql.DataSource;

import williamsel.controller.CategoriaController;
import williamsel.controller.DispositivoController;
import williamsel.controller.PrestamoController;
import williamsel.controller.UsuarioController;
import williamsel.repository.CategoriaRepository;
import williamsel.repository.DispositivoRepository;
import williamsel.repository.PrestamoRepository;
import williamsel.repository.UsuarioRepository;
import williamsel.routers.RouteCategoria;
import williamsel.routers.RoutesDispositivo;
import williamsel.routers.RoutesPrestamos;
import williamsel.routers.RoutesUsuario;
import williamsel.service.*;

public class Inicio {

    private final DataSource dataSource;

    public Inicio(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public RoutesUsuario inicioUsuario() {
        UsuarioRepository usuarioRepository = new UsuarioRepository(this.dataSource);
        UsuarioService usuarioService = new UsuarioService(usuarioRepository);
        UsuarioController usuarioController = new UsuarioController(usuarioService);
        return new RoutesUsuario(usuarioController);
    }

    public RouteCategoria inicioCategoria() {
        CategoriaRepository repo = new CategoriaRepository(dataSource);
        CategoriaService service = new CategoriaService(repo);
        CategoriaController controller = new CategoriaController(service);
        return new RouteCategoria(controller);
    }

    public RoutesDispositivo inicioDispositivo() {
        DispositivoRepository repo = new DispositivoRepository(dataSource);
        ClouudinaryService cloudinary = new ClouudinaryService();
        DispositivoService service = new DispositivoService(repo, cloudinary);
        DispositivoController controller = new DispositivoController(service);
        return new RoutesDispositivo(controller);
    }

    public RoutesPrestamos inicioPrestamo() {
        PrestamoRepository prestamoRepo = new PrestamoRepository(dataSource);
        DispositivoRepository dispositivoRepo = new DispositivoRepository(dataSource);
        PrestamoService service = new PrestamoService(prestamoRepo, dispositivoRepo);
        PrestamoController controller = new PrestamoController(service);
        return new RoutesPrestamos(controller);
    }
}