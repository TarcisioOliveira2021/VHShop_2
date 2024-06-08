package org.acme.Services;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Default;

@RequestScoped
@Default
public class AuthenticationService {

    private UsuarioService service;

    public AuthenticationService(UsuarioService serviceC) {
        service = serviceC;
    }

    public Boolean autenticado(String login, String senha) {
        return service.autenticar(login, senha);
    }
}
