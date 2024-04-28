import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

public class CartasUI {
  private JPanel rootPanel;
  private final Game game;
  private JTabbedPane playerTabs;
  private JButton showButton;
  private JButton verifyButton;
  private JButton sortButton;

  public CartasUI(Game game) {
    this.game = game;
    setupUI();
  }

  public void run() {
    JFrame frame = new JFrame("Cartas");
    frame.setContentPane(this.rootPanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setResizable(false);
    frame.setVisible(true);
  }

  private void createTabPanels() {
    playerTabs.removeAll();

    for (Player player : game.getPlayers()) {
      JPanel playerPanel = new JPanel();
      playerPanel.setLayout(new GridBagLayout());
      playerTabs.addTab(player.getName(), playerPanel);

      for (int i = 0; i < 10; i++) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = i;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);
        playerPanel.add(new JLabel(getCardImage()), gbc);
      }
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

    for (Component label : cardLabels) {
      JLabel cardLabel = (JLabel) label;
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
    ArrayList<Card> playerCards = player.getCards();
    Component[] cardLabels = playerPanel.getComponents();

    for (int j = 0; j < cardLabels.length; j++) {
      JLabel cardLabel = (JLabel) cardLabels[j];
      cardLabel.setIcon(getCardImage(playerCards.get(j)));
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
    sortButton.setEnabled(false);
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

    playerTabs.repaint();
    showButton.setEnabled(false);
    verifyButton.setEnabled(true);
    sortButton.setEnabled(true);
  }

  private void verifyButtonActionPerformed() {
    int currentPlayerIndex = playerTabs.getSelectedIndex();
    Player currentPlayer = game.getPlayers()[currentPlayerIndex];


    StringBuilder message = new StringBuilder();

    for (Figure figure : currentPlayer.getFigures()) {
      message.append(figure.toString()).append("\n");
    }

    if (message.length() == 0) {
      message.append("No hay pares, ternas, cuartas o escaleras");
    }

    message.append("Puntaje: ").append(currentPlayer.getScore()).append("\n");

    JOptionPane.showMessageDialog(null, message.toString());
  }

  private void sortButtonActionPerformed() {
    int currentPlayerIndex = playerTabs.getSelectedIndex();
    Player currentPlayer = game.getPlayers()[currentPlayerIndex];
    currentPlayer.sortCards();
    updateCards((JPanel) playerTabs.getComponent(currentPlayerIndex), currentPlayer);
  }

  private void setupUI() {
    rootPanel = new JPanel();
    rootPanel.setLayout(new GridBagLayout());

    GridBagConstraints gbc;

    JButton dealButton = new JButton();
    dealButton.addActionListener(e -> dealButtonActionPerformed());
    dealButton.setText("Deal");

    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    rootPanel.add(dealButton, gbc);

    showButton = new JButton();
    showButton.addActionListener(e -> showButtonActionPerformed());
    showButton.setText("Show");

    gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    rootPanel.add(showButton, gbc);

    verifyButton = new JButton();
    verifyButton.addActionListener(e -> verifyButtonActionPerformed());
    verifyButton.setEnabled(false);
    verifyButton.setText("Verify");

    gbc = new GridBagConstraints();
    gbc.gridx = 2;
    gbc.gridy = 0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    rootPanel.add(verifyButton, gbc);

    sortButton = new JButton();
    sortButton.addActionListener(e -> sortButtonActionPerformed());
    sortButton.setEnabled(false);
    sortButton.setText("Sort");

    gbc = new GridBagConstraints();
    gbc.gridx = 4;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.EAST;
    rootPanel.add(sortButton, gbc);

    playerTabs = new JTabbedPane();

    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 5;
    gbc.weighty = 1.0;
    gbc.fill = GridBagConstraints.BOTH;
    rootPanel.add(playerTabs, gbc);

    this.createTabPanels();
  }
}
