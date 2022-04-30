package at.fhooe.swe4.slidingpuzzle;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.util.*;

import static java.lang.Math.abs;

public class Board implements Comparable<Board> {

  private final List board;
  private int size;

  private int getArrayIndex(int row, int col) {
    if(row < 1 || row > size || col < 1 || col > size) {
      throw new ArrayIndexOutOfBoundsException("indices must be > 0 and <= size");
    }
    int nRow = row-1;
    int nCol = col-1;

    return nRow*size+nCol;
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
  }

  public Board(List boardInput, int size) {
    board = new ArrayList<Integer>();
    board.addAll(boardInput);
    this.size = size;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for(int row = 1; row <= size; row++) {
      for(int col = 1; col <= size; col++) {
        sb.append(board.get(getArrayIndex(row, col)));
        sb.append(" ");
      }
      sb.append("\n");
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

    Integer val = (Integer) board.get(getArrayIndex(i,j));
    return val;
  }

  // Setzt die Kachelnummer an der Stelle (i,j) zurück. Wirft die
  // Laufzeitausnahmen
  // InvalidBoardIndexException und InvalidTileNumberException
  public void setTile(int i, int j, int number) {
    if(i < 1 || i > size || j < 1 || j > size) {
      throw new InvalidBoardIndexException(i,j,size);
    }

    if(number < 0 || number > size*size-1) {
      throw new InvalidTileNumberException(number, size);
    }

    int aIndex = getArrayIndex(i,j);
    board.set(aIndex, number);
  }

  // Setzt die Position der leeren Kachel auf (i,j)
  // Entsprechende Kachel wird auf 0 gesetzt.
  // Wirft InvalidBoardIndexException.
  public void setEmptyTile(int i, int j) {
    setTile(i,j,0);
  }

  // Zeilenindex der leeren Kachel
  public int getEmptyTileRow() {
    int retVal = -1;
    for(int row = 1; row <= size; row++) {
      for(int col = 1; col <= size; col++) {
        int curVal = getTile(row, col);
        if(curVal == 0) {
          retVal = row;
        }
      }
    }
    return retVal;
  }

  // Gibt Spaltenindex der leeren Kachel zurück.
  public int getEmptyTileColumn() {
    int retVal = -1;
    for(int row = 1; row <= size; row++) {
      for(int col = 1; col <= size; col++) {
        int curVal = getTile(row, col);
        if(curVal == 0) {
          retVal = col;
        }
      }
    }
    return retVal;
  }

  // Gibt Anzahl der Zeilen (= Anzahl der Spalten) des Boards zurück.
  public int size() {
    return size;
  }

  // Überprüft, ob Position der Kacheln konsistent ist.
  public boolean isValid() {
    ArrayList<Integer> copy = new ArrayList<>();
    copy.addAll(board);
    Collections.sort(copy);

    for(int i = 0; i < size*size; i++) {
      int curVal = (Integer) copy.get(i);
      if(curVal != i) {
        return false;
      }
    }
    return true;
  }

  // Macht eine tiefe Kopie des Boards.
  // Vorsicht: Referenztypen müssen neu allokiert und anschließend deren Inhalt
  // kopiert werden.
  public Board copy() {
    Board copy = new Board(size);
    for(int i = 0; i < size*size; i++) {
      copy.board.set(i, board.get(i));
    }
    return copy;
  }

  public boolean isSolvable() {
    int inversions = 0;

    for(int i = 0; i < board.size() - 1; i++) {
      for(int j = i + 1; j < board.size(); j++)
        if((Integer) board.get(i) > (Integer) board.get(j)) inversions++;
      if((Integer) board.get(i) == 0 && i % 2 == 1) inversions++;
    }

    /*
      If size is odd, then number inversions must be even for board to be solvable.
      If size is even, then number of inversions must be odd for board to be solvable.
    */
    if (size % 2 == 1) {
      return (inversions % 2 == 0);
    } else {
      return (inversions % 2 == 1);
    }
  }

  // Erzeugt eine zufällige lösbare Konfiguration des Boards, indem auf die
  // bestehende
  // Konfiguration eine Reihe zufälliger Verschiebeoperationen angewandt wird.
  public void shuffle() {

    Collections.shuffle(board);
    while(!this.isSolvable()) {
      Collections.shuffle(board);
    }
  }

  private boolean isNeighbourOfEmptyField(int row, int col) {
    int rowEmpty = getEmptyTileRow();
    int colEmpty = getEmptyTileColumn();

    int rowOffset = row-rowEmpty;
    int colOffset = col-colEmpty;

    if((abs(rowOffset) == 0 && abs(colOffset) == 1) ||
            (abs(rowOffset) == 1 && abs(colOffset) == 0)) {
      return true;
    } else {
      return false;
    }
  }

  // Verschiebt leere Kachel auf neue Position (row, col).
  // throws IllegalMoveException
  public void move(int row, int col) {
    int rowEmpty = getEmptyTileRow();
    int colEmpty = getEmptyTileColumn();

    if((row < 1 || row > size || col < 1 || col > size)
              || (!isNeighbourOfEmptyField(row, col))) {
      throw new IllegalMoveException(row, col, rowEmpty, colEmpty);
    }

    int temp = getTile(row, col);
    setTile(row, col, 0);
    setTile(rowEmpty, colEmpty, temp);
  }

  // Verschiebt leere Kachel nach links. Wirft Laufzeitausnahme
  // IllegalMoveException.
  public void moveLeft() {
    int rowEmpty = getEmptyTileRow();
    int colEmpty = getEmptyTileColumn();

    if(colEmpty < 2) {
      throw new IllegalMoveException(rowEmpty, colEmpty-1, rowEmpty, colEmpty);
    } else {
      move(rowEmpty, colEmpty-1);
    }
  }

  // Verschiebt leere Kachel nach rechts. Wirft IllegalMoveException.
  public void moveRight() {
    int rowEmpty = getEmptyTileRow();
    int colEmpty = getEmptyTileColumn();

    if(colEmpty > 2) {
      throw new IllegalMoveException(rowEmpty, colEmpty+1, rowEmpty, colEmpty);
    } else {
      move(rowEmpty, colEmpty+1);
    }
  }

  // Verschiebt leere Kachel nach oben. Wirft IllegalMoveException.
  public void moveUp() {
    int rowEmpty = getEmptyTileRow();
    int colEmpty = getEmptyTileColumn();

    if(rowEmpty < 2) {
      throw new IllegalMoveException(rowEmpty-1, colEmpty, rowEmpty, colEmpty);
    } else {
      move(rowEmpty-1, colEmpty);
    }
  }

  // Verschiebt leere Kachel nach unten. Wirft IllegalMoveException.
  public void moveDown() {
    int rowEmpty = getEmptyTileRow();
    int colEmpty = getEmptyTileColumn();

    if(rowEmpty > 2) {
      throw new IllegalMoveException(rowEmpty+1, colEmpty, rowEmpty, colEmpty);
    } else {
      move(rowEmpty+1, colEmpty);
    }
  }

  int getNewRowAfterMove(Move m) {
    int rowEmpty = getEmptyTileRow();
    int rowTo = rowEmpty;

    switch(m) {
      case DOWN:
        rowTo += 1;
        break;
      case UP:
        rowTo -= 1;
        break;
    }
    return rowTo;
  }

  int getNewColAfterMove(Move m) {
    int colEmpty = getEmptyTileColumn();
    int colTo = colEmpty;

    switch(m) {
      case LEFT:
        colTo -= 1;
        break;
      case RIGHT:
        colTo += 1;
        break;
    }
    return colTo;
  }

  // Führt eine Sequenz an Verschiebeoperationen durch. Wirft
  // IllegalMoveException.
  public void makeMoves(List<Move> moves) {
    for(int i = 0; i < moves.size(); i++) {
      Move m = moves.get(i);

      int newRow = getNewRowAfterMove(m);
      int newCol = getNewColAfterMove(m);

      if ((newRow < 1 || newRow > size || newCol < 1 || newCol > size) || (!isNeighbourOfEmptyField(newRow, newCol))) {
        throw new IllegalMoveException(newRow, newCol, getEmptyTileRow(), getEmptyTileColumn());
      }

      switch (m) {
        case UP:
          moveUp();
          break;
        case DOWN:
          moveDown();
          break;
        case LEFT:
          moveLeft();
          break;
        case RIGHT:
          moveRight();
          break;
      }
    }
  }

  public boolean isSolved() {
    for(int i = 0; i < size*size-2; i++) {
      Integer prev = (Integer)board.get(i);
      Integer succ = (Integer)board.get(i+1);
      if((prev+1) != succ) {
        return false;
      }
    }
    return true;
  }
}

