package es.uned.lsi.eped.Examenes;

import es.uned.lsi.eped.DataStructures.IteratorIF;
import es.uned.lsi.eped.DataStructures.Queue;
import es.uned.lsi.eped.DataStructures.Stack;

public class Ex22_23 {
    // APARTADO 2 a) - ALGORITMO DE DESPLAZAMIENTO EFICIENTE EN LISTAS ENLAZADAS

// 1. PRECONDICIONES Y SEGURIDAD
// Antes de operar, verificamos que la lista contenga elementos.
// @Pre: !this.isEmpty() (La lista no debe ser nula ni estar vacía).

// 2. NORMALIZACIÓN DEL DESPLAZAMIENTO (n)
// Para que el algoritmo sea eficiente y funcione con cualquier valor de n:
// a) Calculamos el resto: r = n % size(). Así, si n=8 y size=6, r=2.
// b) Si r < 0 (desplazamiento izquierda), lo convertimos a derecha: r = size + r.
// Tras esto, r siempre será un valor positivo entre 0 y size-1.

// 3. ESTRATEGIA DE RECONFIGURACIÓN DE NODOS (Circularidad)
// En lugar de mover elementos uno a uno, cambiamos las "flechas" (punteros).
// El objetivo es desplazar r posiciones a la DERECHA.

// A) Localización del punto de corte:
// El "nuevo último nodo" (newLastNode) será el que ocupe la posición (size - r).
// Ejemplo: [1,2,3,4,5,6] con r=2 -> el nuevo último es el 4 (posición 6-2=4).

// B) Cerrar el anillo:
// Recorremos hasta el final de la lista original y hacemos que el next del
// último nodo apunte al firstNode actual. Ahora la lista es circular.

// C) Actualizar la cabecera (FirstNode):
// El "nuevo primer nodo" será el que va justo después de nuestro newLastNode.
// Hacemos que el atributo firstNode de la lista apunte a newLastNode.next.

// D) Romper el anillo (Clave para evitar bucles infinitos):
// El newLastNode debe dejar de apuntar a su siguiente para marcar el final.
// Hacemos que newLastNode.next = null.

// JUSTIFICACIÓN DE EFICIENCIA:
// Este método solo requiere un recorrido de la lista para localizar los nodos,
// por lo que el coste es O(n) (lineal respecto al tamaño de la lista),
// independientemente de lo grande que sea el valor de desplazamiento n.

    // b
    // si tenemos la lista 1 2 3 4 5 6 y queremos mover 2 posiciones m=2 desde n=2
    // pues entonces quedaría 5 6 1 2 3 4, pero si movemos 2 posicione desde 3 por ejemplo
    // la lista quedaria exactamente igual porque seguimos moviendo solamente 2 posiciones
    // entonces el parámetro n que nos dice donde empezar no es necesario
    // se resolvería entonces como el apartado a

    // 3 a
    // La mejor estructura es una Cola ya que al ser FIFO
    // entonces podremos que quitar el disfraz que se metió antes
    // usando simplemente getFirst y dequeue operaciones con coste cte

    // b - c
    Queue<String> disfraces = new Queue<>();

    // No podemos usar iteradores porque tenemos un tamaño variable
    // por eso cogemos el tamaño inicial en la variable n porque aunque
    // vamos encolando y desencolando elementos el tamaño sigue siendo el mismo
    // Así tendría simplemente un coste O(n) siendo n el tamaño de la cola
    public void guardaDisfraz(String disfraz) {
        int n = disfraces.size();

        for (int i = 0; i < n; i++) {
            String actual = disfraces.getFirst();
            disfraces.dequeue();
            if (!actual.equals(disfraz)) {
                disfraces.enqueue(actual);
            }
        }
        disfraces.enqueue(disfraz);
    }

    // 2 sem 22/23
    // el ejercicio 2 es literalmente el de desplazar las listas de antes
    public static void main(String[] args) {

    }
}


