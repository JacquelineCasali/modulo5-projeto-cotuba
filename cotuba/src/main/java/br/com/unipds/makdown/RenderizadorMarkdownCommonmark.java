package br.com.unipds.makdown;

import br.com.unipds.Capitulo;
import br.com.unipds.CapituloBuilder;

import jakarta.enterprise.context.ApplicationScoped;
import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Heading;
import org.commonmark.node.Node;
import org.commonmark.node.Text;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.List;

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
                throw new IllegalStateException("Erro ao fazer parse do arquivo " + makdown.arquivo(), ex);
            }

            try {
                HtmlRenderer renderer = HtmlRenderer.builder().build();
                String html = renderer.render(document);
                capituloBuilder.html(html);

            } catch (Exception ex) {
                throw new IllegalStateException("Erro ao renderizar para HTML o arquivo " + makdown.arquivo(), ex);
            }
            return capituloBuilder.build();
        }).toList();


    }
}