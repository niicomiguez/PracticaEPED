package es.uned.lsi.eped.Examenes;

import es.uned.lsi.eped.DataStructures.*;

public class Ex24_25 {
    // 1 SEM JUNIO---------------------------------------------
    // 2 a -> 3 colas una para cada prioridad y luego con un
    // iterador recorrerlas y meterlas en una cola general
    // b
    public String prioridad(String p){
        return switch (p) {
            case "a" -> "alta";
            case "m" -> "media";
            case "b" -> "baja";
            default -> "nada";
        };
    }
    public void insertar(String e){
        QueueIF<String> pAlta= new Queue<>();
        QueueIF<String> pBaja= new Queue<>();
        QueueIF<String> pMedia= new Queue<>();

        if (prioridad(e).equals("alta")){
            pAlta.enqueue(e);
        } else if (prioridad(e).equals("alta")) {
            pMedia.enqueue(e);
        }else {
            pBaja.enqueue(e);
        }
    }

    // apartado c
    public IteratorIF<String> iterator(Queue<String> alta, Queue<String> baja, Queue<String>media){
        QueueIF<String> colaFinal= new Queue<>();
        IteratorIF<String> it= alta.iterator();
        while (it.hasNext()){
            colaFinal.enqueue(it.getNext());
        }
        it=media.iterator();
        while (it.hasNext()){
            colaFinal.enqueue(it.getNext());
        }
        it= baja.iterator();
        while (it.hasNext()){
            colaFinal.enqueue(it.getNext());
        }
        return colaFinal.iterator();
    }

    // 3
    // a -> Usamos una Cola en la que hacemos un dequeue si el size > 10
    // b
    static QueueIF <String> comands = new Queue<>();
    String lastComad = "";
    public void insertar2(String comando){
        comands.enqueue(comando);
        if (comands.size()>10){
            comands.dequeue();
        }
        this.lastComad=comando;
    }

    // c - > Este método no vale
    public static String lastCommand(){
        IteratorIF<String> it= comands.iterator();
        String actual= "";
        while (it.hasNext()){
            actual=it.getNext();
        }
        return actual;
    }

    // Ahora si que vale ya que nos piden COSTE CTE
    // CON BUCLE WHILE TIENE COSTE LINEAL
    public String lastCommand2(){
        return lastComad;
    }

    // 2 SEM JUNIO ----------------------------------------------
    // 3 Usamos una cola , podemos ir desencolando y encolando el primer
    // elemento que será el jugador actual
    QueueIF<String> jugadores = new Queue<>();
    public void pasarTurno(){
        String primerjugador= jugadores.getFirst();
        jugadores.dequeue();
        jugadores.enqueue(primerjugador);
    }
    public void eliminarJugador(){
        jugadores.dequeue();
    }

    // 2 SEPT 24/25 ----------------------------------------------
    // 3 - b
    public class Pair{
        int num;
        int repeticiones;

        public Pair(int num, int repeticiones) {
            this.num = num;
            this.repeticiones = repeticiones;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getRepeticiones() {
            return repeticiones;
        }

        public void setRepeticiones(int repeticiones) {
            this.repeticiones = repeticiones;
        }
    }
    public void simuladorConstructor(ListIF<Integer> l){
        QueueIF<Pair> colaPares = new Queue<>();
        IteratorIF<Integer> it= l.iterator();
        int actual = it.getNext();
        int contador = 1;
        while (it.hasNext()){
            int siguiente = it.getNext();
            if (actual==siguiente){
                contador++;
            }else{
                colaPares.enqueue(new Pair(actual,contador));
                contador=1;
            }
            actual=siguiente;
        }
        // IMPORTANTISIMO
        // si tenemos lista [2,2,3] , primero añadimos el par de doses
        // luego actual pasa a ser 3 pero como it.hasNext() no devuelve false
        // entonces no entra en el while y nunca se mete ese último 3
        // por ello tenemos que meter lo que quede al final con esta línea
        colaPares.enqueue(new Pair(actual,contador));
    }

    //c -> Usamos una Pila por el tema del orden pues teniamos 2 opciones
    // Si insertamos directamente en la pila final en el primer while
    // entonces tendriamos que usar insert(listaFinal.size()+1, num)
    // aumentando el coste total
    // sin embargo si usamos una pila solamente hariamos un bucle despues del otro
    // entonces el coste seria 2n que es = n
    // asi el coste total seria O(n) que es el tamaño total de la lista
    public ListIF<Integer> decode (QueueIF<Pair> colaPares){
        ListIF<Integer> listaFinal = new List<>();
        Stack<Integer> stack = new Stack<>();
        IteratorIF<Pair> it= colaPares.iterator();
        while (it.hasNext()){
            Pair actual= it.getNext();
            int numero= actual.getNum();
            int repeticiones = actual.getRepeticiones();
            for(int i=0;i<repeticiones;i++){
                stack.push(numero);
            }
        }
        while (!stack.isEmpty()){
            int numero = stack.getTop();
            stack.pop();
            listaFinal.insert(1,numero);
        }
        return listaFinal;
    }
    public static void main(String[] args) {

    }
}
