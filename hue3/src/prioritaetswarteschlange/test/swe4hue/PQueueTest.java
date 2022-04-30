package swe4hue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Test suite for classes that implement the PQueue interface
 */

class PQueueTest {

  static final int REPETITION = 10000;

  private PQueue<Integer> data;

  @BeforeEach
  void setUp() {
    data = new Heap<>();
    //data = new DHeapQueue<>(1000);
  }

  @AfterEach
  void tearDown() { data = null; }

  @Test
  void isEmpty() {
    assertTrue(data.isEmpty());
    data.enqueue(2);
    data.enqueue(2);
    assertFalse(data.isEmpty());
    data.dequeue();
    assertFalse(data.isEmpty());
    data.dequeue();
    assertTrue(data.isEmpty());
  }

  @Test
  void peek() {
    assertNull(data.peek());
    data.enqueue(5);
    data.enqueue(4);
    data.enqueue(7);
    data.enqueue(5);
    data.enqueue(8);
    data.enqueue(5);
    int val = data.dequeue();
    assertEquals(val, 8);
  }

  /**
   * Tests include a basic scenario, enqueueing several random numbers & enqueueing numbers with the same priority
   */
  @Test
  void enqueue() {
    enqueueBasics();
    enqueueRandom();
    enqueueSamePriority();
  }

  @Test
  void dequeue() {
    assertThrows(NoSuchElementException.class, () -> {data.dequeue();});
    assertEquals(data.size(), 0);

    Random rand = new Random();
    for(int i = 0; i < REPETITION; i++) {
      int val = rand.nextInt(10000);
      data.enqueue(val);
    }

    while(!data.isEmpty()) {
      assertEquals(data.peek(), data.dequeue());
    }
  }

  /**
   * Tests the basics of the enqueue method.
   */
  void enqueueBasics() {
    assertEquals(data.size(), 0);
    for(int i = 0; i < REPETITION; i++) {
      assertEquals(i, data.size());
      data.enqueue(i);
    }
    assertEquals(data.size(), REPETITION);

    while(! data.isEmpty()) {
      data.dequeue();
    }

    assertEquals(data.size(), 0);
  }

  void enqueueSamePriority() {
    data.enqueue(7);
    data.enqueue(3);
    data.enqueue(3);
    data.enqueue(4);
    data.enqueue(11);
    data.enqueue(7);
    data.enqueue(9);
    data.enqueue(10);
    data.enqueue(14);
    data.enqueue(10);
    data.enqueue(7);
    data.enqueue(9);

    assertEquals(data.dequeue(), 14);
    assertEquals(data.dequeue(), 11);
    assertEquals(data.dequeue(), 10);
    assertEquals(data.dequeue(), 10);
    assertEquals(data.dequeue(), 9);
    assertEquals(data.dequeue(), 9);
    assertEquals(data.dequeue(), 7);
    assertEquals(data.dequeue(), 7);
    assertEquals(data.dequeue(), 7);
    assertEquals(data.dequeue(), 4);
    assertEquals(data.dequeue(), 3);
    assertEquals(data.dequeue(), 3);

    assertThrows(NoSuchElementException.class, () -> {data.dequeue();});
  }

  /**
   * Test for enqueue method. Randomly adds 10k random numbers
   */
  void enqueueRandom() {
    Random rand = new Random();

    for(int i = 0; i < REPETITION; i++) {
      int r = rand.nextInt(10000);
      data.enqueue(r);
      assertEquals(data.peek(), data.dequeue());
    }
    assertEquals(data.size(), 0);
  }

  /**
   * compares my implementation of the priority queue with the JDK pQueue
   * 10k random numbers get inserted. When dequeueing it's asserted that the elements of both containers are always the same
   */
  @Test
  void CompareWithJDKpQueue() {
    PriorityQueue<Integer> compCont = new PriorityQueue<>(Collections.reverseOrder());
    Random rand = new Random();
    for(int i = 0; i < REPETITION; i++) {
      int x = rand.nextInt(10000);
      compCont.add(x);
      data.enqueue(x);
    }

    assertEquals(data.size(), compCont.size());

    for(int i = 0; i < REPETITION; i++) {
      assertEquals(data.dequeue(), compCont.poll());
    }

    assertEquals(data.size(), compCont.size());
  }

}