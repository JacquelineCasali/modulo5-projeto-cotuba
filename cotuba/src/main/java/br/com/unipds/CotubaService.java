package br.com.unipds;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class CotubaService {

    private final RenderizadorMarkdown renderizadorMarkdown;
    private final LeitorPropriedadesEbook leitorPropriedadesEbook;
    private final RepositorioMarkdowns repositorioMarkdowns;
    private final Instance<GeradorEbook> geradoresEbook;



    @Inject
    public CotubaService(RenderizadorMarkdown renderizadorMarkdown, LeitorPropriedadesEbook leitorPropriedadesEbook,
                         RepositorioMarkdowns repositorioMarkdowns,
                        @Any Instance<GeradorEbook> geradoresEbook) {
        this.renderizadorMarkdown = renderizadorMarkdown;
        this.leitorPropriedadesEbook = leitorPropriedadesEbook;
        this.repositorioMarkdowns = repositorioMarkdowns;


        this.geradoresEbook = geradoresEbook;
    }


    public void executar(ParametrosCotuba parametrosCotuba) {

        List<Capitulo> capitulos = repositorioMarkdowns.buscar(parametrosCotuba.getDiretorioMD());


        renderizadorMarkdown.renderizar(capitulos);

        var ebook = new Ebook();


        leitorPropriedadesEbook.ler(parametrosCotuba.getDiretorioMD(), ebook);
        ebook.setCapitulo(capitulos);
        ebook.setFormato(parametrosCotuba.getFormato());
        ebook.setArquivoSaida(parametrosCotuba.getArquivoSaida());

        GeradorEbook geradorEbook=geradoresEbook.select(FormatoEbookFilter.of(ebook.getFormato())).get();

        geradorEbook.gerar(ebook);
    }
}
