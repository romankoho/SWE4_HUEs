package at.fhooe.swe4;

public class Main {

  public static void main(String[] args) {
    Timekeeping<Integer> simulation;
//    simulation = new Timekeeping<Integer>(10, Timekeeping.selection.Heap);
//    simulation.start();
//    System.out.println(simulation.toString());
//    simulation = new Timekeeping<Integer>(10, Timekeeping.selection.DHeapQueue,4);
//    simulation.start();
//    System.out.println(simulation.toString());
//    simulation = new Timekeeping<Integer>(10, Timekeeping.selection.PriorityQueue);
//    simulation.start();
//    System.out.println(simulation.toString());

    /* Testcase like described in my documentation */
    PQueue<Integer> dheap = new DHeapQueue<>();
    dheap.enqueue(50);
    dheap.enqueue(20);
    dheap.enqueue(40);
    dheap.enqueue(50);
    dheap.enqueue(15);

    System.out.println(dheap.toString());
    dheap.enqueue(60);
    System.out.println(dheap.toString());
    dheap.dequeue();
    dheap.dequeue();
    dheap.dequeue();
  }
}
