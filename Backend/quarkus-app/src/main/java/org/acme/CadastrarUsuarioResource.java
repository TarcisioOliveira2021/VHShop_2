package org.acme;

import org.acme.Entities.Usuario;
import org.acme.Services.UsuarioService;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cadastrarUsuario")
public class CadastrarUsuarioResource {

    UsuarioService service;

    public CadastrarUsuarioResource(UsuarioService serviceC) {
        service = serviceC;
    }

    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cadastrarUsuario(Usuario novoUsuario) {
        try {
            service.cadastrar(novoUsuario);
            return Response.ok().build();

        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}