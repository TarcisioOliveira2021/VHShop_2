package org.acme.Services;

import org.acme.Entities.Usuario;
import org.acme.Repositories.UsuarioRepository;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Default;
import jakarta.transaction.Transactional;

@RequestScoped
@Default
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public void cadastrar(Usuario usuario) throws Exception {
        Usuario novoUsuario = mapearNovoUsuario(usuario.getNome(), usuario.getSenha());
        var usuariosRetornados = usuarioRepository.find("nome", usuario.getNome()).list();

        if (!usuariosRetornados.isEmpty()) {
            for (Usuario u : usuariosRetornados) {
                if (u.getRole().equals(novoUsuario.getRole()) && u.getNome().equals(novoUsuario.getNome())) {
                    throw new Exception("Usuário já cadastrado.");
                }
            }
        }

        usuarioRepository.persist(novoUsuario);
    }

    private Usuario mapearNovoUsuario(String nome, String senha) {
        return new Usuario(nome, senha);
    }

    public Boolean autenticar(String login, String senha) {
        Usuario usuario = usuarioRepository.find("nome", login).firstResult();

        if (usuario == null)
            return false;

        return usuario.getSenha().equals(usuario.criptografarSenha(senha));
    }

    public String getRole(String login) {
        Usuario usuario = usuarioRepository.find("nome", login).firstResult();
        return usuario.getRole().toString();
    }
}
