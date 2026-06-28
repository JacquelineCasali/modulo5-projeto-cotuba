package br.com.unipds.cotuba.domain;

import org.jmolecules.ddd.annotation.Entity;
import org.jmolecules.ddd.annotation.Identity;


@Entity
public record Makdown (@Identity String nome ,String conteudo) {


}
