package br.com.unipds;

import br.com.unipds.makdown.Makdown;
import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
public record Capitulo(String titulo, Makdown makdown, String html) {


}
