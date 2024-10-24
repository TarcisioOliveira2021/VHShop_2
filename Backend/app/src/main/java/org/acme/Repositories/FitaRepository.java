package org.acme.Repositories;

import org.acme.Entities.Fita;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FitaRepository implements PanacheRepository<Fita> {
}
