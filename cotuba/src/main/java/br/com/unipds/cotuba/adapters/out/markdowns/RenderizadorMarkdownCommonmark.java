package br.com.unipds.cotuba.adapters.out.markdowns;

import br.com.unipds.cotuba.domain.Capitulo;
import br.com.unipds.cotuba.domain.CapituloBuilder;
import br.com.unipds.cotuba.domain.Makdown;
import br.com.unipds.cotuba.plugin.CotubaPlugin;
import br.com.unipds.cotuba.ports.out.RenderizadorMarkdown;


import jakarta.enterprise.context.ApplicationScoped;
import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Heading;
import org.commonmark.node.Node;
import org.commonmark.node.Text;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.List;
import java.util.ServiceLoader;

@ApplicationScoped
public class RenderizadorMarkdownCommonmark implements RenderizadorMarkdown {

    @Override
    public List<Capitulo> renderizar(List<Makdown> makdowns) {

        return makdowns.stream().map(makdown -> {

            var capituloBuilder = CapituloBuilder.builder();
            capituloBuilder.makdown(makdown);
            Parser parser = Parser.builder().build();
            Node document = null;
            try {
                document = parser.parse(makdown.conteudo());
                document.accept(new AbstractVisitor() {
                    @Override
                    public void visit(Heading heading) {
                        if (heading.getLevel() == 1) {
                            // capítulo
                            String tituloDoCapitulo = ((Text) heading.getFirstChild()).getLiteral();
                            capituloBuilder.titulo(tituloDoCapitulo);
                        } else if (heading.getLevel() == 2) {
                            // seção
                        } else if (heading.getLevel() == 3) {
                            // título
                        }
                    }

                });
            } catch (Exception ex) {
                throw new IllegalStateException("Erro ao fazer parse do arquivo " + makdown.nome(), ex);
            }

            try {
                HtmlRenderer renderer = HtmlRenderer.builder().build();
                String html = renderer.render(document);
                //plugin

                for (CotubaPlugin plugin : ServiceLoader.load(CotubaPlugin.class)) {
                    String htmlProcessado = plugin.aposRenderizacao(html);
                    if (htmlProcessado != null && !htmlProcessado.isBlank()) {
                        html = htmlProcessado;
                    }
                }

                capituloBuilder.html(html);

            } catch (Exception ex) {
                throw new IllegalStateException("Erro ao renderizar para HTML o arquivo " + makdown.nome(), ex);
            }
            return capituloBuilder.build();
        }).toList();


    }
}