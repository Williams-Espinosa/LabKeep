package williamsel.service;


import williamsel.model.Categoria;
import williamsel.repository.CategoriaRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CategoriaService {

    private final CategoriaRepository repo;

    public CategoriaService(CategoriaRepository repo) {
        this.repo = repo;
    }

    public List<Categoria> listar() throws SQLException {
        return repo.findAll();
    }

    public Optional<Categoria> getById(int id) throws SQLException {
        return repo.findById(id);
    }

    public void crear(Categoria c) throws SQLException {
        if (c.getNombre() == null || c.getNombre().isBlank())
            throw new IllegalArgumentException("El nombre es obligatorio");
        repo.save(c);
    }

    public boolean actualizar(int id, Categoria c) throws SQLException {
        c.setId(id);
        return repo.update(c);
    }

    public boolean eliminar(int id) throws SQLException {
        return repo.delete(id);
    }
}
