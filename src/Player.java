import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Player {
  private final String name;
  private ArrayList<Card> cards = new ArrayList<>();
  private Map<Card, Boolean> cardInFigure = new HashMap<>();
  private ArrayList<Card> sortedCards = new ArrayList<>();
  private ArrayList<Figure> figures = new ArrayList<>();
  private int score = 0;

  public Player(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public ArrayList<Card> getCards() {
    return cards;
  }

  public void setCards(ArrayList<Card> cards) {
    this.cards = cards;
  }

  public void sortCards() {
    cards.sort(Comparator.comparing(Card::getIndex));
  }

  private void computeFigures() {
    figures = new ArrayList<>();
    sortedCards = new ArrayList<>(cards);
    sortedCards.sort(Comparator.comparing(Card::getIndex));
    cardInFigure = new HashMap<>();

    // Encontrar escaleras si las hay, debe ser el primer paso
    ArrayList<Figure> flushes = findFlushes();
    // Encontrar cuartas, ternas y pares
    ArrayList<Figure> repeats = findRepeatFigures();

    figures.addAll(flushes);
    figures.addAll(repeats);
  }

  public ArrayList<Figure> getFigures() {
    computeFigures();
    return figures;
  }

  public int getScore() {
    computeScore();
    return score;
  }

  private void computeScore() {
    score = 0;

    for (Card card : cards) {
      if (!cardInFigure.getOrDefault(card, false)) {
        score += card.getValue();
      }
    }
  }

  private ArrayList<Figure> findFlushes() {
    // Método para encontrar las escaleras en la mano del jugador
    ArrayList<Figure> flushes = new ArrayList<>();

    for (int i = 0; i < sortedCards.size(); i++) {
      ArrayList<Card> currentFlush = new ArrayList<>();

      Card currentCard = sortedCards.get(i);
      currentFlush.add(currentCard);

      if (cardInFigure.getOrDefault(currentCard, false)) {
        continue;
      }

      for (int j = i + 1; j < sortedCards.size(); j++) {
        Card jthCard = sortedCards.get(j);

        if (cardInFigure.getOrDefault(jthCard, false)) {
          break;
        }

        if (jthCard.getSuit() != currentCard.getSuit()) {
          break;
        }

        if (currentFlush.get(currentFlush.size() - 1).getIndex() == jthCard.getIndex() - 1) {
          currentFlush.add(jthCard);
        }
      }

      if (currentFlush.size() >= 2) {
        Figure currentFigure = new Figure(Figure.Type.FLUSH, currentFlush);
        updateCardsInFigure(currentFigure, cardInFigure);
        flushes.add(currentFigure);
      }
    }

    return flushes;
  }

  private ArrayList<Figure> findRepeatFigures() {
    // Método para encontrar las cuartas, ternas y pares en la mano del jugador, excluyendo las escaleras ya encontradas
    ArrayList<Figure> repeats = new ArrayList<>();

    int[] cardsCount = new int[Card.Name.values().length];

    // Contar cuántas cartas hay de cada tipo excluyendo las que ya forman parte de una figura
    for (int i = 0; i < sortedCards.size(); i++) {
      Card currentCard = sortedCards.get(i);

      if (cardInFigure.getOrDefault(currentCard, false)) {
        continue;
      }

      cardsCount[currentCard.getName().ordinal()]++;
    }

    for (int i = 0; i < cardsCount.length; i++) {
      Figure currentFigure;

      // Encontramos las cuartas
      if (cardsCount[i] == 4) {
        ArrayList<Card> quads = new ArrayList<>();

        for (Card card : sortedCards) {
          if (card.getName().ordinal() == i) {
            quads.add(card);
          }
        }

        currentFigure = new Figure(Figure.Type.QUADS, quads);
        repeats.add(currentFigure);
        updateCardsInFigure(currentFigure, cardInFigure);
      }

      if (cardsCount[i] == 3) {
        ArrayList<Card> trips = new ArrayList<>();

        for (Card card : sortedCards) {
          if (card.getName().ordinal() == i) {
            trips.add(card);
          }
        }

        currentFigure = new Figure(Figure.Type.TRIPS, trips);
        repeats.add(currentFigure);
        updateCardsInFigure(currentFigure, cardInFigure);
      }

      if (cardsCount[i] == 2) {
        ArrayList<Card> pair = new ArrayList<>();

        for (Card card : sortedCards) {
          if (card.getName().ordinal() == i) {
            pair.add(card);
          }
        }

        currentFigure = new Figure(Figure.Type.PAIR, pair);
        repeats.add(currentFigure);
        updateCardsInFigure(currentFigure, cardInFigure);
      }
    }

    return repeats;
  }

  private void updateCardsInFigure(Figure figure, Map<Card, Boolean> cardInFigure) {
    // Método para actualizar las cartas que forman parte de una figura
    for (Card currentCard : figure.getCards()) {
      cardInFigure.put(currentCard, true);
    }
  }
}
