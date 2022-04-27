package swe4hue;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

public class DHeapQueue<T extends Comparable<T>> implements  PQueue<T>{

  private final List<T> values;
  private final int d;

  private boolean less(T a, T b) {
    return a.compareTo(b) < 0;
  }

  public DHeapQueue(int d) {
    values = new ArrayList<>();
    this.d = d;
  }

  private int parent(int i) { return (i-1)/d;}
  private int kthChild(int i, int k) {return (d * i) + k;}

  public int getD() {
    return this.d;
  }

  @Override
  public boolean isEmpty() {
    return values.isEmpty();
  }

  @Override
  public T peek() {
    return values.isEmpty() ? null : values.get(0);
  }

  @Override
  public void enqueue(T value) {
    assert isHeap();
    values.add(value);
    upHeap();
    assert isHeap();
  }

  @Override
  public T dequeue() {
    assert isHeap();
    if(values.isEmpty())
      throw new NoSuchElementException("cannot dequeue from empty queue");

    T top = values.get(0);
    int last = values.size()-1;
    values.set(0, values.get(last));
    values.remove(last);

    if(!values.isEmpty())
      downHeap();
    assert isHeap();
    return top;
  }

  @Override
  public int size() {
    return values.size();
  }

  private void upHeap() {
    int i = values.size()-1;
    T x = values.get(i);
    while(i != 0 && less(values.get(parent(i)), x)) {
      values.set(i, values.get(parent(i)));
      i = parent(i);
    }

    values.set(i, x);
  }

<<<<<<<< HEAD:hue3/src/prioritaetswarteschlange/src/swe4hue/DHeapQueue.java

========
>>>>>>>> origin/master:hue3/prioritaetswarteschlange/src/swe4hue/DHeapQueue.java
  private int getIndexOfGreatestChild(int i) {
    int indexOfMaxChild = kthChild(i,1);

    T maxChildVal = values.get(indexOfMaxChild);

    int k = 2;
    int kthChildPos = kthChild(i, k);

    while((kthChildPos < values.size()) && (k <= d)) {
      T kthChildVal = values.get(kthChildPos);
      if (less(maxChildVal, kthChildVal)) {
        maxChildVal = kthChildVal;
        indexOfMaxChild = kthChildPos;
      }

      k++;
      kthChildPos = kthChild(i, k);
    }
    return indexOfMaxChild;
  }

  private void downHeap() {
    assert ! values.isEmpty();
    int i = 0;
    T x = values.get(0);
    while(kthChild(i,1) < values.size()) {
      int indexOfGreatestChild = getIndexOfGreatestChild(i);
      if (!less(x, values.get(indexOfGreatestChild)))
        break;

      values.set(i, values.get(indexOfGreatestChild));
      i = indexOfGreatestChild;
    }
    values.set(i, x);
  }

  private boolean isHeap() {
    int i = 1;
    while(i < values.size() &&
            !less(values.get(parent(i)), values.get(i))) {
      i++;
    }
    return i >= values.size();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("heap = [");
    for(int i = 0; i < values.size(); i++) {
      if(i>0) sb.append(", ");
      sb.append(values.get(i));
    }
    sb.append("]");
    return sb.toString();
  }

  public static void main(String[] args) {
<<<<<<<< HEAD:hue3/src/prioritaetswarteschlange/src/swe4hue/DHeapQueue.java
    System.out.println("For detailed tests see JUnit test class");
========
    System.out.println("DHeap testing");
>>>>>>>> origin/master:hue3/prioritaetswarteschlange/src/swe4hue/DHeapQueue.java
    DHeapQueue<Integer> h = new DHeapQueue<>(5);
    Random r = new Random();
    for(int i = 0; i < 20; i++) {
      h.enqueue(r.nextInt(100));
    }

    String s = h.toString();
    System.out.println(s);

    h.dequeue();
    s = h.toString();
    System.out.println(s);

    h.dequeue();
    s = h.toString();
    System.out.println(s);
  }

}
