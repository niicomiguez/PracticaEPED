package es.uned.lsi.eped.pract2025_2026;

import es.uned.lsi.eped.DataStructures.IteratorIF;
import es.uned.lsi.eped.DataStructures.List;
import es.uned.lsi.eped.DataStructures.ListIF;
import es.uned.lsi.eped.DataStructures.SequenceIF;

// HECHA
public class Seq_PSF {
    /* Lista para almacenar los pares <cadena,frencuencia>*/
    private List<Pair_S_F> seqStringFreq;
    
    /* Constructor */
    public Seq_PSF() {
        this.seqStringFreq= new List<>();
    }

    /* Devuelve un iterador de la secuencia */
    public IteratorIF<Pair_S_F> iterator() {
        return this.seqStringFreq.iterator();
    }
    
    /* Añade un nuevo par <cadena,frecuencia> a la secuencia */
    public void add(Pair_S_F pair) {
        int pos = this.seqStringFreq.size() + 1;
        this.seqStringFreq.insert(pos, pair);
    }
}

