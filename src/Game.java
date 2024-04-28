public class Game {
  private final Player[] players;
  private final Dealer dealer;

  public Game() {
    // Crear jugadores y repartidor
    Player player1 = new Player("Martín Estrada Contreras");
    Player player2 = new Player("Raúl Vidal");
    Player player3 = new Player("Mateo Upegui Borja");

    players = new Player[]{player1, player2, player3};
    dealer = new Dealer();
    dealCards();
  }

  public Player[] getPlayers() {
    return players;
  }

  public void dealCards() {
    // Repartir 10 cartas a los jugadores
    dealer.dealCards(players, 10);
  }
}
