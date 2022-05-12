import java.util.ArrayList;
import java.util.List;

public class SearchNode implements Comparable<SearchNode> {

  private Board board;
  private SearchNode getPredecessor;
  private int sumCosts;
  private Move move;

  // Suchknoten mit Board-Konfiguration initialisieren.
  public SearchNode(Board board) {
    this.board = board;

  }

  // Gibt Board-Konfiguration dieses Knotens zurück.
  public Board getBoard() {
    return null;
  }

  // Gibt Referenz auf Vorgängerknoten zurück.
  public SearchNode getPredecessor() {
    return null;
  }

  // Setzt den Verweis auf den Vorgängerknoten.
  public void setPredecessor(SearchNode predecessor) {

  }

  // Gibt Kosten (= Anzahl der Züge) vom Startknoten bis zu diesem Knoten zurück.
  public int costsFromStart() {
    return 0;
  }

  // Gibt geschätzte Kosten bis zum Zielknoten zurück. Die Abschätzung
  // kann mit der Summe der Manhatten-Distanzen aller Kacheln erfolgen.
  public int estimatedCostsToTarget() {
    int result = 0;
    for (int x = 1; x <= board.size(); x++)
      for (int y = 1; y <= board.size(); y++) {
        int value = board.getTile(x, y);
        if (value != 0) {
          int targetX = (value - 1) / board.size();
          int targetY = (value - 1) % board.size();
          result += Math.abs(x - (targetX + 1)) + Math.abs(y - (targetY + 1));
        }
      }
    return result;
  }

  // Setzt die Kosten vom Startknoten bis zu diesem Knoten.
  public void setCostsFromStart(int costsFromStart) {

  }

  // Gibt Schätzung der Wegkosten vom Startknoten über diesen Knoten bis zum
  // Zielknoten zu-rück.
  public int estimatedTotalCosts() {
    return 0;
  }

  // Gibt zurück, ob dieser Knoten und der Knoten other dieselbe
  // Board-Konfiguration darstellen.
  // Vorsicht: Knotenkonfiguration vergleichen, nicht die Referenzen.
  public boolean equals(SearchNode other) {
    return this.board.equals(other.board);
  }

  // Vergleicht zwei Knoten auf Basis der geschätzten Gesamtkosten.
  // <1: Kosten dieses Knotens sind geringer als Kosten von other.
  // 0: Kosten dieses Knotens und other sind gleich.
  // >1: Kosten dieses Knotens sind höher als Kosten von other.
  public int compareTo(SearchNode other) {
    return other.sumCosts - this.sumCosts;
  }

  // Konvertiert die Knotenliste, die bei diesem Knoten ihren Ausgang hat,
  // in eine Liste von Zügen. Da der Weg in umgekehrter Reihenfolge gespeichert
  // ist, muss die Zugliste invertiert werden.
  public List<Move> toMoves() {
    List<Move> result = new ArrayList<Move>();
    SearchNode cur = this;
    while (cur != null) {
      if (cur.move != null) {
        result.add(cur.move);
      }
      cur = cur.getPredecessor();
    }
    // reverse the order of the collection
    // to get the moves from the start
    List<Move> tmp = new ArrayList<>();
    for (int i = 0; i < result.size(); i++) {
      tmp.add(result.get(result.size() - i - 1));
    }
    return result;
  }

  public void setMove(Move move) {
    this.move = move;
  }
}