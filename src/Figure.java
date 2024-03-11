import java.util.ArrayList;

public class Figure {
  private final Type type;
  private final ArrayList<Card> cards;

  public Figure(Type type, ArrayList<Card> cards) {
    this.type = type;
    this.cards = cards;
  }

  public ArrayList<Card> getCards() {
    return cards;
  }

  public String toString() {
    StringBuilder figureString = new StringBuilder();

    if (type == Type.FLUSH) {
      figureString.append("Escalera de ").append(cards.getFirst().getSuitString().toLowerCase()).append(" [");

      for (int i = 0; i < cards.size(); i++) {
        figureString.append(cards.get(i).getNameString());

        if (i < cards.size() - 1) {
          figureString.append(", ");
        }
      }

      figureString.append("]");
      return figureString.toString();
    } else if (type == Type.QUADS) {
      figureString.append("Poker de ").append(cards.getFirst().getNameString());
    } else if (type == Type.TRIPS) {
      figureString.append("Trio de ").append(cards.getFirst().getNameString());
    } else if (type == Type.PAIR) {
      figureString.append("Par de ").append(cards.getFirst().getNameString());
    }

    return figureString.toString();
  }

  public enum Type {
    PAIR, TRIPS, QUADS, FLUSH
  }
}
