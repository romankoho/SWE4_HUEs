//file Board.java

package at.fhooe.swe4.slidingpuzzle;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.util.*;

import static java.lang.Math.abs;

public class Board implements Comparable<Board> {
  private final List board;
  private int size;

  /**
   * Calculcates the array index for a given row and column (row and column start with 1)
   * @param row = input row
   * @param col = input column
   * @return = array index for this row and column
   */
  private int getArrayIndex(int row, int col) {
    if(row < 1 || row > size || col < 1 || col > size) {
      throw new ArrayIndexOutOfBoundsException("indices must be > 0 and <= size");
    }
    int nRow = row-1;
    int nCol = col-1;

    return nRow*size+nCol;
  }

  /**
   * Constructor for board. Initializes board 1 to n*n-1. Last position of array (n|n) holds 0 cell.
   * Size must be 3 or 4. If other size is chosen board will be initialized with size 3
   * @param size = size of board (for a 3x3 board enter 3 and NOT 9)
   */
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

  /**
   * Constructor taking a one dimensional list as start board
   * @param boardInput = list representing start baord
   * @param size = size of board (for a 3x3 board enter 3 and NOT 9)
   */
  public Board(List boardInput, int size) {
    board = new ArrayList<Integer>();
    board.addAll(boardInput);
    this.size = size;
  }


  /**
   * @return string representation of board
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for(int row = 1; row <= size; row++) {
      for(int col = 1; col <= size; col++) {
        sb.append(board.get(getArrayIndex(row, col)));
        sb.append("\t");
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  /**
   * checks if boards have the same configuration (board and size are considered)
   * @param other = the other board
   * @return = true if this and other are the same, else false
   */
  public boolean equals(Object other) {
    if (this == other) return true;
    if(other == null || getClass() != other.getClass()) return false;

    Board o = (Board)other;
    return board.equals(o.board) &&
            size == o.size;
  }

  /**
   * @return hashCode for board (considering board and size)
   */
  @Override
  public int hashCode() {
    return Objects.hash(board, size);
  }

  /**
   * @param other the object to be compared.
   * @return < 1 if this is smaller than other; 0 if they are the same, >1 if this is larger than oterh
   */
  public int compareTo(Board other) {
    if (size < other.size) {
      return -1;
    }
    else if (size == other.size) {
      return 0;
    } else
      return 1;
  }

  /**
   * Gets the value of a certain tile
   * @param i = row of tile
   * @param j = column of tile
   * @return = number of tile at position i|j
   * throws InvalidBoardIndexException if i or j are < 1 or > size
   */
  public int getTile(int i, int j) {
    if(i < 1 || i > size || j < 1 || j > size) {
      throw new InvalidBoardIndexException(i,j,size);
    }

    Integer val = (Integer) board.get(getArrayIndex(i,j));
    return val;
  }

  /**
   * Sets a tile to input number
   * @param i = row of tile to be set
   * @param j = column of tile to be set
   * @param number = value which should be set on position i|j
   * throws InvalidBoardIndexException if i or j are < 1 or > size
   * throws InvalidTileNumberException if number < 1 or > size*size-1
   */
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

  /**
   * Sets a certain position to 0 (it could be possible to set several tiles to 0 with this method)
   * @param i = row of tile which should be set to 0
   * @param j = column of tile which should be set to 0
   * throws InvalidBoardIndexException if i or j are < 1 or > size
   */
  public void setEmptyTile(int i, int j) {
    if(i < 1 || i > size || j < 1 || j > size) {
      throw new InvalidBoardIndexException(i,j,size);
    }

    setTile(i,j,0);
  }


  /**
   * @return row number of empty tile (rows start with 1)
   */
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

  /**
   * @return column number of empty tile (columns start with 1)
   */
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

  /**
   * @return number of rows / columns
   */
  public int size() {
    return size;
  }

  // Überprüft, ob Position der Kacheln konsistent ist.

  /**
   * checks if a board is valid.
   * @return true if all numbers from 0 to size-1 is once in board (irrespective of order)
   */
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

  /**
   * Makes a deep copy of the board and returns the new board
   * @return the copied board
   */
  public Board copy() {
    Board copy = new Board(size);
    for(int i = 0; i < size*size; i++) {
      copy.board.set(i, board.get(i));
    }
    return copy;
  }

  /**
   * Checks if a board is solvable
   * @return true if it is solvable, otherwise false
   */
  public boolean isSolvable() {
    int inversions = 0;

    for(int i = 0; i < board.size() - 1; i++) {
      for(int j = i + 1; j < board.size(); j++)
        if((Integer) board.get(i) > (Integer) board.get(j)) inversions++;
      if((Integer) board.get(i) == 0 && i % 2 == 1) inversions++;
    }

    /*
      If size is odd, then number of inversions must be even for board to be solvable.
      If size is even, then number of inversions+row_number_of_empty_cell (starting with 1) must be odd for board to be solvable.
    */
    if (size % 2 == 1) {
      return (inversions % 2 == 0);
    } else {
      int blankRow = getEmptyTileRow();
      int test = inversions+blankRow-1;
      return (test % 2 == 1);
    }
  }

  /**
   * Shuffles a board but guarantees that it is still solvable
   */
  public void shuffle() {

    Collections.shuffle(board);
    while(!this.isSolvable()) {
      Collections.shuffle(board);
    }
  }

  /**
   * Checks if a tile is a neighbour of the tile containing 0
   * @param row = row to be checked
   * @param col = column to be checked
   * @return true if the cell is a neighbour of the empty cell otherwise false
   * throws InvalidBoardIndexException if row or colum < 1 or > size
   */
  private boolean isNeighbourOfEmptyField(int row, int col) {
    if(row < 1 || row > size || col < 1 || col > size) {
      throw new InvalidBoardIndexException(row,col,size);
    }

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

  /**
   * Moves empty tile to this position. In fact the empty tile and the chosen tile will be exchanged.
   * Only works if empty tile and inputted tile are neighbours. Otherwise IllegalMoveException will be thrown
   * @param row = row which should be set to 0
   * @param col = column which should be set to 0
   */
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

  /**
   * Moves the empty tile to the left
   * throws IllegalMoveExcpetion if the column of the empty tile is already 1
   */
  public void moveLeft() {
    int rowEmpty = getEmptyTileRow();
    int colEmpty = getEmptyTileColumn();

    if(colEmpty < 2) {
      throw new IllegalMoveException(rowEmpty, colEmpty-1, rowEmpty, colEmpty);
    } else {
      move(rowEmpty, colEmpty-1);
    }
  }

  /**
   * Moves the empty tile to the right
   * throws IllegalMoveExcpetion if the column of the empty tile is already the same as size
   */
  public void moveRight() {
    int rowEmpty = getEmptyTileRow();
    int colEmpty = getEmptyTileColumn();

    if(colEmpty > size-1) {
      throw new IllegalMoveException(rowEmpty, colEmpty+1, rowEmpty, colEmpty);
    } else {
      move(rowEmpty, colEmpty+1);
    }
  }

  /**
   * Moves the empty tile up
   * throws IllegalMoveExcpetion if the row of the empty tile is already 1
   */
  public void moveUp() {
    int rowEmpty = getEmptyTileRow();
    int colEmpty = getEmptyTileColumn();

    if(rowEmpty < 2) {
      throw new IllegalMoveException(rowEmpty-1, colEmpty, rowEmpty, colEmpty);
    } else {
      move(rowEmpty-1, colEmpty);
    }
  }

  /**
   * Moves the empty tile down
   * throws IllegalMoveExcpetion if the row of the empty tile is already the same as size
   */
  public void moveDown() {
    int rowEmpty = getEmptyTileRow();
    int colEmpty = getEmptyTileColumn();

    if(rowEmpty > size-1) {
      throw new IllegalMoveException(rowEmpty+1, colEmpty, rowEmpty, colEmpty);
    } else {
      move(rowEmpty+1, colEmpty);
    }
  }

  /**
   * Peeks and returns potential new row number of empty tile if move would be made
   * @param m = the move to be made (up or down)
   * @return the row number of empty tile if move is made
   */
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

  /**
   * Peeks and returns potential new column number of empty tile if move would be made
   * @param m = the move to be made (left or right)
   * @return the column number of empty tile if move is made
   */
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

  /**
   * Performs a sequence of move operations
   * @param moves = a list of moves
   * throw IllegalMOveException if the move can't be made because the empty tile cannot be moved in this direction anymore
   */
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

  /**
   * checks if a board is solved (means it is sorted but the 0 is on the last position)
   * @return true if board is solved
   */
  public boolean isSolved() {
    for(int i = 0, shouldVal = 1; i < size*size-1; i++, shouldVal++) {
      Integer isVal = (Integer)board.get(i);
      if(shouldVal != isVal) {
        return false;
      }
    }
    int last = (Integer) board.get(size*size-1);
    return last == 0;
  }
}

