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
        char[] letras= p.toCharArray();
        GTreeIF<Node> nodoActual = this.index;
        for (char letra : letras) {
            int pos = 1;
            boolean encontrada = false;
            boolean pasada = false;
            IteratorIF<GTreeIF<Node>> it = nodoActual.getChildren().iterator();

            while (it.hasNext() && !encontrada && !pasada) {
                GTreeIF<Node> hijo = it.getNext();
                Node contenido = hijo.getRoot();

                if (contenido.getNodeType() == Node.NodeType.INNER) {
                    char letraNodo = ((NodeInner) contenido).getLetter();
                    if (letraNodo == letra) {
                        encontrada = true;
                    } else if (letraNodo > letra) {
                        pasada = true;
                    } else {
                        pos++;
                    }
                } else {
                    pos++;
                }
            }

            if (!encontrada) {
                GTreeIF<Node> nuevoSub = new GTree<>();
                nuevoSub.setRoot(new NodeInner(letra));
                nodoActual.addChild(pos, nuevoSub);
            }

            nodoActual = nodoActual.getChild(pos);
        }
        int posInfo = 1;
        boolean infoEncontrado = false;
        IteratorIF<GTreeIF<Node>> itInfo = nodoActual.getChildren().iterator();

        while (itInfo.hasNext() && !infoEncontrado) {
            GTreeIF<Node> hijoInfo = itInfo.getNext();
            if (hijoInfo.getRoot().getNodeType() == Node.NodeType.INFO) {
                infoEncontrado = true;
                // Si ya existe la palabra, añadimos el doc_id y freq a su lista
                ((NodeInfo) hijoInfo.getRoot()).getSeqPSR().add(new Pair_S_F(doc_id, freq));
            } else {
                posInfo++;
            }
        }

        if (!infoEncontrado) {
            Pair_S_F nuevoPar = new Pair_S_F(doc_id, freq);

            NodeInfo nuevoNodoInfo = new NodeInfo(nuevoPar);

            GTreeIF<Node> ramaInfo = new GTree<>();
            ramaInfo.setRoot(nuevoNodoInfo);

            nodoActual.addChild(1, ramaInfo);
        }

    }

    @Override
    public IteratorIF<Pair_W_SeqPSF> prefixIterator(String prefix) {
        return null;
    }
}
