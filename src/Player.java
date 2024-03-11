import java.util.ArrayList;
import java.util.Comparator;

public class Player {
  private final String name;
  private ArrayList<Card> cards = new ArrayList<>();
  private boolean[] cardsInFigure = new boolean[0];
  private ArrayList<Figure> figures = new ArrayList<>();
  private int score = 0;

  public Player(String name) {
    this.name = name;
    findFiguresAndScore();
  }

  public String getName() {
    return name;
  }

  public ArrayList<Card> getCards() {
    return cards;
  }

  public void setCards(ArrayList<Card> cards) {
    this.cards = cards;
    cardsInFigure = new boolean[cards.size()];
    figures = new ArrayList<>();
    findFiguresAndScore();
  }

  public ArrayList<Figure> getFigures() {
    return figures;
  }

  public int getScore() {
    return score;
  }

  public void sortCards() {
    if (cards == null) {
      return;
    }

    cards.sort(Comparator.comparing(Card::getIndex));
  }

  private void findFiguresAndScore() {
    // Método para encontrar las figuras en la mano del jugador
    if (cards == null) {
      return;
    }

    findFlushes(); // Encontrar escaleras si las hay, debe ser el primer paso
    findDuplicateCardFigures(); // Encontrar cuartas, ternas y pares
    findScore();
  }

  private void findFlushes() {
    // Método para encontrar las escaleras en la mano del jugador
    if (cards == null) {
      return;
    }

    ArrayList<Figure> flushes = new ArrayList<>();
    ArrayList<Card> sortedCards = new ArrayList<>(cards);
    sortedCards.sort(Comparator.comparing(Card::getIndex));

    for (int i = 0; i < sortedCards.size(); i++) {
      ArrayList<Card> currentFlush = new ArrayList<>();
      currentFlush.add(sortedCards.get(i));

      for (int j = i + 1; j < sortedCards.size(); j++) {
        if (cardsInFigure[i]) {
          continue;
        }

        if (sortedCards.get(j).getSuit() != sortedCards.get(i).getSuit()) {
          break;
        }

        if (currentFlush.getLast().getIndex() == sortedCards.get(j).getIndex() - 1) {
          currentFlush.add(sortedCards.get(j));
        }
      }

      if (currentFlush.size() >= 2) {
        Figure currentFigure = new Figure(Figure.Type.FLUSH, currentFlush);
        updateCardsInFigure(currentFigure);
        flushes.add(currentFigure);
      }
    }

    figures.addAll(flushes);
  }

  private void findDuplicateCardFigures() {
    // Método para encontrar las cuartas, ternas y pares en la mano del jugador, excluyendo las escaleras ya encontradas
    if (cards == null) {
      return;
    }

    int[] cardsCount = new int[Card.Name.values().length];

    // Contar cuántas cartas hay de cada tipo excluyendo las que ya forman parte de una figura
    for (int i = 0; i < cards.size(); i++) {
      if (cardsInFigure[i]) {
        continue;
      }

      cardsCount[cards.get(i).getName().ordinal()]++;
    }

    for (int i = 0; i < cardsCount.length; i++) {
      Figure currentFigure;

      // Encontramos las cuartas
      if (cardsCount[i] == 4) {
        ArrayList<Card> quads = new ArrayList<>();

        for (Card card : cards) {
          if (card.getName().ordinal() == i) {
            quads.add(card);
          }
        }

        currentFigure = new Figure(Figure.Type.QUADS, quads);
        figures.add(currentFigure);
        updateCardsInFigure(currentFigure);
      }

      if (cardsCount[i] == 3) {
        ArrayList<Card> trips = new ArrayList<>();

        for (Card card : cards) {
          if (card.getName().ordinal() == i) {
            trips.add(card);
          }
        }

        currentFigure = new Figure(Figure.Type.TRIPS, trips);
        figures.add(currentFigure);
        updateCardsInFigure(currentFigure);
      }

      if (cardsCount[i] == 2) {
        ArrayList<Card> pair = new ArrayList<>();

        for (Card card : cards) {
          if (card.getName().ordinal() == i) {
            pair.add(card);
          }
        }

        currentFigure = new Figure(Figure.Type.PAIR, pair);
        figures.add(currentFigure);
        updateCardsInFigure(currentFigure);
      }
    }
  }

  private void findScore() {
    score = 0;

    for (int i = 0; i < cardsInFigure.length; i++) {
      if (!cardsInFigure[i]) {
        score += cards.get(i).getValue();
      }
    }
  }

  private void updateCardsInFigure(Figure figure) {
    // Método para actualizar las cartas que forman parte de una figura
    for (Card cardInFigure : figure.getCards()) {
      for (int i = 0; i < cards.size(); i++) {
        if (cards.get(i).getIndex() == cardInFigure.getIndex()) {
          cardsInFigure[i] = true;
        }
      }
    }
  }
}
