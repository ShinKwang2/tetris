package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TetrisTable extends JPanel {

    private final String LEVEL_MSG = "Level: ";

    BufferedImage off;
    Graphics offG;

    int[][] map;
    Color[] colors;

    int[] blockRow;
    int[] blockCol;

    JLabel levelLabel;


    public TetrisTable() {}

    public TetrisTable(Color[] colors) {
        this.colors = colors;
        setLayout(null);

        levelLabel = new JLabel(LEVEL_MSG + "?");
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setBounds(310, 600, 100, 130);
        add(levelLabel);
    }

    public void initialize() {

        off = new BufferedImage((15 * 21) * 2 + 1, (15 * 21) * 2 + 1, BufferedImage.TYPE_INT_RGB);
        offG = off.getGraphics();

        map = new int[21][12];
        blockRow = new int[4];
        blockCol = new int[4];

        drawMap(map);
        drawGrid();
//        drawTitle();
    }



    public void drawMap(int[][] map) {
        for (int r = 0; r < 21; r++) {
            for (int c = 0; c < 12; c++) {
                offG.setColor(colors[map[r][c]]);
                offG.fillRect(c * 30, r * 30, 30, 30);
            }
        }
    }

    public void drawGrid() {
        offG.setColor(new Color(200, 200, 200));

        for(int r = 0; r < 21; r++)
        {
            for(int c = 0; c < 12; c++)
            {
                offG.drawRect(c * 30, r * 30, 30, 30);
            }
        }
    }

    public void changeLevel(int level) {
        levelLabel.setText(LEVEL_MSG + level);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(off, 0, 0, this);
    }
}
