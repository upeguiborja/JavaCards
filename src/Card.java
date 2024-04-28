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

  public String getSuitString() {
    if (suit == Suit.CLUB) {
      return "Tréboles";
    } else if (suit == Suit.SPADE) {
      return "Picas";
    } else if (suit == Suit.HEART) {
      return "Corazones";
    } else {
      return "Diamantes";
    }
  }

  public String getNameString() {
    if (name == Name.ACE) {
      return "As";
    } else if (name == Name.TWO) {
      return "Dos";
    } else if (name == Name.THREE) {
      return "Tres";
    } else if (name == Name.FOUR) {
      return "Cuatro";
    } else if (name == Name.FIVE) {
      return "Cinco";
    } else if (name == Name.SIX) {
      return "Seis";
    } else if (name == Name.SEVEN) {
      return "Siete";
    } else if (name == Name.EIGHT) {
      return "Ocho";
    } else if (name == Name.NINE) {
      return "Nueve";
    } else if (name == Name.TEN) {
      return "Diez";
    } else if (name == Name.JACK) {
      return "Jota";
    } else if (name == Name.QUEEN) {
      return "Reina";
    } else {
      return "Rey";
    }
  }

  public int getIndex() {
    return index;
  }

  public int getValue() {
    if (name.ordinal() > 9) {
      return 10;
    }

    return name.ordinal() + 1;
  }

  public enum Suit {
    CLUB, SPADE, HEART, DIAMOND
  }

  public enum Name {
    ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
  }
}
