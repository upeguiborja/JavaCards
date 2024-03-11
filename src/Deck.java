import java.util.ArrayList;
import java.util.Collections;

public class Deck {
  // Clase para representar una baraja de cartas
  private ArrayList<Card> cards;

  public Deck() {
    cards = new ArrayList<>();
    for (int i = 0; i < 52; i++) {
      cards.add(new Card(i));
    }
  }

  public ArrayList<Card> getCards() {
    return cards;
  }

  public void shuffleCards() {
    // Barajar las cartas de la baraja
    Collections.shuffle(cards);
  }

  public ArrayList<Card> takeCards(int n) throws IllegalArgumentException {
    // Tomar n cartas de la baraja
    if (n < 0 || n > 52) {
      throw new IllegalArgumentException("Number of cards to take must be between 0 and 52.");
    }

    if (n > cards.size()) {
      throw new IllegalArgumentException("Not enough cards in the deck.");
    }

    ArrayList<Card> takenCards = new ArrayList<>();

    for (int i = 0; i < n; i++) {
      takenCards.add(cards.get(i));
      cards.remove(i);
    }

    return takenCards;
  }

  public void resetCards() {
    // Reiniciar la baraja
    cards = new ArrayList<>();
    for (int i = 0; i < 52; i++) {
      cards.add(new Card(i));
    }
  }
}
