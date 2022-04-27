package at.fhooe.swe4;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class DHeapQueue<T extends Comparable<T>> implements PQueue<T> {
  private List<T> values;
  int d;

  /**
   *  Constructor without parameter init the heap with size 2
   * */
  public DHeapQueue() {
     values = new ArrayList<>();
    d = 2;
  }

  /**
   *  number of fChildren define the size of D-ary heap
   * */
  public DHeapQueue(int numberOfChildren) {
    if(numberOfChildren < 2){
      throw new IllegalArgumentException("Number of Children must be > 1");
    }
    values = new ArrayList<>();
    d = numberOfChildren;
  }


  private boolean less(T a, T b) {
    return a.compareTo(b) < 0;
  }

  private int parent(int i) {
    return ((i-1) / d);
  }

  private int lastChild(int start){
    return(start*d+d);
  }

  private int firstChild(int start){
    return (start*d+1);
  }


  /* helper function to bring the heap in correct condition, if you dequeue a value */
  private void upHeap() {
    int i = values.size() - 1; //start Array 0
    T x = values.get(i);
    while (i != 0 && less(values.get(parent(i)), x)) {
      values.set(i, values.get(parent(i)));
      i = parent(i);
    }
    values.set(i, x);
  }

  /* helper function to bring the heap in correct condition, if you enqueue a value */
  private void downHeap() {
    assert !values.isEmpty();
    int root = 0;
    int maxChildIndex = maxChild(firstChild(root),lastChild(root));
    T tmpValue = values.get(root);
    while ((maxChildIndex < values.size())
            && (!less(values.get(maxChildIndex), tmpValue))){
      values.set(root, values.get(maxChildIndex));
      root = maxChildIndex;
      maxChildIndex = maxChild(firstChild(maxChildIndex),lastChild(maxChildIndex));
    }
    values.set(root, tmpValue);
  }

  public int maxChild(int start, int end) {
    int maxChildIndex = start;
    int i = start;
    while (i <= end && i < values.size()) {
      if (less(values.get((maxChildIndex)), values.get(i))) {
        maxChildIndex = i;
      }
      i++;
    }
    return maxChildIndex;
  }

  /*figure out if heap is in correct condition
   * check each value with the parent -> parent have to greater one
   * */
  private boolean isHeap() {
    int i = 1;
    while (i < values.size() && !less(values.get(parent(i)), values.get(i))) {
      i++;
    }

    return i >= values.size();
  }

  /**
   *  Return the size of the Heap
   * */
  @Override
  public int size(){
    return values.size();
  }

  /**
   *  Return True, if Heap is empty
   * */
  @Override
  public boolean isEmpty() {
    return values.isEmpty();
  }

  /**
   *  Return a first element without remove
   * */
  @Override
  public T peek() {
    return values.isEmpty() ? null : values.get(0);
  }

  /**
   *  Insert a new Element in the Heap
   * */
  @Override
  public void enqueue(T value) {
    assert isHeap();
    values.add(value);
    upHeap();
    assert isHeap();
  }


  /**
   *  Remove first Element of the heap
   * */
  @Override
  public T dequeue() {
    assert isHeap();
    if (values.isEmpty())
      throw new NoSuchElementException("cannot dequeue form empty queue");

    T top = values.get(0);
    int last = values.size() - 1;
    values.set(0, values.get(last));
    values.remove(last);
    if (!values.isEmpty())
      downHeap();
    assert isHeap();
    return top;
  }

    /**
   *  Return a String of the Content
   * */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("DHeap = [");
    for (int i = 0; i < values.size(); i++) {
      sb.append(values.get(i));
      sb.append(" ");
    }
    sb.append(("]"));
    return sb.toString();
  }

}
