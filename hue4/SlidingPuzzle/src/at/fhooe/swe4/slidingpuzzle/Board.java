package at.fhooe.swe4.slidingpuzzle;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Board implements Comparable<Board> {

  private final List board;
  private int size;
  private int arraySize;

  private int getArrayIndex(int row, int col) {
    if(row < 1 || row > size || col < 1 || col > size) {
      throw new ArrayIndexOutOfBoundsException("indices must be > 0 and <= size");
    }
    int nRow = row-1;
    int nCol = col-1;

    return nRow*size+nCol;
  }

  private int getRow(int index) {
    return index/size+1;
  }

  private int getCol(int index) {
    return index % size+1;
  }

  // Board mit Zielkonfiguration initialisieren.
  public Board(int size) {
    int saveSize = size;
    if((size != 3) && (size != 4)) {
      System.out.println("Only size 3 or 4 allowed. Board was initialized with size = 3");
      saveSize = 3;
    }
    board = new ArrayList<Integer>(saveSize*saveSize);
    for(int i = 0; i < saveSize*saveSize; i++) {
      board.add(i,i+1);
    }

    board.set(saveSize*saveSize-1, 0);

    this.size = saveSize;
    this.arraySize = saveSize*saveSize;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for(int row = 1; row <= size; row++) {
      for(int col = 1; col <= size; col++) {
        sb.append(board.get(getArrayIndex(row, col)));
        sb.append(" ");
      }
      sb.append(System.getProperty("line.separator"));
    }
    return sb.toString();
  }

  // Überprüfen, ob dieses Board und das Board other dieselbe Konfiguration
  // aufweisen.
  public boolean equals(Object other) {
    if (this == other) return true;
    if(other == null || getClass() != other.getClass()) return false;

    Board o = (Board)other;
    return Objects.equals(board, o.board) &&
            Objects.equals(size, o.size);
  }

  // <1, wenn dieses Board kleiner als other ist.
  // 0, wenn beide Boards gleich sind
  // >1, wenn dieses Board größer als other ist.
  public int compareTo(Board other) {
    if (size < other.size) {
      return -1;
    }
    else if (size == other.size) {
      return 0;
    } else
      return 1;
  }

  // Gibt die Nummer der Kachel an der Stelle (i,j) zurück,
  // Indizes beginnen bei 1. (1,1) ist somit die linke obere Ecke.
  // Wirft die Laufzeitausnahme InvalidBoardIndexException.
  public int getTile(int i, int j) {
    if(i < 1 || i > size || j < 1 || j > size) {
      throw new InvalidBoardIndexException(i,j,size);
    }
    return 0;
  }

  // Setzt die Kachelnummer an der Stelle (i,j) zurück. Wirft die
  // Laufzeitausnahmen
  // InvalidBoardIndexException und InvalidTileNumberException
  public void setTile(int i, int j, int number) {
    
  }

  // Setzt die Position der leeren Kachel auf (i,j)
  // Entsprechende Kachel wird auf 0 gesetzt.
  // Wirft InvalidBoardIndexException.
  public void setEmptyTile(int i, int j) {
    
  }

  // Zeilenindex der leeren Kachel
  public int getEmptyTileRow() {
    return 0;
  }

  // Gibt Spaltenindex der leeren Kachel zurück.
  public int getEmptyTileColumn() {
    return 0;
  }

  // Gibt Anzahl der Zeilen (= Anzahl der Spalten) des Boards zurück.
  public int size() {
    return size;
  }

  // Überprüft, ob Position der Kacheln konsistent ist.
  public boolean isValid() {
    return false;
  }

  // Macht eine tiefe Kopie des Boards.
  // Vorsicht: Referenztypen müssen neu allokiert und anschließend deren Inhalt
  // kopiert werden.
  public Board copy() {
    return null;
  }

  // Erzeugt eine zufällige lösbare Konfiguration des Boards, indem auf die
  // bestehende
  // Konfiguration eine Reihe zufälliger Verschiebeoperationen angewandt wird.
  public void shuffle() {
    
  }

  // Verschiebt leere Kachel auf neue Position (row, col).
  // throws IllegalMoveException
  public void move(int row, int col) {
    
  }

  // Verschiebt leere Kachel nach links. Wirft Laufzeitausnahme
  // IllegalMoveException.
  public void moveLeft() {
    
  }

  // Verschiebt leere Kachel nach rechts. Wirft IllegalMoveException.
  public void moveRight() {
    
  }

  // Verschiebt leere Kachel nach oben. Wirft IllegalMoveException.
  public void moveUp() {
    
  }

  // Verschiebt leere Kachel nach unten. Wirft IllegalMoveException.
  public void moveDown() {
    
  }

  // Führt eine Sequenz an Verschiebeoperationen durch. Wirft
  // IllegalMoveException.
  public void makeMoves(List<Move> moves) {
    
  }

  public static void main(String[] args) {
    Board b1 = new Board(3);
    System.out.println(b1);

    Board b2 = new Board(3);
    System.out.println(b2);

    boolean result = b1.equals(b2);
    System.out.println(result);


  }

}
