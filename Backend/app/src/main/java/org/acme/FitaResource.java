package org.acme;

import java.util.List;

import org.acme.Entities.Fita;
import org.acme.Services.FitaService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/fita")
public class FitaResource {

    private FitaService service;

    public FitaResource(FitaService serviceC) {
        service = serviceC;
    }

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Fita> list() {
        return service.ObterTodas();
    }

    @Path("/cadastrarfita")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cadastrarFita(Fita novaFita) {
        try {
            if (validarFita(novaFita)) {
                service.Cadastrar(novaFita);
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

        return Response.ok().build();
    }

    @Path("/delete")
    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    public Response deletarFita(String idFita) {
        try {
            service.Deletar(Long.parseLong(idFita));
            return Response.ok().build();

        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Path("/update")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateFita(Fita novaFita) {
        if (validarFita(novaFita)) {
            service.Update(novaFita);
            return Response.ok().build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("Erro ao salvar a fita. Verifique os campos e tente novamente").build();
    }

    private boolean validarFita(Fita fita) {
        return fita.nome != null && fita.ano != null && fita.genero != null;
    }

}
