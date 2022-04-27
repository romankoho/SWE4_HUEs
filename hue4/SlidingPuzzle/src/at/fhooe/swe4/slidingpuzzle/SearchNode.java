package at.fhooe.swe4.slidingpuzzle;

import java.util.List;

public class SearchNode implements Comparable<SearchNode> {

    // Suchknoten mit Board-Konfiguration initialisieren.
    public SearchNode(Board board) {
      
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

    // Gibt geschätzte Kosten bis zum Zielknoten zurück.  Die Abschätzung
    // kann mit der Summe der Manhatten-Distanzen aller Kacheln erfolgen.
    public int estimatedCostsToTarget() {
      return 0;
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
    public boolean equals(Object other) {
      return false;
    }
  
    // Vergleicht zwei Knoten auf Basis der geschätzten Gesamtkosten.
    // <1: Kosten dieses Knotens sind geringer als Kosten von other.
    //   0: Kosten dieses Knotens und other sind gleich.
    // >1: Kosten dieses Knotens sind höher als Kosten von other.
    public int compareTo(SearchNode other) {
      return 0;
    }

    // Konvertiert die Knotenliste, die bei diesem Knoten ihren Ausgang hat,
    // in eine Liste von Zügen. Da der Weg in umgekehrter Reihenfolge gespeichert
    // ist, muss die Zugliste invertiert werden.
    public List<Move> toMoves() {
      return null;
    }
}
