package at.fhooe.swe4.slidingpuzzle;

import java.util.*;

public class SlidingPuzzle {

    // Berechnet die Zugfolge, welche die gegebene Board-Konfiguration in die
    // Ziel-Konfiguration überführt.
    // Wirft NoSolutionException (Checked Exception), falls es eine keine derartige 
    // Zugfolge gibt.
    public static List<Move> solve(Board board) {
        if(!board.isSolvable()) {
            throw new NoSolutionException();
        }

        Queue<SearchNode> openList = new PriorityQueue<>();
        Set<SearchNode> closedList = new HashSet<>();

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
                boolean test = myContains(closedList, succ);
                if(!myContains(closedList, succ)) {
                    openList.add(succ);
                }
            }
        }

        //this should never happen, just need a return statement
        return startNode.toMoves();
    }

    private static boolean myContains(Set<SearchNode> successors, SearchNode other) {
        for(SearchNode e:successors) {
            if (e.equals(other)) {
                return true;
            }
        }
        return false;
    }

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

            SearchNode successorNode = new SearchNode(nextBoard);
            successorNode.setPredecessor(currentNode);
            successorNode.setCostsFromStart(currentNode.costsFromStart()+1);
            return successorNode;
        } catch (IllegalMoveException e){
            return null;
        }
    }


    // Gibt die Folge von Board-Konfigurationen auf der Konsole aus, die sich durch
    // Anwenden der Zugfolge moves auf die Ausgangskonfiguration board ergibt.
    public static void printMoves(Board board, List<Move> moves) {
    }

}
