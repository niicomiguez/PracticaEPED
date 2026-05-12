package es.uned.lsi.eped.Examenes;

import es.uned.lsi.eped.DataStructures.IteratorIF;
import es.uned.lsi.eped.DataStructures.List;
import es.uned.lsi.eped.DataStructures.ListIF;
import es.uned.lsi.eped.DataStructures.Stack;

import java.net.Inet4Address;

public class Ex23_24 {

    // 1
    //a - b - c
    // Este método funciona perfectamente pero hay un problema
    // VECTOR = ARRAY entonces no cumple con lo que piden
    // Entonces habria que hacer un for sobre otro for y punto
    // de forma que tenemos 2 bucles anidados haciendo que el coste
    // sea n^k siendo n el tamaño del vector y k el número de bucles
    // entonces seria de n^2 luego también hay que tener en cuenta las casillas
    // que tengan el vector. Las veces que pase el bucle sobre el
    // vector es proporcional a su tamaño entonces el coste final es de
    // O(n^2 + k)
    // Si el valor de k es notoriamente grande notaríamos cambios significativos
    public ListIF<Integer> rehacerVector(ListIF<Integer> vector){
        IteratorIF<Integer> it= vector.iterator();
        Stack<Integer> stack=new Stack<>();
        ListIF<Integer> lista = new List<>();
        int contador=0;
        while (it.hasNext()){
            int numeroActual=it.getNext();
            for(int i=0;i<numeroActual;i++){
                stack.push(contador);
            }
            contador++;
        }

        while(!stack.isEmpty()){
            int elemento= stack.getTop();
            stack.pop();
            lista.insert(1,elemento);
        }

        return lista;
    }

    // 2 a - b - c
    // Se usaría una Pila por el mero hecho de operaciones de coste cte
    // además al ser una estructura LIFO. Pues el elemento que se tiene que cerrar
    // será el último que entró , haciendo más sencillo nuestra labor.

    // En primer lugar se haría un bucle for desde 0 a s.lenght()
    // se iria comprobando caracter a caracter, en caso de que sea
    // ( [ o { entonces en la pila se almacena su elemento análogo
    // ) ] o } respectivamente.
    // Posteriormente se seguiría ejecutando el bucle for y si el
    // siguiente carácter es ) ] o } pero es distinto de pila.getTop()
    // entonces no estará equilibrado, en caso contrario se seguirá le ejecución de forma normal
    // al final del bucle tiene que haber un pila.isEmpty() porque
    // si lo está quiere decir que se acabó la longitud del string, se salio del bucle
    // y el string esta equilibrado pero si no lo está entonces quiere decir
    // que el string se acabó pero que aún hay algo que no se cerró
    /*public boolean check(String s){

    }*/

    // 2 sem jun-----------------------------------------------
    // 1
    // Este método no hay que codificalo, también se podría
    // hacerse con contains() no ahorra coste pero es mas simple
    // entonces la mejor estrucutura seria un árbol con un AVL
    public boolean repetidos(ListIF<Integer> numeros){
        IteratorIF<Integer> it= numeros.iterator();
        ListIF<Integer> listaContadores= new List<>();
        while (it.hasNext()){
            int actual=it.getNext();
            int contadorActual=listaContadores.get(actual);
            if(contadorActual>=1){
                return true;
            }
            listaContadores.insert(actual, contadorActual+1);
        }
        return false;
    }
    public static void main(String[] args) {

    }
}
