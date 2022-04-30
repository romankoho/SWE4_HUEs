package at.fhooe.swe4.slidingpuzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.abs;

public class SearchNode implements Comparable<SearchNode> {

    private Board boardConfig;
    private SearchNode predecessor = null;

    //cost from start position to this current state
    private int costsFromStart;

    //cost from current position to target position
    private int estimatedCostsToTarget;

    // Suchknoten mit Board-Konfiguration initialisieren.
    public SearchNode(Board board) {
        boardConfig = board;
        estimatedCostsToTarget = calculateEstimatedCostsToTarget();
    }

    // Gibt Board-Konfiguration dieses Knotens zurück.
    public Board getBoard() {
      return boardConfig;
    }

    // Gibt Referenz auf Vorgängerknoten zurück.
    public SearchNode getPredecessor() {
      return predecessor;
    }

    // Setzt den Verweis auf den Vorgängerknoten.
    public void setPredecessor(SearchNode predecessor) {
      this.predecessor = predecessor;
    }

    // Gibt Kosten (= Anzahl der Züge) vom Startknoten bis zu diesem Knoten zurück.
    public int costsFromStart() {
        return costsFromStart;
    }

    // Gibt geschätzte Kosten bis zum Zielknoten zurück.  Die Abschätzung
    // kann mit der Summe der Manhatten-Distanzen aller Kacheln erfolgen.
    public int estimatedCostsToTarget() {
        return estimatedCostsToTarget;
    }

    private int getTargetRow(int number) {
        if(number % boardConfig.size() == 0) {
            return number / boardConfig.size();
        } else {
            return (number / boardConfig.size()) + 1;
        }
    }

    private int getTargetCol(int number) {
        if(number % boardConfig.size() == 0) {
            return boardConfig.size();
        } else {
            return number % boardConfig.size();
        }
    }

    private int calculateEstimatedCostsToTarget() {
        int heuristic = 0;

        for(int row = 1; row <= boardConfig.size(); row++) {
            for (int col = 1; col <= boardConfig.size(); col++) {
                int curTile = boardConfig.getTile(row, col);
                if (curTile != 0) {
                    heuristic += calculateManhatten(curTile, row, col);
                }
            }
        }
        return heuristic;
    }

    private int calculateManhatten(int number, int currRow, int currCol) {
        int targetRow = getTargetRow(number);
        int targetCol = getTargetCol(number);

        int rowOffset = abs(currRow - targetRow);
        int colOffset = abs(currCol - targetCol);

        return rowOffset+colOffset;
    }
  
    // Setzt die Kosten vom Startknoten bis zu diesem Knoten.
    public void setCostsFromStart(int costsFromStart) {
      this.costsFromStart = costsFromStart;
    }


    // Gibt Schätzung der Wegkosten vom Startknoten über diesen Knoten bis zum
    // Zielknoten zu-rück.
    public int estimatedTotalCosts() {
        return costsFromStart + estimatedCostsToTarget;
    }

    // Gibt zurück, ob dieser Knoten und der Knoten other dieselbe
    // Board-Konfiguration darstellen.
    // Vorsicht: Knotenkonfiguration vergleichen, nicht die Referenzen.
    public boolean equals(Object other) {
        if (this == other) return true;
        if(other == null || getClass() != other.getClass()) return false;

        SearchNode o = (SearchNode) other;
        return Objects.equals(boardConfig, o.boardConfig) &&
                Objects.equals(costsFromStart, o.costsFromStart) &&
                Objects.equals(estimatedCostsToTarget, o.estimatedCostsToTarget);
    }
  
    // Vergleicht zwei Knoten auf Basis der geschätzten Gesamtkosten.
    // <1: Kosten dieses Knotens sind geringer als Kosten von other.
    //   0: Kosten dieses Knotens und other sind gleich.
    // >1: Kosten dieses Knotens sind höher als Kosten von other.
    public int compareTo(SearchNode other) {
      return this.estimatedTotalCosts() - other.estimatedTotalCosts();
    }

    // Konvertiert die Knotenliste, die bei diesem Knoten ihren Ausgang hat,
    // in eine Liste von Zügen. Da der Weg in umgekehrter Reihenfolge gespeichert
    // ist, muss die Zugliste invertiert werden.
    public List<Move> toMoves() {
      ArrayList<Move> movesUntilHere = new ArrayList<>();
        SearchNode current = this;
        while(current.predecessor != null) {
            Board curBoard = current.boardConfig;
            Board prevBoard = current.predecessor.getBoard();
            Move madeMove = calculateMove(curBoard, prevBoard);
            movesUntilHere.add(madeMove);
            current = current.predecessor;
        }
        Collections.reverse(movesUntilHere);
        return movesUntilHere;
    }

    private Move calculateMove(Board curBoard, Board prevBoard) {
        Move result = Move.LEFT;
        int curBoardRow = curBoard.getEmptyTileRow();
        int curBoardCol = curBoard.getEmptyTileColumn();
        int prevBoardRow = prevBoard.getEmptyTileRow();
        int prevBoardCol = prevBoard.getEmptyTileColumn();

        int verticalMove = curBoardRow - prevBoardRow;
        int horizontalMove = curBoardCol - prevBoardCol;

        if(horizontalMove != 0 && verticalMove != 0) {
            throw new IllegalMoveException(curBoardRow, curBoardCol, prevBoardRow, prevBoardCol);
        }

        //must be left or right move
        if(horizontalMove != 0) {
            if(horizontalMove == 1) {
                result = Move.RIGHT;
            } else {        //horizontalMove == -1
                result = Move.LEFT;
            }
        }

        //must be up or down move
        if(verticalMove != 0) {
            if(verticalMove == 1) {
                result = Move.DOWN;
            } else {        //verticalMove == -1
                result = Move.UP;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "SearchNode:\n" + boardConfig + "\n" +
                "costs from start: " + costsFromStart + " | " +
                "costs to target: " + estimatedCostsToTarget + "\n";
    }
}


