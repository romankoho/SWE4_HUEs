package at.fhooe.swe4;

import java.util.Random;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Nested;

class PQueueTest {
  private PQueue<Integer> heap;

  @Nested
  class HeapTest {
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
      heap = new Heap<>();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
      heap = null;
    }

    @org.junit.jupiter.api.Test
    void isEmpty() {
      assertTrue(heap.isEmpty());
      heap.enqueue(1);
      assertFalse(heap.isEmpty());
      heap.dequeue();
      assertTrue(heap.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void peekEndurance() {
      Random randomNumber = new Random();
      for(int i = 0; i < 1000; i++){
        heap.enqueue(randomNumber.nextInt(1000));
      }
      int value = 100000;
      heap.enqueue(value);
      assertEquals(heap.peek(), value);
    }

    @org.junit.jupiter.api.Test
    void EmptyQueue() {
      assertNull(heap.peek());
      assertThrows(NoSuchElementException.class, () -> heap.dequeue());
    }

    @org.junit.jupiter.api.Test
    void enqueue() {
      Random randomNumber = new Random();
      for(int i = 0; i < 50; i++){
        heap.enqueue(randomNumber.nextInt(100));
      }
      assertFalse(heap.isEmpty());
      Integer firstValue = heap.dequeue();
      Integer secondValue = heap.dequeue();
      assertTrue(firstValue.compareTo(secondValue) == 0 ||firstValue.compareTo(secondValue) == 1 );
      firstValue = secondValue;
      secondValue = heap.dequeue();
      assertTrue(firstValue.compareTo(secondValue) == 0 ||firstValue.compareTo(secondValue) == 1 );
      assertFalse(heap.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void dequeue() {
      assertTrue(heap.isEmpty());
      Random randomNumber = new Random();
      int counter = 0;
      for(int i = 0; i < 50; i++){
        heap.enqueue(randomNumber.nextInt(100));
        counter++;
      }
      assertFalse(heap.isEmpty());
      for(int i = 0; i < 40; i++){
        heap.dequeue();
        counter--;
      }
      assertFalse(heap.isEmpty());
      while(!heap.isEmpty()){
        heap.dequeue();
        counter--;
      }
      assertTrue(heap.isEmpty());
      assertEquals(counter, 0);
      assertThrows(NoSuchElementException.class, () -> heap.dequeue());
    }

    @org.junit.jupiter.api.Test
    void enduranceTest() {
      Random randomNumber = new Random();
      for(int i = 0; i < 5000; i++){
        heap.enqueue(randomNumber.nextInt(500));
        assertFalse(heap.isEmpty());
      }
      assertFalse(heap.isEmpty());
      assertTrue(5001 > heap.peek());
      for(int i = 0; i < 5000; i++){
        assertFalse(heap.isEmpty());
        int tmp = heap.dequeue();
        if(!heap.isEmpty()){
          assertTrue(tmp >= (int)heap.peek());
        }
      }
      assertTrue(heap.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void priorityTest() {
      for(int i = 0; i < 100; i++){
        heap.enqueue(i);
        assertFalse(heap.isEmpty());
        assertEquals(i, heap.peek());
      }
    }

    @org.junit.jupiter.api.Test
    void priorityTestRandomNumber() {
      Random randomNumber = new Random();
      for(int i = 0; i < 100; i++){
        heap.enqueue(randomNumber.nextInt(100));
      }
      while(!heap.isEmpty()){
        int firstValue = heap.dequeue();
        int secondValue = heap.dequeue();
        assertTrue(firstValue >= secondValue);
      }
    }

    @org.junit.jupiter.api.Test
    void sameNumber() {
      int value = 54;
      for(int i = 0; i < 100; i++){
        heap.enqueue(value);
      }
      while (!heap.isEmpty()){
        assertEquals(heap.dequeue(), value);
      }
    }

    @org.junit.jupiter.api.Test
    void sizeTest() {
      Random randomNumber = new Random();
      assertEquals(heap.size() ,0);
      heap.enqueue(20);
      assertEquals(heap.size() ,1);
      heap.enqueue(20);
      assertEquals(heap.size() ,2);
      heap.dequeue();
      assertEquals(heap.size() ,1);
      heap.dequeue();
      assertEquals(heap.size() ,0);
      for(int i = 0; i < 89; i++){
        heap.enqueue(randomNumber.nextInt(1000));
      }
      assertEquals(heap.size() ,89);
    }

    @org.junit.jupiter.api.Test
    void toStringTest() {
      heap.enqueue(1000);
      heap.enqueue(2000);
      String testString = heap.toString();
      assertEquals(testString,"Heap = [2000 1000 ]");
    }

  }

  @Nested
  class DHeapQueueTest  {
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
      heap = new DHeapQueue<>(3);
    }
    @org.junit.jupiter.api.AfterEach
    void tearDown() {
      heap = null;
    }

    @org.junit.jupiter.api.Test
    void isEmpty() {
      assertTrue(heap.isEmpty());
      heap.enqueue(1);
      assertFalse(heap.isEmpty());
      heap.dequeue();
      assertTrue(heap.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void ConstructorTest(){
      assertThrows(IllegalArgumentException.class, ()-> new DHeapQueue<Integer>(1));
      assertThrows(IllegalArgumentException.class, ()-> new DHeapQueue<Integer>(0));
    }

    @org.junit.jupiter.api.Test
    void peek() {
      int firstValue = 5;
      int secondValue = 100;
      assertTrue(heap.isEmpty());
      assertNull( heap.peek());
      heap.enqueue(firstValue);
      assertFalse(heap.isEmpty());
      assertEquals(Integer.valueOf(firstValue), heap.peek());
      heap.enqueue(secondValue);
      assertEquals(Integer.valueOf(secondValue), heap.peek());
      heap.dequeue();
      assertEquals(Integer.valueOf(firstValue), heap.peek());
      assertFalse(heap.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void peekEndurance() {
      Random randomNumber = new Random();
      for(int i = 0; i < 1000; i++){
        heap.enqueue(randomNumber.nextInt(1000));
      }
      int value = 100000;
      heap.enqueue(value);
      assertEquals(heap.peek(),value);
    }

    @org.junit.jupiter.api.Test
    void EmptyQueue() {
      assertNull(heap.peek());
      assertThrows(NoSuchElementException.class, () -> heap.dequeue());
    }

    @org.junit.jupiter.api.Test
    void enqueue() {
      Random randomNumber = new Random();
      for(int i = 0; i < 50; i++){
        heap.enqueue(randomNumber.nextInt(100));
      }
      assertFalse(heap.isEmpty());
      Integer firstValue = heap.dequeue();
      Integer secondValue = heap.dequeue();
      assertTrue(firstValue.compareTo(secondValue) == 0 ||firstValue.compareTo(secondValue) == 1 );
      firstValue = secondValue;
      secondValue = heap.dequeue();
      assertTrue(firstValue.compareTo(secondValue) == 0 ||firstValue.compareTo(secondValue) == 1 );
      assertFalse(heap.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void dequeue() {
      assertTrue(heap.isEmpty());
      Random randomNumber = new Random();
      int counter = 0;
      for(int i = 0; i < 50; i++){
        heap.enqueue(randomNumber.nextInt(100));
        counter++;
      }
      assertFalse(heap.isEmpty());
      for(int i = 0; i < 40; i++){
        heap.dequeue();
        counter--;
      }
      assertFalse(heap.isEmpty());
      while(!heap.isEmpty()){
        heap.dequeue();
        counter--;
      }
      assertTrue(heap.isEmpty());
      assertTrue(counter == 0);
      assertThrows(NoSuchElementException.class, () -> heap.dequeue());
    }

    @org.junit.jupiter.api.Test
    void enduranceTest() {
      Random randomNumber = new Random();
      for(int i = 0; i < 5000; i++){
        heap.enqueue(randomNumber.nextInt(500));
        assertFalse(heap.isEmpty());
      }
      assertFalse(heap.isEmpty());
      assertTrue(5001 > heap.peek());
      for(int i = 0; i < 5000; i++){
        assertFalse(heap.isEmpty());
        int tmp = heap.dequeue();
        if(!heap.isEmpty()){
          assertTrue(tmp >= (int)heap.peek());
        }
      }
      assertTrue(heap.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void priorityTest() {
      for(int i = 0; i < 100; i++){
        heap.enqueue(i);
        assertFalse(heap.isEmpty());
        assertEquals(i, heap.peek());
      }
    }

    @org.junit.jupiter.api.Test
    void priorityTestRandomNumber() {
      Random randomNumber = new Random();
      for(int i = 0; i < 100; i++){
        heap.enqueue(randomNumber.nextInt(100));
      }
      while(!heap.isEmpty()){
        int firstValue = heap.dequeue();
        int secondValue = heap.dequeue();
        assertTrue(firstValue >= secondValue);
      }
    }

    @org.junit.jupiter.api.Test
    void sameNumber() {
      int value = 54;
      for(int i = 0; i < 100; i++){
        heap.enqueue(value);
      }
      while (!heap.isEmpty()){
        assertEquals(heap.dequeue(),value);
      }
    }

    @org.junit.jupiter.api.Test
    void sizeTest() {
      Random randomNumber = new Random();
      assertEquals(heap.size() ,0);
      heap.enqueue(20);
      assertEquals(heap.size() ,1);
      heap.enqueue(20);
      assertEquals(heap.size() ,2);
      heap.dequeue();
      assertEquals(heap.size() ,1);
      heap.dequeue();
      assertEquals(heap.size() ,0);
      for(int i = 0; i < 89; i++){
        heap.enqueue(randomNumber.nextInt(1000));
      }
      assertEquals(heap.size(),89);
    }

    @org.junit.jupiter.api.Test
    void toStringTest() {
      heap.enqueue(1000);
      heap.enqueue(2000);
      String testString = heap.toString();
      assertEquals(testString,"DHeap = [2000 1000 ]");
    }
  }

}
