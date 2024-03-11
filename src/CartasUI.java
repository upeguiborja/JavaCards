import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Vector;

public class CartasUI {
  private JPanel rootPanel;
  private Game game;
  private JTabbedPane playerTabs;
  private JButton dealButton;
  private JButton showButton;
  private JButton verifyButton;

  public void run(Game game) {
    this.game = game;

    JFrame frame = new JFrame("Cartas");
    frame.setContentPane(this.rootPanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);

    this.createTabs();
  }

  private void createTabs() {
    playerTabs.removeAll();

    for (Player player : game.getPlayers()) {
      JPanel playerPanel = new JPanel();
      playerTabs.addTab(player.getName(), playerPanel);
    }
  }

  private ImageIcon getCardImage(Card card) {
    URL url = getClass().getResource(card.getIndex() + ".png");

    if (url == null) {
      throw new IllegalArgumentException("Card image not found");
    }

    return new ImageIcon(url);
  }

  private ImageIcon getCardImage() {
    URL url = getClass().getResource("back.png");

    if (url == null) {
      throw new IllegalArgumentException("Card image not found");
    }

    return new ImageIcon(url);
  }

  private void resetCards(JPanel playerPanel) {
    Component[] cardLabels = playerPanel.getComponents();

    for (int i = 0; i < cardLabels.length; i++) {
      JLabel cardLabel = (JLabel) cardLabels[i];
      cardLabel.setIcon(getCardImage());
    }
  }

  private void createCards(JPanel playerPanel) {
    for (int i = 0; i < 10; i++) {
      JLabel cardLabel = new JLabel();
      cardLabel.setIcon(getCardImage());
      playerPanel.add(new JLabel(getCardImage()));
    }
  }

  private void updateCards(JPanel playerPanel, Player player) {
    Card[] playerCards = player.getCards();
    Component[] cardLabels = playerPanel.getComponents();

    for (int j = 0; j < cardLabels.length; j++) {
      JLabel cardLabel = (JLabel) cardLabels[j];
      cardLabel.setIcon(getCardImage(playerCards[j]));
    }
  }

  private void dealButtonActionPerformed() {
    if (playerTabs == null || game == null) {
      return;
    }

    game.dealCards();

    for (Component playerTab : playerTabs.getComponents()) {
      JPanel playerPanel = (JPanel) playerTab;

      if (playerPanel.getComponents().length == 0) {
        // Si el panel del jugador no tiene cartas, entonces las creamos
        createCards(playerPanel);
      } else {
        // Si el panel del jugador ya tiene cartas, entonces las reiniciamos
        resetCards(playerPanel);
      }
    }

    playerTabs.repaint();
    showButton.setEnabled(true);
    verifyButton.setEnabled(false);
  }

  private void showButtonActionPerformed() {
    if (playerTabs == null || game == null) {
      return;
    }

    // Asumimos que las cartas ya han sido repartidas y además que los JLabels de las cartas ya han sido creados
    Component[] playerPanels = this.playerTabs.getComponents();
    Player[] players = game.getPlayers();

    for (int i = 0; i < playerPanels.length; i++) {
      JPanel playerPanel = (JPanel) playerPanels[i];
      Player player = players[i];
      updateCards(playerPanel, player);
    }

    showButton.setEnabled(false);
    verifyButton.setEnabled(true);
    playerTabs.repaint();
  }

  private void verifyButtonActionPerformed() {
    int currentPlayerIndex = playerTabs.getSelectedIndex();
    Player currentPlayer = game.getPlayers()[currentPlayerIndex];

    Vector<Card.Name> pairs = currentPlayer.getPairs();
    Vector<Card.Name> trips = currentPlayer.getTrips();
    Vector<Card.Name> quads = currentPlayer.getQuads();

    StringBuilder message = new StringBuilder();

    for (Card.Name pair : pairs) {
      message.append("Par de ").append(pair).append("\n");
    }

    for (Card.Name trip : trips) {
      message.append("Terna de ").append(trip).append("\n");
    }

    for (Card.Name quad : quads) {
      message.append("Cuarta de ").append(quad).append("\n");
    }

    if (message.length() == 0) {
      message.append("No hay pares, ternas, cuartas o escaleras");
    }

    JOptionPane.showMessageDialog(null, message.toString());
  }

  private void createUIComponents() {
    dealButton = new JButton();
    dealButton.addActionListener(e -> dealButtonActionPerformed());

    showButton = new JButton();
    showButton.addActionListener(e -> showButtonActionPerformed());

    verifyButton = new JButton();
    verifyButton.addActionListener(e -> verifyButtonActionPerformed());
  }
}