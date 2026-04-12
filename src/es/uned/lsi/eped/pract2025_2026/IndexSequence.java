package es.uned.lsi.eped.pract2025_2026;

import es.uned.lsi.eped.DataStructures.IteratorIF;
import es.uned.lsi.eped.DataStructures.List;
import es.uned.lsi.eped.DataStructures.ListIF;

// HECHA
public class IndexSequence implements IndexIF {
    protected ListIF<Pair_W_SeqPSF> index;

    public IndexSequence(){
        this.index=new List<Pair_W_SeqPSF>();
    }

    // Lista de pares de palabra lugar (capitulo02,12)
    @Override
    public Seq_PSF retrieveIndex(String p) {
        IteratorIF<Pair_W_SeqPSF> it = index.iterator();
        while (it.hasNext()){
            Pair_W_SeqPSF actual=it.getNext();
            if (actual.getWord().equals(p)){
                return actual.getSeqPSR();
            }
            // Si actual es mayor alfabéticamente que p entonces break porque está ordenado alfabéeticamente
            if(actual.getWord().compareTo(p)>0){
                break;
            }
        }
        return new Seq_PSF();
    }

    // Insertar índice
    @Override
    public void insertIndex(String p, String doc_id, int freq) {
        IteratorIF<Pair_W_SeqPSF> it = index.iterator();
        int pos = 1;
        boolean encontrada = false;
        boolean insertada = false;

        while (it.hasNext() && !encontrada && !insertada) {
            Pair_W_SeqPSF actual = it.getNext();
            int comparacion = actual.getWord().compareTo(p);

            if (comparacion == 0) {
                /* Caso A: La palabra ya existe, actualizamos su secuencia */
                actual.add(doc_id, freq);
                encontrada = true;
            } else if (comparacion > 0) {
                /* Caso B: Hemos pasado el lugar donde debería estar p */
                Pair_W_SeqPSF nuevo = new Pair_W_SeqPSF(p);
                nuevo.add(doc_id, freq);
                index.insert(pos,nuevo);
                insertada = true;
            }
            pos++;
        }

        /* Caso C: La palabra es mayor que todas las de la lista o la lista está vacía */
        if (!encontrada && !insertada) {
            Pair_W_SeqPSF nuevo = new Pair_W_SeqPSF(p);
            nuevo.add(doc_id, freq);
            index.insert(index.size() + 1,nuevo );
        }
    }

    // Devuelve iterador de la lista
    @Override
    public IteratorIF<Pair_W_SeqPSF> prefixIterator(String prefix) {
        /* 1. Creamos la lista donde guardaremos los resultados */
        ListIF<Pair_W_SeqPSF> listaResultados = new List<Pair_W_SeqPSF>();
        int posResultados = 1;

        /* 2. Obtenemos el iterador del índice principal */
        IteratorIF<Pair_W_SeqPSF> it = index.iterator();

        while (it.hasNext()) {
            Pair_W_SeqPSF actual = it.getNext();
            String palabra = actual.getWord();

            /* 3. Comprobamos si la palabra empieza por el prefijo */
            if (palabra.startsWith(prefix)) {
                listaResultados.insert(posResultados,actual );
                posResultados++;
            } else if (palabra.compareTo(prefix) > 0) {
            /* 4. Optimización: si la palabra ya es mayor que el prefijo
               y no empieza por él, no habrá más coincidencias después */
                break;
            }
        }

        return listaResultados.iterator();
    }
}
