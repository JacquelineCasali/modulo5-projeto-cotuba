package br.com.unipds;

import java.util.List;

public class CotubaService {

    public void executar(ParametrosCotuba parametrosCotuba) {


        var renderizadorMarkdown = new RenderizadorMarkdown();
        List<Capitulo> capitulos = renderizadorMarkdown.renderizar(parametrosCotuba.getDiretorioMD());

        var ebook = new Ebook();

        var leitorPropriedadesEbook= new LeitorPropriedadesEbook();
        leitorPropriedadesEbook.ler(parametrosCotuba.getDiretorioMD(),ebook);
        ebook.setCapitulo(capitulos);
        ebook.setFormato(parametrosCotuba.getFormato());
        ebook.setArquivoSaida(parametrosCotuba.getArquivoSaida());


        if (FormatoEbook.PDF.equals(ebook.getFormato())) {

            var gerarPDF = new GeradorPDF();
            gerarPDF.gerarPDF(ebook);

        } else if (FormatoEbook.EPUB.equals(ebook.getFormato())) {
            var geradorEPUB = new GeradorEPUB();
            geradorEPUB.gerarEPUB(ebook);
        } else {
            throw new IllegalArgumentException("Formato do ebook inválido: " + parametrosCotuba.getFormato());
        }
    }
}
