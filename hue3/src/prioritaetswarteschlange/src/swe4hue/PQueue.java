package swe4hue;

public interface PQueue<T extends Comparable<T>> {
  boolean isEmpty();
  T peek();
  void enqueue(T value);
  T dequeue();
  int size();
}
