public class Card {
  // Clase para representar una carta de la baraja
  private final Suit suit;
  private final Name name;
  private final int index;

  public Card(int index) throws IllegalArgumentException {
    // Modificamos la tabla propuesta por el ejercicio para empezar en cero
    if (index < 0 || index > 51) {
      throw new IllegalArgumentException("Index must be between 0 and 51.");
    }

    this.suit = Suit.values()[index / 13];
    this.name = Name.values()[index % 13];
    this.index = index;
  }

  public Suit getSuit() {
    return suit;
  }

  public Name getName() {
    return name;
  }

  public int getIndex() {
    return index;
  }

  public enum Suit {
    CLUB, SPADE, HEART, DIAMOND
  }

  public enum Name {
    ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
  }

  public enum Figure {
    NONE, PAIR, TRIPS, QUADS
  }
}
