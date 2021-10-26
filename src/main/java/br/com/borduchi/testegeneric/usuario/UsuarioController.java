package br.com.borduchi.testegeneric.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

public abstract class UsuarioController<T extends GenericUsuario<T>> {

    private final UsuarioService<T> usuarioService;

    protected UsuarioController(UsuarioRepositoryJpa<T> usuarioRepositoryJpa) {
        this.usuarioService = new UsuarioService<T>(usuarioRepositoryJpa) {};
    }

    @GetMapping
    public Page<T> findPage(Pageable pageable) {
        return usuarioService.findPage(pageable);
    }

    @GetMapping("/{id}")
    public T findByid(@PathVariable UUID id) {
        return usuarioService.findById(id);
    }

    @PostMapping
    public T save(@RequestBody T usuario) {
        return usuarioService.save(usuario);
    }

    @PutMapping("/{id}")
    public T update(@PathVariable UUID id, @RequestBody T usuario) {
        return usuarioService.update(id, usuario);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        usuarioService.delete(id);
    }

}
