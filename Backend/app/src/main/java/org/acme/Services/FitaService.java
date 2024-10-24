package org.acme.Services;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Default;
import jakarta.transaction.Transactional;

import org.acme.Entities.Fita;
import org.acme.Repositories.FitaRepository;

import java.util.List;

@RequestScoped
@Default
public class FitaService {

    private FitaRepository repository = new FitaRepository();

    public FitaService(FitaRepository _repository) {
        repository = _repository;
    }

    @Transactional
    public void Cadastrar(Fita novafita) throws Exception {

        var fitasRetornadas = ObterTodas();

        if (!fitasRetornadas.isEmpty()) {
            for (Fita f : fitasRetornadas) {
                if (f.getAno().equals(novafita.getAno()))
                    throw new Exception("Fita já cadastrada.");
            }
        }

        repository.persist(novafita);
    }

    @Transactional
    public List<Fita> ObterTodas() {
        return repository.listAll();
    }

    @Transactional
    public void Deletar(long idFita) {
        repository.deleteById(idFita);
    }

    @Transactional
    public void Update(Fita fita) {

        var fita_Antiga = repository.findById(fita.getId());
        fita_Antiga.nome = fita.nome;
        fita_Antiga.genero = fita.genero;
        fita_Antiga.ano = fita.ano;

        repository.persist(fita_Antiga);
    }

}
