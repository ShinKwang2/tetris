import java.util.List;

import static java.awt.event.KeyEvent.*;
import static java.awt.event.KeyEvent.VK_UP;

public class Game {

    Player leftPlayer;
    Player rightPlayer;

    TetrisTable leftPanel;
    TetrisTable rightPanel;


    List<Integer> keyBoard1 = List.of(VK_A, VK_D, VK_S, VK_W);
    List<Integer> keyBoard2 = List.of(VK_LEFT, VK_RIGHT, VK_DOWN, VK_UP);


    public Game(TetrisTable leftPanel, TetrisTable rightPanel) {
        this.leftPanel = leftPanel;
        this.rightPanel = rightPanel;

        leftPlayer = new Player(21, 12, leftPanel);
        rightPlayer = new Player(21, 12, rightPanel);
        leftPlayer.setOtherPlayer(rightPlayer);
        rightPlayer.setOtherPlayer(leftPlayer);
    }

    public void startGame(){
        leftPlayer.start();
        rightPlayer.start();
    }

    public void pauseGame() {
        leftPlayer.pause();
        rightPlayer.pause();
    }

    public void restartGame() {
        leftPlayer.restart();
        rightPlayer.restart();
    }

    public void pressKey(int keyCode) {
        if (keyBoard1.contains(keyCode)) {
            leftPlayer.command(keyBoard1.indexOf(keyCode));
        }

        if (keyBoard2.contains(keyCode)) {
            rightPlayer.command(keyBoard2.indexOf(keyCode));
        }
    }
}
