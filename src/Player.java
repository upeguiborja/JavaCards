import java.util.Vector;

public class Player {
  private final String name;
  private Card[] cards;
  private int[] cardCount;

  public Player(String name, Card[] cards) {
    this.name = name;
    this.cards = cards;

    if (cards != null) {
      countCards();
    }
  }

  public String getName() {
    return name;
  }

  public Card[] getCards() {
    return cards;
  }

  public void setCards(Card[] cards) {
    this.cards = cards;
    countCards();
  }

  public void countCards() {
    if (cards == null) {
      return;
    }

    // Reiniciar el contador de cartas
    cardCount = new int[Card.Name.values().length];

    for (Card card : cards) {
      cardCount[card.getIndex() % 13]++;
    }
  }

  // En este juego donde solo hay única baraja de 52 cartas no pueden existir quintas, sextas, etc. por lo que solo se
  // implementarán los métodos para obtener pares, ternas y cuartas de cartas. En caso de que se desee implementar para
  // obtener quintas, sextas, etc. se deberán tener en cuenta que se necesitarán más de una baraja de 52 cartas.
  public Vector<Card.Name> getPairs() {
    // Método para obtener los pares de cartas
    Vector<Card.Name> pairs = new Vector<>();

    for (int i = 0; i < Card.Name.values().length; i++) {
      if (cardCount[i] == 2) {
        pairs.add(Card.Name.values()[i]);
      }
    }

    return pairs;
  }

  public Vector<Card.Name> getTrips() {
    // Método para obtener las ternas de cartas
    Vector<Card.Name> trips = new Vector<>();

    for (int i = 0; i < Card.Name.values().length; i++) {
      if (cardCount[i] == 3) {
        trips.add(Card.Name.values()[i]);
      }
    }

    return trips;
  }

  public Vector<Card.Name> getQuads() {
    // Método para obtener las cuartas de cartas
    Vector<Card.Name> quads = new Vector<>();

    for (int i = 0; i < Card.Name.values().length; i++) {
      if (cardCount[i] == 4) {
        quads.add(Card.Name.values()[i]);
      }
    }

    return quads;
  }
}
