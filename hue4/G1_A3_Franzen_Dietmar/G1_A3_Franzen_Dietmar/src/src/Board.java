import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board implements Comparable<Board> {
  private final int size;
  private final List<Integer> board;

  // Board mit Zielkonfiguration initialisieren.
  public Board(int size) {
    if (size < 0)
      throw new IllegalArgumentException("Size has to be bigger then 0");
    this.size = size;
    board = new ArrayList<>(size * size);
    for (int i = 0; i < size * size - 1; i++) {
      board.add(i + 1);
    }
    board.add(0);
  }

  // Überprüfen, ob dieses Board und das Board other dieselbe Konfiguration
  // aufweisen.
  public boolean equals(Board other) {
    if (this.size != other.size())
      return false;
    for (int i = 1; i < size+1; i++) {
      for (int j = 1; j < size+1; j++) {
        if (this.getTile(i, j) != other.getTile(i, j))
          return false;
      }
    }
    return true;
  }

  // <1, wenn dieses Board kleiner als other ist.
  // 0, wenn beide Boards gleich sind
  // >1, wenn dieses Board größer als other ist.
  public int compareTo(Board other) {
    return other.size() - this.size();
  }

  // Gibt die Nummer der Kachel an der Stelle (i,j) zurück,
  // Indizes beginnen bei 1. (1,1) ist somit die linke obere Ecke.
  // Wirft die Laufzeitausnahme InvalidBoardIndexException.
  public int getTile(int i, int j) {
    if (checkCoordinates(i, j))
      throw new InvalidBoardIndexException("i: " + i + " or j: " + j + " is bigger then size: " + size);
    return this.board.get((i - 1) * size + (j - 1));
  }

  // Setzt die Kachelnummer an der Stelle (i,j) zurück. Wirft die
  // Laufzeitausnahmen
  // InvalidBoardIndexException und InvalidTileNumberException
  public void setTile(int i, int j, int number) {
    if (checkCoordinates(i, j))
      throw new InvalidBoardIndexException("i: " + i + " or j: " + j + " is bigger then size: " + size);
    this.board.set((i - 1) * size + (j - 1), number);
  }

  private boolean checkCoordinates(int i, int j) {
    return i < 1 || j < 1 || i > this.size || j > this.size;
  }

  // Setzt die Position der leeren Kachel auf (i,j)
  // Entsprechende Kachel wird auf 0 gesetzt.
  // Wirft InvalidBoardIndexException.
  public void setEmptyTile(int i, int j) {
    this.setTile(i, j, 0);
  }

  // Zeilenindex der leeren Kachel
  public int getEmptyTileRow() {
    return this.board.indexOf(0) / size + 1;
  }

  // Gibt Spaltenindex der leeren Kachel zurück.
  public int getEmptyTileColumn() {
    return this.board.indexOf(0) - ((getEmptyTileRow() - 1) * this.size) + 1;
  }

  // Gibt Anzahl der Zeilen (= Anzahl der Spalten) des Boards zurück.
  public int size() {
    return this.size;
  }

  // Überprüft, ob Position der Kacheln konsistent ist.
  public boolean isValid() {
    for (int i = 0; i < (size * size) - 1; i++) {
      if (!this.board.contains(i)) {
        return false;
      }
    }
    return true;
  }

  // Macht eine tiefe Kopie des Boards.
  // Vorsicht: Referenztypen müssen neu allokiert und anschließend deren Inhalt
  // kopiert werden.
  public Board copy() {
    Board result = new Board(this.size);
    result.board.clear();
    result.board.addAll(this.board);
    return result;
  }

  // Erzeugt eine zufällige lösbare Konfiguration des Boards, indem auf die
  // bestehende
  // Konfiguration eine Reihe zufälliger Verschiebeoperationen angewandt wird.
  public void shuffle() {
    Random rnd = new Random(System.nanoTime());
    for (int i = 0; i < 100000; i++) {
      int rndNummer = rnd.nextInt(4);
      try {
        switch (rndNummer) {
          case 0:
            moveDown();
            break;
          case 1:
            moveUp();
            break;
          case 2:
            moveRight();
            break;
          case 3:
            moveLeft();
            break;
        }

      } catch (IllegalMoveException e) {
        // Nothing to do
      }
    }
  }

  // Verschiebt leere Kachel auf neue Position (row, col).
  // throws IllegalMoveException
  public void move(int row, int col) {
    if (checkCoordinates(row, col))
      throw new IllegalMoveException("Cannot move to (" + row + ", " + col + ")");
    int curRow = getEmptyTileRow();
    int curCol = getEmptyTileColumn();
    if (!((Math.abs(curRow - row) == 1 && (curCol - col == 0))
            || (curRow - row == 0 && Math.abs(curCol - col) == 1)))
      throw new IllegalMoveException("Move row: " + row + " col: " + col + " is illegal.");
    int tile = getTile(row, col);
    setEmptyTile(row, col);
    setTile(curRow, curCol, tile);
  }

  // Verschiebt leere Kachel nach links. Wirft Laufzeitausnahme
  // IllegalMoveException.
  public void moveLeft() {
    move(getEmptyTileRow(), getEmptyTileColumn() - 1);
  }

  // Verschiebt leere Kachel nach rechts. Wirft IllegalMoveException.
  public void moveRight() {
    move(getEmptyTileRow(), getEmptyTileColumn() + 1);
  }

  // Verschiebt leere Kachel nach oben. Wirft IllegalMoveException.
  public void moveUp() {
    move(getEmptyTileRow() - 1, getEmptyTileColumn());
  }

  // Verschiebt leere Kachel nach unten. Wirft IllegalMoveException.
  public void moveDown() {
    move(getEmptyTileRow() + 1, getEmptyTileColumn());
  }

  // Führt eine Sequenz an Verschiebeoperationen durch. Wirft
  // IllegalMoveException.
  public void makeMoves(List<Move> moves) {
    moves.forEach(m -> move(m.getRow(), m.getCol()));
  }

  private class InvalidBoardIndexException extends BoardException {
    public InvalidBoardIndexException(String message) {
      super(message);
    }
  }

  private class BoardException extends RuntimeException {
    public BoardException(String message) {
      super(message);
    }
  }

  private class IllegalMoveException extends BoardException {
    public IllegalMoveException(String message) {
      super(message);
    }
  }
}
