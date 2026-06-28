package br.com.unipds.cotuba.ports.out;



import br.com.unipds.cotuba.domain.Makdown;
import org.jmolecules.ddd.annotation.Repository;

import java.nio.file.Path;
import java.util.List;

@Repository
public interface RepositorioMarkdowns {
    List<Makdown> buscar(Path diretorioMD);
}
