package br.com.borduchi.testegeneric.usuario;

import br.com.borduchi.testegeneric.usuario.cliente.Cliente;
import br.com.borduchi.testegeneric.usuario.component.save.SalvarUsuarioComponent;
import br.com.borduchi.testegeneric.usuario.funcionario.Funcionario;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Transactional
public abstract class UsuarioService<T extends GenericUsuario<T>> {

    private static final String USUARIO_NAO_ENCONTRADO = "USUÁRIO NÃO ENCONTRADO";

    private final UsuarioRepositoryJpa<T> usuarioRepository;
    private final SalvarUsuarioComponent<T> salvarUsuarioComponent;

    protected UsuarioService(UsuarioRepositoryJpa<T> usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.salvarUsuarioComponent = new SalvarUsuarioComponent<T>(usuarioRepository) {
        };
    }

    public Page<T> findPage(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    public T save(T novoUsuario) {
        salvarUsuarioComponent.process(novoUsuario);
        T usuario = novoUsuario.create();
        return usuarioRepository.save(usuario);
    }

    public T update(UUID id, T usuario) {
        T usuarioSalvo = findById(id);
        usuarioSalvo.update(usuario);
        return usuarioRepository.save(usuarioSalvo);
    }

    public void delete(UUID id) {
        findById(id);
        usuarioRepository.deleteById(id);
    }

    public T findById(UUID id) {
        Optional<T> usuarioOptional = usuarioRepository.findById(id);
        return usuarioOptional.orElseThrow(() -> new DataIntegrityViolationException(USUARIO_NAO_ENCONTRADO));
    }

}
