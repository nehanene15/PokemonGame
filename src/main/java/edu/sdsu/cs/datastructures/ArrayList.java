package edu.sdsu.cs.datastructures;

import java.util.AbstractList;
import java.util.Collection;

/**
 * Created by NehaNene, cssc0669.
 * Array list implementation.
 */

public class ArrayList<E> extends AbstractList<E>{
    private static final int defaultCapacity=16;
    public int size=0; //amount of elements in array
    public E[] storage; //total capacity of the array

    public ArrayList(){
        this(defaultCapacity);
    }

    public ArrayList(int initCapacity) {
        if(initCapacity<0){
            throw new IllegalArgumentException("Illegal argument "+initCapacity);
        }
        storage=(E[])(new Object[initCapacity]);
        size=0;
    }

    public ArrayList(Collection<E> col){
        storage = (E[])col.toArray();
        size =storage.length;
    }

    public E set(int i, E in){ //set index i to element in
       checkRange(i);
       E oldVal = storage[i];
       storage[i] = in;
       return oldVal;
   }

    public E get(int index) {//get element at index
        checkRange(index);
        return storage[index];
    }

    public int size() {
        return size;
    }

    public void add(int i, E element){ //add element at index i
        checkRange(i);
        ensureCapacity();
        System.arraycopy(storage, i, storage, i +1, size - i);
        storage[i] = element;
        size++;

    }

    public E remove(int i){ //remove element at index i
        checkRange(i);
        E oldVal = storage[i];
        int numShift = size - i-1;
        if(numShift>0){
            System.arraycopy(storage,i+1,storage,i,numShift);
        }
        storage[--size]=null;
        return oldVal;
    }


    private void checkRange(int index) { //throws index out of bounds excpetion
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException("Index out of bounds "+(index));
    }


    private void ensureCapacity(){ //adjust capacity depending on size of array list

        if(size()==0){
            return;
        }
        if((double)size>=((double)storage.length*.8)){
            upsize();
          }
        if((double)size<=((double)storage.length*.3)){
           downsize();
        }

    }

    public void upsize(){ // double capacity
        E[] upsized = (E[]) new Object[storage.length*2];
        System.arraycopy(storage, 0, upsized, 0, storage.length);
        storage = upsized;
    }

    private void downsize(){ //decrease capacity
        E[] downsized = (E[]) new Object[storage.length/2];
        System.arraycopy(storage, 0, downsized, 0, downsized.length);
        storage = downsized;

    }










}
