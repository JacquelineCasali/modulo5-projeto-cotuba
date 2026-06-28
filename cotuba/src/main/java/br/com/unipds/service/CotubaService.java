package br.com.unipds.service;

import br.com.unipds.Capitulo;
import br.com.unipds.ParametrosCotuba;

import br.com.unipds.ebook.EbookBuilder;
import br.com.unipds.ebook.FormatoEbookFilter;
import br.com.unipds.gerador.GeradorEbook;
import br.com.unipds.leitor.LeitorPropriedadesEbook;
import br.com.unipds.makdown.Makdown;
import br.com.unipds.makdown.RenderizadorMarkdown;
import br.com.unipds.makdown.RepositorioMarkdowns;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

import java.nio.file.Path;
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
        Path diretorioDosMD = parametrosCotuba.diretorioMD();

        List<Makdown> markdowns = repositorioMarkdowns.buscar(parametrosCotuba.diretorioMD());

        List<Capitulo> capitulos = renderizadorMarkdown.renderizar(markdowns);



        var propriedadesEbook = leitorPropriedadesEbook.ler(diretorioDosMD);
        var ebook = EbookBuilder.builder()
        .caitulo(capitulos)
        .formato(parametrosCotuba.formato())
        .arquivoSaida(parametrosCotuba.arquivoSaida())
        .titulo(propriedadesEbook.titulo())
        .autor(propriedadesEbook.autor())
                .build();

        GeradorEbook geradorEbook = geradoresEbook.select(FormatoEbookFilter.of(ebook.formato())).get();


        geradorEbook.gerar(ebook);
    }
}
