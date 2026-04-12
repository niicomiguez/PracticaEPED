package es.uned.lsi.eped.pract2025_2026;

import es.uned.lsi.eped.DataStructures.*;

// POR HACER
public class IndexTree implements IndexIF {

    protected GTreeIF<Node> index;

    public IndexTree(){
        this.index = new GTree<>();
        Node contenidoRaiz = new NodeRoot();
        this.index.setRoot(contenidoRaiz);
    }

    @Override
    public Seq_PSF retrieveIndex(String p) {
        if(p==null || p.isEmpty()) return new Seq_PSF();
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
        if(p==null || p.isEmpty()) return;
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
        boolean infoEncontrado = false;
        IteratorIF<GTreeIF<Node>> itInfo = nodoActual.getChildren().iterator();

        while (itInfo.hasNext() && !infoEncontrado) {
            GTreeIF<Node> hijoInfo = itInfo.getNext();
            if (hijoInfo.getRoot().getNodeType() == Node.NodeType.INFO) {
                infoEncontrado = true;
                // Si ya existe la palabra, añadimos el doc_id y freq a su lista
                ((NodeInfo) hijoInfo.getRoot()).getSeqPSR().add(new Pair_S_F(doc_id, freq));
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
        ListIF<Pair_W_SeqPSF> resultados = new List<>();

        if (prefix == null || prefix.isEmpty()) return resultados.iterator();

        GTreeIF<Node> nodoActual = this.index;
        char[] palabraFragmentada = prefix.toCharArray();

        for (char letra : palabraFragmentada) {
            IteratorIF<GTreeIF<Node>> it = nodoActual.getChildren().iterator();
            boolean encontrada = false;

            while (it.hasNext() && !encontrada) {
                GTreeIF<Node> hijo = it.getNext();
                Node contenido = hijo.getRoot();

                if (contenido.getNodeType() == Node.NodeType.INNER) {
                    char letraNodo = ((NodeInner) contenido).getLetter();
                    if (letraNodo == letra) {
                        nodoActual = hijo;
                        encontrada = true;
                    } else if (letraNodo > letra) {
                        return resultados.iterator();
                    }
                }
            }

            if (!encontrada) {
                return resultados.iterator();
            }
        }

        explorar(nodoActual, prefix, resultados);
        return resultados.iterator();
    }
    private void explorar(GTreeIF<Node> nodo, String palabraAcumulada, ListIF<Pair_W_SeqPSF> resultados) {
        IteratorIF<GTreeIF<Node>> it = nodo.getChildren().iterator();

        while (it.hasNext()) {
            GTreeIF<Node> hijo = it.getNext();
            Node contenido = hijo.getRoot();

            // CASO A: HEMOS ENCONTRADO UNA PALABRA
            if (contenido.getNodeType() == Node.NodeType.INFO) {
                NodeInfo info = (NodeInfo) contenido;

                Pair_W_SeqPSF nuevoPar = new Pair_W_SeqPSF(palabraAcumulada, info.getSeqPSR());

                resultados.insert(resultados.size() + 1, nuevoPar);
            }

            // CASO B: ES UNA LETRA, HAY QUE SEGUIR BAJANDO
            else if (contenido.getNodeType() == Node.NodeType.INNER) {
                NodeInner inner = (NodeInner) contenido;
                // Llamada recursiva: pasamos el hijo y le sumamos su letra a la palabra
                explorar(hijo, palabraAcumulada + inner.getLetter(), resultados);
            }
        }
    }
}
