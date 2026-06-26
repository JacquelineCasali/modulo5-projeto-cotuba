package br.com.unipds;

import java.nio.file.Path;
import java.util.List;

public class Ebook {
    private String titulo;
    private String autor;
    private FormatoEbook formato;
    private List<Capitulo> capitulo;
    private Path arquivoSaida;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public FormatoEbook getFormato() {
        return formato;
    }

    public void setFormato(FormatoEbook formato) {
        this.formato = formato;
    }

    public List<Capitulo> getCapitulo() {
        return capitulo;
    }

    public void setCapitulo(List<Capitulo> capitulo) {
        this.capitulo = capitulo;
    }

    public Path getArquivoSaida() {
        return arquivoSaida;
    }

    public void setArquivoSaida(Path arquivoSaida) {
        this.arquivoSaida = arquivoSaida;
    }
}
