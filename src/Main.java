import com.github.weisj.darklaf.LafManager;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
  public static void main(String[] args) {
    Game game = new Game();

    LafManager.install();
    CartasUI cartasUI = new CartasUI();
    cartasUI.run(game);
  }
}