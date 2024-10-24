package org.acme.Security;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.acme.Entities.Usuario;
import org.acme.Services.AuthenticationService;
import org.acme.Services.UsuarioService;

@Path("/login")
public class AuthenticationResource {

    private AuthenticationService service;
    private UsuarioService serviceUsuario;

    public AuthenticationResource(AuthenticationService serviceC, UsuarioService serviceUsuarioC) {
        service = serviceC;
        serviceUsuario = serviceUsuarioC;
    }

    @POST()
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/autenticar")
    public Response logar(Usuario usuario) {

        String login = usuario.getNome();
        String senha = usuario.getSenha();

        boolean autenticado = service.autenticado(login, senha);

        if (autenticado) {
            String roleUsuario = serviceUsuario.getRole(login);
            return Response.ok(roleUsuario).build();
        }

        return Response.status(404).tag("Usuário não encontrado, informe novamente seu login ou senha.").build();
    }

}