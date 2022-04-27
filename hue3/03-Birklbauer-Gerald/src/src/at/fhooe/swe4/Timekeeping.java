package at.fhooe.swe4;

import java.util.PriorityQueue;
import java.util.Random;

public class Timekeeping<T> {
  public enum selection {
    DHeapQueue,
    Heap,
    PriorityQueue
  }

  private PQueue<Integer> internalContainer;
  private PriorityQueue<Integer> internalPriorityQueue;
  private StringBuffer result;
  private int testSize;
  private selection option;
  private int daryHeap = 2;
  private int round = 3;
  private int multiplier = 10;
  private int maxRandomNumber = 1000000;

/**
 * Timekkeping Class is for time measurement for heap, Dheap and priorityQueue
 * size: set the number of elements for test, if you run enqueue or dequeue test. Value will not used for entire simulation
 * selection container: With selection you can choose heap, Dheap or priorityQueue container
 * d : Parameter for Dheap size
 * */
  public Timekeeping(int size, selection container, int d){
    if(selection.DHeapQueue.equals(container)){
      internalContainer = new DHeapQueue<>(d);
    } else if (selection.Heap.equals(container)){
      internalContainer = new Heap<>();
    } else if (selection.PriorityQueue.equals(container)){
      internalPriorityQueue = new PriorityQueue<>();
    }
    testSize = size;
    option = container;
    daryHeap = d;
    result  = new StringBuffer();
  }

  /**
   * Timekkeping Class is for time measurement for heap and priorityQueue
   * size: set the number of elements for test, if you run enqueue or dequeue test. Value will not used for entire simulation
   * selection container: With selection you can choose heap or priorityQueue container
   * */
  public Timekeeping(int size, selection container){
    if (selection.Heap.equals(container)){
      internalContainer = new Heap<>();
    } else if (selection.PriorityQueue.equals(container)){
      internalPriorityQueue = new PriorityQueue<>();
    }
    testSize = size;
    option = container;
    result  = new StringBuffer();
  }

  /* create a new Container for measurement */
  private void createNewContainer(){
    if(option.equals(selection.DHeapQueue)){
//      System.out.println("DHEAP");
      internalContainer = new DHeapQueue<>(daryHeap);
    } else if(option.equals(selection.Heap)){
//      System.out.println("HEAPP");
      internalContainer = new Heap<>();
    }  else if (selection.PriorityQueue.equals(option)){
      internalPriorityQueue = new PriorityQueue<>();
    }
  }

  /* fill Container with random number */
  public void fillContainerInt(){
    Random randomNumber = new Random();
    for(int i = 0; i < testSize; i++) {
      if (option.equals(selection.DHeapQueue) || option.equals(selection.Heap)) {
        internalContainer.enqueue((randomNumber.nextInt(maxRandomNumber)));
      } else if (option.equals(selection.PriorityQueue)) {
        internalPriorityQueue.add(randomNumber.nextInt(maxRandomNumber));
      }
    }
  }

  /* create a new Container for measurement */
  public void dequeueContainer(){
    for(int i = 0; i < testSize; i++){
      if (option.equals(selection.DHeapQueue) || option.equals(selection.Heap)) {
        internalContainer.dequeue();
      } else if (option.equals(selection.PriorityQueue)) {
        internalPriorityQueue.poll();
      }
    }
  }

  /* Run Enqueue Test */
  public StringBuilder RunEnqueue(){
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < 20; i++){
      createNewContainer();
      long start = System.nanoTime();
      fillContainerInt();
      long time = System.nanoTime() - start;
      result.append(time);
      result.append(", ");
    }
    return result;
  }

  /* Run Dequeue Test */
  public StringBuilder RunDequeue(){
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < 20; i++){
      createNewContainer();
      fillContainerInt();
      long start = System.nanoTime();
      dequeueContainer();
      long time = System.nanoTime() - start;
      result.append(time);
      result.append(", ");
    }
    return result;
  }

  /*start an entire test scenario */
  public void start(){
    int tmp = testSize;
    testSize = 10;
    result.delete(0, result.length());
    System.out.print("## Start measuring: " + option);
    if(option.equals(selection.DHeapQueue)){
      System.out.println("DaryHeap Size:" + daryHeap);
    } else {
      System.out.println();
    }
    result.append("Enqueue: ");
    for(int i = 0; i < round; i++){
      result.append("SIZE: " + testSize);
      result.append(System.getProperty("line.separator"));
      result.append(this.RunEnqueue().toString());
      testSize = testSize * multiplier;
      result.append(System.getProperty("line.separator"));
    }
    testSize = 10;
    result.append(System.getProperty("line.separator"));
    result.append("Dequeue: ");

    for(int i = 0; i < round; i++){
      result.append("SIZE: " + testSize);
      result.append(System.getProperty("line.separator"));
      result.append(this.RunDequeue().toString());
      testSize = testSize * multiplier;
      result.append(System.getProperty("line.separator"));
    }
    System.out.println("## End measuring with: " + option);
  }

  @Override
  public String toString() {
    return result.toString();
  }

}
