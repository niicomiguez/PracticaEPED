package es.uned.lsi.eped.pract2025_2026;


import es.uned.lsi.eped.DataStructures.IteratorIF;

/* Un objeto de esta clase permite contabilizar la frecuencia
 * de las palabras de un documento.
 */
public class ProcessDoc {
    String doc_id;
    Seq_PSF words;

    /* Constructor por defecto crea una secuencia vacía */
    public ProcessDoc(String did) {
        this.doc_id = did;
        this.words = new Seq_PSF();
    }

    /* Devuelve la secuencia de pares <palabra,frecuencia> */
    public Seq_PSF getSequence() {
        return this.words;
    }

    /* Incrementa en 1 la frecuencia de la palabra w en el documento si existe */
    public void addWord(String w) {
        IteratorIF<Pair_S_F> it = this.words.iterator();
        boolean encontrada = false;

        /* Buscamos la palabra en la secuencia actual del documento */
        while (it.hasNext() && !encontrada){
            Pair_S_F parActual = it.getNext();

            if (parActual.getString().equals(w)){
                parActual.incFrequency();
                encontrada = true;
            }
        }

        /* Si la palabra no existía en el documento, se crea un nuevo par con frecuencia 1 */
        if (!encontrada){
            Pair_S_F nuevoPar = new Pair_S_F(w, 1);
            this.words.add(nuevoPar);
        }
    }
}
