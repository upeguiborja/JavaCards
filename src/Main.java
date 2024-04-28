public class Main {
  public static void main(String[] args) {
    Game game = new Game();

    CartasUI cartasUI = new CartasUI(game);
    cartasUI.run();
  }
}