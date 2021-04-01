package Util;

import java.util.ArrayList;
import java.util.EmptyStackException;
//import ch.edu.MAL.model.MyArrayList;


//import java.util.LinkedList;

public class Stack<E> {
    private ArrayList<E> S = new ArrayList();


    public Stack(){}
    public int getSize() { return S.size(); }

    public void push(E e){      // aggiunge un elemento alla coda
        this.S.add(e);
    }

    public E pop(){         // rimuove l'ultimo elemento aggiunto dalla coda e lo ritorna
        if(S.size()==0) throw new EmptyStackException();
        return this.S.remove(this.S.size()-1);
    }

    public void peek(){         // guarda l'ultimo elemento
        if(S.size()==0) throw new EmptyStackException();
        this.S.get(this.S.size()-1);
    }

    public boolean isEmpty(){
        return (S.size()==0);
    }

}
/*

public class Queue<E> {

    private LinkedList<E> Q = new LinkedList();

    public Queue(){}

    public int getSize(){
        return Q.size();
    }

    public void enqueue(E e){      // aggiunge un elemento alla coda
        this.Q.addFirst(e);
    }

    public E dequeue(){         // rimuove il primo elemento aggiunto dalla coda e lo ritorna
        if(Q.size()==0) throw new IndexOutOfBoundsException();
        return this.Q.removeFirst();
    }

    public void peek(){         // guarda il primo elemento
        if(Q.size()==0) throw new IndexOutOfBoundsException();
        this.Q.getFirst();
    }
}
*/
