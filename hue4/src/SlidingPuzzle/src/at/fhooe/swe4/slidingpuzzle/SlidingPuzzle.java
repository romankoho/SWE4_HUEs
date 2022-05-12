package at.fhooe.swe4.slidingpuzzle;

import java.util.*;

public class SlidingPuzzle {

    private static Queue<SearchNode> openList = new PriorityQueue<>();
    private static Set<SearchNode> closedList = new HashSet<>();

    /**
     * Solves a puzzle and returns a list of moves necessary to solve it
     * @param board the board to be solved
     * @return the list of moves to be made to solve the board
     * throws NoSolutionException
     */
    public static List<Move> solve(Board board) {
        openList.clear();
        closedList.clear();

        if(!board.isSolvable()) {
            throw new NoSolutionException();
        }
        SearchNode startNode = new SearchNode(board);
        startNode.setPredecessor(null);
        startNode.setCostsFromStart(0);
        openList.add(startNode);

        while(!openList.isEmpty()) {
            SearchNode currentNode = openList.poll();
            if(currentNode.getBoard().isSolved()) {
                return currentNode.toMoves();
            }
            closedList.add(currentNode);

            List<SearchNode> successors = calculateSuccessors(currentNode);
            for(SearchNode succ:successors) {
                if(!closedList.contains(succ)) {
                    openList.add(succ);
                }
            }
        }
        return null;
    }

    /**
     * Makes up to four possible moves from a certain starting point (left, right, up, down)
     * For each new state a node is created and linked to the predecessor
     * @param currentNode = the starting state from which the next possible states are created
     * @return a list of the next states
     */
    private static List<SearchNode> calculateSuccessors(SearchNode currentNode) {
        List<Move> possibleMoves = Arrays.asList(Move.LEFT, Move.RIGHT, Move.UP, Move.DOWN);
        ArrayList<SearchNode> successors = new ArrayList<>();
        for(Move m:possibleMoves) {
            SearchNode nextSuccessor = calculateSuccessor(currentNode, m);
            if(nextSuccessor != null) {
                successors.add(nextSuccessor);
            }
        }
        return successors;
    }

    /**
     * Creates a new node for one particular move (m) based on the current search node
     * @param currentNode = the current state
     * @param m = the move to be made
     * @return a new searchNode with the new board configuration and costs or null if the move can't be made
     */
    private static SearchNode calculateSuccessor(SearchNode currentNode, Move m) {
        Board nextBoard = currentNode.getBoard().copy();
        try {
            switch (m) {
                case RIGHT:
                    nextBoard.moveRight();
                    break;
                case LEFT:
                    nextBoard.moveLeft();
                    break;
                case UP:
                    nextBoard.moveUp();
                    break;
                case DOWN:
                    nextBoard.moveDown();
                    break;
            }

            //estimatedCostsToTarget are set automatically
            SearchNode successorNode = new SearchNode(nextBoard);
            successorNode.setPredecessor(currentNode);
            successorNode.setCostsFromStart(currentNode.costsFromStart()+1);
            return successorNode;
        } catch (IllegalMoveException e){
            return null;
        }
    }

    /**
     * Prints the sequence of boards that result in executing all the moves
     * @param board = the start board
     * @param moves = the list of moves that will be applied to the start board
     */
    public static void printMoves(Board board, List<Move> moves) {
        System.out.println(board.toString());
        for (Move m:moves) {
            switch(m) {
                case DOWN:
                    board.moveDown();
                    break;
                case UP:
                    board.moveUp();
                    break;
                case LEFT:
                    board.moveLeft();
                    break;
                case RIGHT:
                    board.moveRight();
                    break;
            }
            System.out.println(board.toString());
        }
    }

}
