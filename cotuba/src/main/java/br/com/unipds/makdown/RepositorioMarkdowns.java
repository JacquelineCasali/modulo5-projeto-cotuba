package br.com.unipds.makdown;

import br.com.unipds.Capitulo;

import java.nio.file.Path;
import java.util.List;

public interface RepositorioMarkdowns {
    List<Makdown> buscar(Path diretorioMD);
}
