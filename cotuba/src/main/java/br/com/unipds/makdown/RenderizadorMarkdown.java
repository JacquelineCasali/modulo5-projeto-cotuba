package br.com.unipds.makdown;


import br.com.unipds.Capitulo;

import java.util.List;

public interface RenderizadorMarkdown {
   List<Capitulo> renderizar(List<Makdown> makdowns);
}
