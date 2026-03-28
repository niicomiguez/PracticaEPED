package es.uned.lsi.eped.pract2025_2026;

import es.uned.lsi.eped.DataStructures.*;

// POR HACER
public class IndexTree implements IndexIF {

    protected GTreeIF<Node> index;

    public IndexTree(){
        this.index = new GTree<Node>();
        Node contenidoRaiz = new NodeRoot();
        this.index.setRoot(contenidoRaiz);
    }

    @Override
    public Seq_PSF retrieveIndex(String p) {
        char[] letras= p.toCharArray();
        // Variable auxiliar para no perder la raíz actual
        GTreeIF<Node> nodoActual = this.index;
        for (char letra: letras){
            boolean encontrada=false;
            IteratorIF<GTreeIF<Node>> it= nodoActual.getChildren().iterator();
            while (it.hasNext() && !encontrada){
                GTreeIF<Node> hijo = it.getNext();
                Node contenido = hijo.getRoot();
                if (contenido.getNodeType()== Node.NodeType.INNER){
                    char letraNodo =((NodeInner) contenido).getLetter();
                    if (letraNodo==letra){
                        nodoActual=hijo;
                        encontrada=true;
                    }else if(letraNodo>letra){
                        return new Seq_PSF();
                    }
                }
            }
            if (!encontrada) {
                return new Seq_PSF();
            }
        }
        IteratorIF<GTreeIF<Node>> itHijos = nodoActual.getChildren().iterator();

        while (itHijos.hasNext()) {
            GTreeIF<Node> hijo = itHijos.getNext();
            Node contenido = hijo.getRoot();

            if (contenido.getNodeType() == Node.NodeType.INFO) {
                return ((NodeInfo) contenido).getSeqPSR();
            }
        }

        return new Seq_PSF();
    }

    @Override
    public void insertIndex(String p, String doc_id, int freq) {

    }

    @Override
    public IteratorIF<Pair_W_SeqPSF> prefixIterator(String prefix) {
        return null;
    }
}
