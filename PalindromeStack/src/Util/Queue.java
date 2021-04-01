package Util;

import java.util.LinkedList;

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
        return this.Q.removeLast();
    }

    public void peek(){         // guarda il primo elemento
        if(Q.size()==0) throw new IndexOutOfBoundsException();
        this.Q.getFirst();
    }
}
