package br.com.unipds.estatisticas;

import java.util.*;

class ContadorPalavras implements Iterable<ContadorPalavras.ContagemPalavra>{
    record  ContagemPalavra(String palavra,int ocorrencias){}



    private Map<String, Integer> mapa = new TreeMap<>();

    public void adicionarPalavra(String palavra) {
        mapa.merge(palavra, 1, Integer::sum);
    }
public Iterable<? extends Map.Entry<String ,Integer>> entrySet(){
        return mapa.entrySet();
}

    @Override
    public Iterator<ContagemPalavra> iterator() {
        Iterator<Map.Entry<String,Integer>> iterator =mapa.entrySet().iterator();

        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public ContagemPalavra next() {
                Map.Entry<String,Integer> entry =iterator.next();

                return new ContagemPalavra(entry.getKey(),entry.getValue());
            }
        };
    }

}