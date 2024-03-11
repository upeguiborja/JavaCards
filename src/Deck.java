public class Deck {
  // Clase para representar una baraja de cartas
  private Card[] cards;

  public Deck() {
    this.cards = new Card[52];
    for (int i = 0; i < 52; i++) {
      this.cards[i] = new Card(i);
    }
  }

  public Card[] getCards() {
    return cards;
  }

  public void shuffleCards() {
    // Barajar las cartas de la baraja
    for (int i = 0; i < cards.length; i++) {
      int randomIndex = (int) (Math.random() * cards.length);
      Card temp = cards[i];
      cards[i] = cards[randomIndex];
      cards[randomIndex] = temp;
    }
  }

  public Card[] takeCards(int n) throws IllegalArgumentException {
    // Tomar n cartas de la baraja
    if (n < 0 || n > 52) {
      throw new IllegalArgumentException("Number of cards to take must be between 0 and 52.");
    }

    if (n > cards.length) {
      throw new IllegalArgumentException("Not enough cards in the deck.");
    }

    Card[] takenCards = new Card[n];

    for (int i = 0; i < n; i++) {
      takenCards[i] = cards[i];
    }

    Card[] remainingCards = new Card[cards.length - n];
    for (int i = n; i < cards.length; i++) {
      remainingCards[i - n] = cards[i];
    }
    this.cards = remainingCards;
    return takenCards;
  }

  public void resetCards() {
    // Reiniciar la baraja
    this.cards = new Card[52];
    for (int i = 0; i < 52; i++) {
      this.cards[i] = new Card(i);
    }
  }
}
