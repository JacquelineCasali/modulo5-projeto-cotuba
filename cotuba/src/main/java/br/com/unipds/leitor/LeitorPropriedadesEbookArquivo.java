package br.com.unipds.leitor;

import br.com.unipds.ebook.EbookBuilder;
import br.com.unipds.ebook.PropriedadesEbook;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

@ApplicationScoped
public class LeitorPropriedadesEbookArquivo implements LeitorPropriedadesEbook {

    @Override
    public PropriedadesEbook ler(Path diretorioMD) {
        Path arquivoPropriedades = diretorioMD.resolve("ebook.properties");
        if (!Files.exists(arquivoPropriedades)) {
            throw new IllegalStateException("Arquivo ebook.properties não encontrada");
        }
        Properties properties = new Properties();
        try (Reader reader = Files.newBufferedReader(arquivoPropriedades, StandardCharsets.UTF_8)) {
            properties.load(reader);
        } catch (IOException ex) {
            throw new IllegalStateException("Erro ao ler arquivo " + arquivoPropriedades, ex);
        }

        String propriedadeTitulo = "cotuba.ebook.titulo";
        String titulo = properties.getProperty(propriedadeTitulo);
        validarPropriedade(titulo, propriedadeTitulo);


        String propriedadeAutor = "cotuba.ebook.autor";

        String autor = properties.getProperty(propriedadeAutor);
        validarPropriedade(autor, propriedadeAutor);

       return  new PropriedadesEbook(titulo,autor);
    }

    private void validarPropriedade(String valor, String propriedade) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("Propriedade invalida:" + propriedade);
        }

    }
}
