public class Dealer {
  // Clase para representar al repartidor de cartas
  private Deck deck;

  public Dealer() {
    // Inicializar la baraja
    deck = new Deck();
  }

  public void dealCards(Player[] players, int n) throws IllegalArgumentException {
    if (players == null || players.length == 0 || players.length > 5) {
      throw new IllegalArgumentException("Number of players must be between 1 and 5");
    }

    if (deck.getCards().length < players.length * n) {
      deck.resetCards();
    }

    // Barajar la baraja
    deck.shuffleCards();

    // Repartir 10 cartas a cada jugador
    for (Player player : players) {
      player.setCards(deck.takeCards(n));
    }
  }
}

