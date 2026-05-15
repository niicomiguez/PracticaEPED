package es.uned.lsi.eped.Examenes;

import es.uned.lsi.eped.DataStructures.Stack;
import es.uned.lsi.eped.DataStructures.StackIF;

public class Ex21_22 {
    StackIF<Integer> sumStacks (StackIF<Integer> s1, StackIF<Integer> s2){
        StackIF<Integer> pilaAux= new Stack<>();
        StackIF<Integer> pilaFinal= new Stack<>();
        while (!s1.isEmpty()){
            int primeros1 = s1.getTop();
            int primeros2= s2.getTop();
            s1.pop();
            s2.pop();
            pilaAux.push(primeros1+primeros2);
        }
        while (!pilaAux.isEmpty()){
            int actual= pilaAux.getTop();
            pilaAux.pop();
            pilaFinal.push(actual);
        }
        return pilaFinal;
    }
    // Lo mejor es la recursividad por la pila de llamadas del sistema
    StackIF<Integer> sumStacksRecursivo (StackIF<Integer> s1, StackIF<Integer> s2){
        StackIF<Integer> pilaFinal= new Stack<>();

        if (s1.isEmpty()){
            return new Stack<Integer>();
        }
        int actuals1= s1.getTop();
        int actuals2=s2.getTop();
        s1.pop();
        s2.pop();

        StackIF<Integer> res = sumStacks(s1, s2);
        s1.push(actuals1);
        s2.push(actuals2);

        res.push(actuals1 + actuals2);

        return res;
    }

    public static void main(String[] args) {

    }
}
