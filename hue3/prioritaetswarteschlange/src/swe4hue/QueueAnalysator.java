package swe4hue;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

public class QueueAnalysator {

  static final PrintStream CONSOLE = System.out;
  static final int REPETITIONS = 5;
  static final int PACKAGESIZE = 100_000;                //100 k
  static final Long MAXSIZE = Long.valueOf(5_000_000);   //5 mio

  private final DHeapQueue<Integer> data;

  /**
   * Data structures to save measured time per 100k enqueue / dequeue:
   * As enqueueing / dequeueing is repeated 5 times these lists will have the size of measurements per REPETITION * 5
   * E.g. per simulation repetition there are 100 values to be saved
   * If I want to calculate the average of enqueueing 100k numbers at queue-size=0
   *    I need to sum up the list entries 0, 100, 200, 300 & 400 (and devide it by 5)
   *    This will be done in the method calculateAveragesPrintEnqueueFile
   */
  private final ArrayList<Double> measuredValuesEnqueue;
  private final ArrayList<Double> measuredValuesDequeue;

  public QueueAnalysator(int d) {
    data = new DHeapQueue<>(d);
    measuredValuesEnqueue = new ArrayList<>();
    measuredValuesDequeue = new ArrayList<>();
  }

  /**
   * @throws FileNotFoundException
   * runs test 5 times. Each time 10 mio numbers are enqueued and afterwards again dequeued
   * averages for enqueueing and dequeueing are calculated and outputted to a .csv file
   */
  public void runTest() throws FileNotFoundException {
    for (int i = 0; i < REPETITIONS; i++) {     //repeat test 5 times (use average)
      enq();
      deq();
    }

    calculateAveragesPrintEnqueueFile();
    calculateAveragesPrintDequeueFile();
    System.out.println("done :)");
  }

  /**
   * method to enqueue 10 mio random numbers
   * time is measured for every 100k enqueue calls
   */
  public void enq() {
    Random rand = new Random();
    while(data.size() < MAXSIZE) {            //enqueue in total 10 mio random figures

      //enqueue 100k random numbers and measure time
      long start = System.nanoTime();
      for(int enqueueCalls = 0; enqueueCalls < PACKAGESIZE; enqueueCalls++) {
        data.enqueue(rand.nextInt(10_000_000));
      }
      long time = ((System.nanoTime() - start));    //ns
      //System.out.println("enq time: " + time / 1000000000.0);
      measuredValuesEnqueue.add(time/ 1000000000.0);
    }
  }

  /**
   * method do dequeue numbers and measure time per 100k dequeue calls
   */
  public void deq() {
    while(data.size() > 0) {
      //dequeue 100k random numbers and measure time
      long start = System.nanoTime();
      for (int dequeueCalls = 0; dequeueCalls < PACKAGESIZE; dequeueCalls++) {
        data.dequeue();
      }
      long time = ((System.nanoTime() - start));    //ns
      //System.out.println("deq time: " + time / 1000000000.0);
      measuredValuesDequeue.add(time / 1000000000.0);
    }
  }

  /**
   * Just sets the name of the output file depending on the input string
   * @param method: should either be "enqueue" or "dequeue". File will be named accordingly
   * @return: name of outputfile (method_d_#.csv => e.g. enqueue_d_3.csv
   */
    private String setFileName(String method) {
      int d = data.getD();
      StringBuilder sb = new StringBuilder();
      sb.append(method + "_d_");
      sb.append(d);
      sb.append(".csv");

      return sb.toString();
    }

  /**
   * @param stream: determines to which stream the System.out.writeln command writes the output
   */
    private void setOutputStream(PrintStream stream) {
      System.setOut(stream);
    }

  /**
   * Calculates averages from the measuredValuesEnqueueList and prints results in an output-file
   * @throws FileNotFoundException
   */
    private void calculateAveragesPrintEnqueueFile() throws FileNotFoundException {

      //prepare output file
      String sb = setFileName("enqueue");
      PrintStream o = new PrintStream(sb);      //create new outputstream
      setOutputStream(o);                       //redirect System.out to my newly created outputstream
      System.out.println("measured time starting at queue size; seconds");

      //calculating average
      long measurements = MAXSIZE / PACKAGESIZE;
      for(int i = 0; i < measurements; i++) {
        double sum = 0;
        for(int j = 0; j < measuredValuesEnqueue.size(); j += measurements) {
          sum += measuredValuesEnqueue.get(j+i);
        }
        double avg = sum / REPETITIONS;
        long insertStart = i * PACKAGESIZE;
        System.out.println(insertStart + ";" + avg);
      }

      //reset system output to console;
      setOutputStream(CONSOLE);
    }

    private void calculateAveragesPrintDequeueFile() throws FileNotFoundException {
      //prepare output file
      String sb = setFileName("dequeue");
      PrintStream o = new PrintStream(sb);
      setOutputStream(o);
      System.out.println("measured time starting at queue size; seconds");

      long measurements = MAXSIZE / PACKAGESIZE;
      for(int i = 0; i < measurements; i++) {
        double sum = 0;
        for(int j = 0; j < measuredValuesDequeue.size(); j += measurements) {
          sum += measuredValuesDequeue.get(j+i);
        }
        double avg = sum / REPETITIONS;
        long dequeueStart = MAXSIZE - (i * PACKAGESIZE);
        System.out.println(dequeueStart + ";" + avg);
      }

      setOutputStream(CONSOLE);
    }

    public static void main(String[] args) {
    QueueAnalysator q1 = new QueueAnalysator(2);
    QueueAnalysator q2 = new QueueAnalysator(64);
    QueueAnalysator q3 = new QueueAnalysator(128);
    QueueAnalysator q4 = new QueueAnalysator(256);

    try {
      q1.runTest();
      q2.runTest();
      q3.runTest();
      q4.runTest();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}
