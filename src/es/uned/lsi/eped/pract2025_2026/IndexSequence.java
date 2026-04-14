package es.uned.lsi.eped.pract2025_2026;

import es.uned.lsi.eped.DataStructures.IteratorIF;
import es.uned.lsi.eped.DataStructures.List;
import es.uned.lsi.eped.DataStructures.ListIF;

public class IndexSequence implements IndexIF {
    protected ListIF<Pair_W_SeqPSF> index;

    /* Constructor por defecto crea una lista vacía */
    public IndexSequence(){
        this.index=new List<Pair_W_SeqPSF>();
    }

    /* Devuelve una lista de pares de la palabra indicada por parámetro */
    @Override
    public Seq_PSF retrieveIndex(String p) {
        IteratorIF<Pair_W_SeqPSF> it = index.iterator();
        while (it.hasNext()){
            Pair_W_SeqPSF actual=it.getNext();
            if (actual.getWord().equals(p)){
                return actual.getSeqPSR();
            }
            /* Poda: al estar la lista ordenada, si la palabra actual
            es mayor que p, esta no existe */
            if(actual.getWord().compareTo(p)>0){
                break;
            }
        }
        return new Seq_PSF();
    }

    /* Inserta una palabra y su frecuencia en el índice manteniendo el orden alfabético */
    @Override
    public void insertIndex(String p, String doc_id, int freq) {
        IteratorIF<Pair_W_SeqPSF> it = index.iterator();
        int pos = 1;
        boolean encontrada = false;
        boolean insertada = false;

        while (it.hasNext() && !encontrada && !insertada) {
            Pair_W_SeqPSF actual = it.getNext();
            int comparacion = actual.getWord().compareTo(p);

            /* Si encontramos la palabra, actualizamos su frecuencia;
            si encontramos una mayor, insertamos en esta posición para
            mantener el orden alfabético */
            if (comparacion == 0) {
                actual.add(doc_id, freq);
                encontrada = true;
            } else if (comparacion > 0) {
                Pair_W_SeqPSF nuevo = new Pair_W_SeqPSF(p);
                nuevo.add(doc_id, freq);
                index.insert(pos,nuevo);
                insertada = true;
            }
            pos++;
        }

        /* Si no se encuentra ni ninguna mayor, se inserta en
        la última posición*/
        if (!encontrada && !insertada) {
            Pair_W_SeqPSF nuevo = new Pair_W_SeqPSF(p);
            nuevo.add(doc_id, freq);
            index.insert(index.size() + 1,nuevo );
        }
    }

    /* Devuelve iterador de la lista */
    @Override
    public IteratorIF<Pair_W_SeqPSF> prefixIterator(String prefix) {
        ListIF<Pair_W_SeqPSF> listaResultados = new List<Pair_W_SeqPSF>();
        int posResultados = 1;

        IteratorIF<Pair_W_SeqPSF> it = index.iterator();

        while (it.hasNext()) {
            Pair_W_SeqPSF actual = it.getNext();
            String palabra = actual.getWord();

            /* Si la palabra empieza por el prefijo se inserta el par
            actual en la lista de resultados con su posición correspondiente*/
            if (palabra.startsWith(prefix)) {
                listaResultados.insert(posResultados,actual );
                posResultados++;
            } else if (palabra.compareTo(prefix) > 0) {
            /* Poda: Si la palabra es mayor que el prefijo, este no existirá*/
                break;
            }
        }

        return listaResultados.iterator();
    }
}
