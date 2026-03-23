package es.uned.lsi.eped.pract2025_2026;


import es.uned.lsi.eped.DataStructures.IteratorIF;

// POR HACER
/* Un objeto de esta clase permite contabilizar la frecuencia
 * de las palabras de un documento.
 */
public class ProcessDoc {
    String doc_id;
    Seq_PSF words;

    /* Constructor */
    public ProcessDoc(String did) {
        this.doc_id = did;
        this.words = new Seq_PSF();
    }

    /* Devuelve la secuencia de pares <palabra,frecuencia> */
    public Seq_PSF getSequence() {
        return this.words;
    }

    /* Incrementa en 1 la frecuencia de la palabra w en el documento */    
    public void addWord(String w) {
        /* Se recorren los pares de palabras con un Iterador */
        /* Se comparan las palabras con w. True -> incrementar : False -> añadir palabra*/
        IteratorIF<Pair_S_F> it = this.words.iterator();
        boolean encontrada=false;

        while (it.hasNext() && !encontrada){
            Pair_S_F parActual=it.getNext();

            if (parActual.getString().equals(w)){
                parActual.incFrequency();
                encontrada=true;
            }

            if (!encontrada){
                Pair_S_F nuevoPar=new Pair_S_F(w,1);
                this.words.add(nuevoPar);
            }
        }

    }
}
