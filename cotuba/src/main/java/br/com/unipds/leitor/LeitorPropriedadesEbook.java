package br.com.unipds.leitor;


import br.com.unipds.ebook.PropriedadesEbook;

import java.nio.file.Path;

public interface LeitorPropriedadesEbook {
    PropriedadesEbook ler(Path diretorioMD);
}
