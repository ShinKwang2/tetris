package view;

import game.Game;
import rank.Rank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import static java.awt.event.KeyEvent.*;

public class TetrisFrame extends JFrame implements ActionListener {

    JButton startButton;
    JButton pauseButton;
    JButton restartButton;

    Color[] colors;

    Game game;
    Rank rank;

    public TetrisFrame(String title) {
        super(title);

        this.setLayout(null);
        setColors();
        setComponents();
    }

    private void setComponents() {

        TetrisTable leftTable = new TetrisTable(colors);
        leftTable.setBounds(20, 40, 360, 700);
        leftTable.setBackground(new Color(130, 120, 110));
        leftTable.initialize();
        getContentPane().add(leftTable);

        TetrisTable rightTable = new TetrisTable(colors);
        rightTable.setBounds(814, 40, 360, 700);
        rightTable.setBackground(new Color(130, 120, 110));
        rightTable.initialize();
        getContentPane().add(rightTable);


        JPanel rankPanel = createRankPanel();
        List<JLabel> rankLabels = createRankLabels();
        for (JLabel rankLabel : rankLabels) {
            rankPanel.add(rankLabel);
        }
        getContentPane().add(rankPanel);
        rank = new Rank(rankLabels);


        JPanel bottomPanel = new JPanel();
        bottomPanel.setBounds(474, 560, 220, 140);
        bottomPanel.setBackground(new Color(239,234,227));
        bottomPanel.setLayout(new GridLayout(3, 1, 10, 10));

        startButton = new JButton("게임 시작");
        startButton.addActionListener(this);
        startButton.setBackground(new Color(247, 210, 121));
        bottomPanel.add(startButton);

        pauseButton = new JButton("일시 정지");
        pauseButton.addActionListener(this);
        startButton.setBackground(new Color(247, 210, 121));
        bottomPanel.add(pauseButton);

        restartButton = new JButton("게임 재개(!일시 정지)");
        restartButton.addActionListener(this);
        restartButton.setBackground(new Color(247, 210, 121));
        bottomPanel.add(restartButton);

        getContentPane().add(bottomPanel);

        game = new Game(leftTable, rightTable);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                game.pressKey(keyCode);

            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            game.startGame();
            this.requestFocus();
        }

        if (e.getSource() == pauseButton) {
            game.pauseGame();
            this.requestFocus();
        }

        if (e.getSource() == restartButton) {
            game.restartGame();
            this.requestFocus();
        }
    }


    private void setColors() {
        colors = new Color[9];
        colors[0] = new Color(80, 70, 70);
        colors[1] = new Color(211, 165, 144);
        colors[2] = new Color(171, 201, 200);
        colors[3] = new Color(120, 170 , 130);
        colors[4] = new Color(20, 150, 170);
        colors[5] = new Color(135, 149, 158);
        colors[6] = new Color(190, 183, 114);
        colors[7] = new Color(210, 153, 210);
        colors[8] = new Color(0, 0, 0);
    }

    private JPanel createRankPanel() {
        JPanel rankPanel = new JPanel();
        rankPanel.setBounds(474, 200, 220, 280);
        rankPanel.setBackground(new Color(239,234,227));
        rankPanel.setLayout(new GridLayout(6, 1));

        JLabel rankText = new JLabel("Rank Top 5");
        rankText.setHorizontalAlignment(SwingConstants.CENTER);
        rankPanel.add(rankText);

        return rankPanel;
    }

    private List<JLabel> createRankLabels() {
        JLabel first = new JLabel("1. 랭킹을 등록해주세요.");
        first.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel second = new JLabel("2. 랭킹을 등록해주세요.");
        second.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel third = new JLabel("3. 랭킹을 등록해주세요.");
        third.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel fourth = new JLabel("4. 랭킹을 등록해주세요.");
        fourth.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel fifth = new JLabel("5. 랭킹을 등록해주세요.");
        fifth.setHorizontalAlignment(SwingConstants.CENTER);

        return List.of(first, second, third, fourth, fifth);
    }
}
