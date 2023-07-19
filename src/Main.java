import block.BlockType;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        System.out.println(Arrays.toString(BlockType.values()));

        TetrisFrame tetris = new TetrisFrame("Tetris");
        tetris.setSize(1200, 860);
        tetris.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tetris.getContentPane().setBackground(new Color(233, 225, 216));
        tetris.setVisible(true);

    }
}