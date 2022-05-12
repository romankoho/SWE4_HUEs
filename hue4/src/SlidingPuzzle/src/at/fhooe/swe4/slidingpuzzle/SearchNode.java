//file: SearchNode.java

package at.fhooe.swe4.slidingpuzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.abs;

public class SearchNode implements Comparable<SearchNode> {

    private Board boardConfig;
    private SearchNode predecessor = null;

    //costs=steps from start position to this current state
    private int costsFromStart;

    //cost from current position to target position
    private int estimatedCostsToTarget;

    /**
     * Constructor taking a board configuration
     * The estimatedCostsToTarget will be calculated automatically
     *      (as it can be calculated from the board configuration)
     *      predecessor and costsFromStart must be "manually" set
     * @param board = input board
     */
    // Suchknoten mit Board-Konfiguration initialisieren.
    public SearchNode(Board board) {
        boardConfig = board;
        estimatedCostsToTarget = calculateEstimatedCostsToTarget();
    }

    /**
     * Getter for board
     * @return board
     */
    public Board getBoard() {
      return boardConfig;
    }

    /**
     * Getter for predecessor
     * @return predecessor
     */
    public SearchNode getPredecessor() {
      return predecessor;
    }

    /**
     * Setter for predecessor. Sets predecessor to input value
     * @param predecessor
     */
    public void setPredecessor(SearchNode predecessor) {
      this.predecessor = predecessor;
    }

    /**
     * Getter for costsFromStart
     * @return costsFromStart
     */
    public int costsFromStart() {
        return costsFromStart;
    }


    /**
     * Getter for estimatedCostsToTarget
     * @return estimatedCostsToTarget
     */
    public int estimatedCostsToTarget() {
        return estimatedCostsToTarget;
    }

    /**
     * Calculates the target row for a certain number/tile
     * @param number = number for which target row is calculated
     * @return the target row of the input number
     */
    private int getTargetRow(int number) {
        if(number % boardConfig.size() == 0) {
            return number / boardConfig.size();
        } else {
            return (number / boardConfig.size()) + 1;
        }
    }

    /**
     * Calculates the target column for a certain number/tile
     * @param number = number for which target column is calculated
     * @return the target column of the input number
     */
    private int getTargetCol(int number) {
        if(number % boardConfig.size() == 0) {
            return boardConfig.size();
        } else {
            return number % boardConfig.size();
        }
    }

    /**
     * Calculates the estimatedCostsToTarget using the Manhatten distance
     * @return estimated costs to target
     */
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

    /**
     * Calculates for a number and it's current row and column the Manhatten distance to it's target position
     * @param number = number for which distance should be calculated
     * @param currRow = the current row position of the number
     * @param currCol = the current column position of the number
     * @return
     */
    private int calculateManhatten(int number, int currRow, int currCol) {
        int targetRow = getTargetRow(number);
        int targetCol = getTargetCol(number);

        int rowOffset = abs(currRow - targetRow);
        int colOffset = abs(currCol - targetCol);

        return rowOffset+colOffset;
    }

    /**
     * Setter for costsFromStart
     * @param costsFromStart
     */
    public void setCostsFromStart(int costsFromStart) {
      this.costsFromStart = costsFromStart;
    }


    /**
     * @return sum of costsFromStart + estimatedCostsToTarget
     */
    public int estimatedTotalCosts() {
        return costsFromStart + estimatedCostsToTarget;
    }

    /**
     * Compares two nodes based on the boardConfiguration only (doesn't consider cost components of node)
     * @param other = the node with which this node is returned
     * @return true if nodes are the same, othewise false
     */
    public boolean equals(Object other) {
        if (this == other) return true;
        if(other == null || getClass() != other.getClass()) return false;

        SearchNode o = (SearchNode) other;
        return Objects.equals(boardConfig, o.boardConfig);
    }

    /**
     * Compares nodes based on estimated total costs
     * @param other the object to be compared.
     * @return <1 if costs of this node are smaller than other, 0 if costs are the same; 1 if costs of this node are larger
     */
    public int compareTo(SearchNode other) {
      return this.estimatedTotalCosts() - other.estimatedTotalCosts();
    }

    /**
     * Gets a hashcode for a node based on the baordConfiguration (doesn't consider cost components)
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(boardConfig);
    }

    /**
     * Returns a list of moves that were made from the start board to this current node
     * @return list of moves from start configuration to current configuration
     */
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

    /**
     * Calculates the move that was made between two board configurations
     * @param curBoard the current board configuration
     * @param prevBoard the previous board configuration (starting board before making the move)
     * @return the respective move (left, right, up, down) of null if the boards differ be more than one move
     */
    private Move calculateMove(Board curBoard, Board prevBoard) {
        Move result = null;
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
            } else if(horizontalMove == -1) {
                result = Move.LEFT;
            }
        }

        //must be up or down move
        if(verticalMove != 0) {
            if(verticalMove == 1) {
                result = Move.DOWN;
            } else if (verticalMove == -1) {
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


