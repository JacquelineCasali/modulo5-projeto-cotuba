package br.com.unipds.ebook;

import br.com.unipds.Capitulo;
import io.soabase.recordbuilder.core.RecordBuilder;

import java.nio.file.Path;
import java.util.List;

@RecordBuilder
public record Ebook( String titulo,
        String autor,
        FormatoEbook formato,
        List<Capitulo> caitulo,
        Path arquivoSaida) {


}
