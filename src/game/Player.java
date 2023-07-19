package game;

import game.block.Block;
import exception.PauseException;
import exception.ReGameException;
import rank.Rank;
import view.TetrisTable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Player {

    private final int LEFT = 0;
    private final int RIGHT = 1;
    private final int DOWN = 2;
    private final int UP = 3;
    private final long DELAY_TIME = 1000L;

    private int level;
    private int blockCount;

    private boolean gameContinue = false;

    private boolean reGame = false;

    private boolean isPause = false;


    private int[][] map;
    private int rowSize;
    private int colSize;
    private TetrisTable view;

    private Player otherPlayer;

    private Random random;
    private Block currentBlock;

    private Thread gameThread;


    public Player(int rowSize, int colSize, TetrisTable view) {
        this.view = view;
        this.rowSize = rowSize;
        this.colSize = colSize;
        map = new int[this.rowSize][this.colSize];

        random = new Random();
        currentBlock = new Block(random.nextInt(7) + 1);
        gameThread = new GameThread();
    }

    public void start() {
        level = 0;
        gameContinue = true;
        view.initialize();
        view.changeLevel(level);
        map = new int[rowSize][colSize];
        currentBlock = new Block(random.nextInt(7) + 1);

        gameThread.interrupt();
        gameThread = new GameThread();
        gameThread.start();

    }

    public void pause() {
        isPause = true;
    }

    public void restart() {
        isPause = false;
    }

    public void setOtherPlayer(Player otherPlayer) {
        this.otherPlayer = otherPlayer;
    }

    private void setLevel(int level) {
        this.level = level;
    }

    public void command(int index) {
        if (index == LEFT) {
            removeBlock();
            if (checkMove(-1)) {
                currentBlock.moveColLocation(-1);
            }
            drawBlock();
            view.repaint();
        }
        if (index == RIGHT) {
            removeBlock();
            if (checkMove(1)) {
                currentBlock.moveColLocation(1);
            }
            drawBlock();
            view.repaint();
        }
        if (index == DOWN) {
            removeBlock();
            if (checkDrop()) {
                currentBlock.moveRowLocation(1);
            }
            drawBlock();
            view.repaint();
        }
        if (index == UP) {
            removeBlock();
            int[] tempRow = currentBlock.getRowLocation().clone();
            int[] tempCol = currentBlock.getColLocation().clone();

            int[] nextRow = currentBlock.getNextRow();
            int[] nextCol = currentBlock.getNextCol();

            for (int i = 0; i < 4; i++) {
                tempRow[i] = tempRow[i] + nextRow[i];
                tempCol[i] = tempCol[i] + nextCol[i];
            }

            if (checkTurn(tempRow, tempCol)) {
                currentBlock.setRowLocation(tempRow.clone());
                currentBlock.setColLocation(tempCol.clone());
                currentBlock.addCurrentPosition();
            }
            drawBlock();
            view.repaint();
        }
    }

    private void dropBlock() { ///시간으로 드랍하기
        removeBlock();

        int[] blockRow = currentBlock.getRowLocation();
        if (checkDrop()) {
            for (int i = 0; i < 4; i++) {
                blockRow[i] = blockRow[i] + 1;
            }
        }
        else {
            drawBlock();
            nextBlock();
            view.repaint();
        }
    }

    private void nextBlock() {
        deleteLine();

        int nextBlockType = random.nextInt(7) + 1;
        while (currentBlock.getBlockIndex() == nextBlockType) {
            nextBlockType = random.nextInt(7) + 1;
        }
        currentBlock = new Block(nextBlockType);
        checkGameOver();
        blockCount++;
    }

    private boolean checkDrop() {
        int[] blockRow = currentBlock.getRowLocation();
        int[] blockCol = currentBlock.getColLocation();
        for (int i = 0; i < 4; i++) {
            if (blockRow[i] + 1 >= rowSize) {
                return false;
            }
            if (blockRow[i] + 1 < rowSize) {
                if (map[blockRow[i] + 1][blockCol[i]] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkMove(int direction) {
        int[] blockRow = currentBlock.getRowLocation();
        int[] blockCol = currentBlock.getColLocation();
        for (int i = 0; i < 4; i++) {
            int nextCol = blockCol[i] + direction;
            if (nextCol < 0 || colSize <= nextCol) {
                return false;
            }
            if (map[blockRow[i]][nextCol] != 0) {
                return false;
            }
        }
        return true;
    }

    private boolean checkTurn(int[] rows, int[] cols) {
        for (int i = 0; i < 4; i++) {
            if (rows[i] < 0 || 21 <= rows[i] || cols[i] < 0 || 12 <= cols[i]) {
                return false;
            }
            if (map[rows[i]][cols[i]] != 0) {
                return false;
            }
        }
        return true;
    }

    private void removeBlock() {
        int[] blockRow = currentBlock.getRowLocation();
        int[] blockCol = currentBlock.getColLocation();
        for (int i = 0; i < 4; i++) {
            map[blockRow[i]][blockCol[i]] = 0;
        }
    }

    private void drawBlock() {
        int[] blockRow = currentBlock.getRowLocation();
        int[] blockCol = currentBlock.getColLocation();
        for (int i = 0; i < 4; i++) {
            map[blockRow[i]][blockCol[i]] = currentBlock.getBlockIndex();
        }
    }

    private void deleteLine() {
        List<Integer> candidateRow = new ArrayList<>();

        for (int row = 0; row < rowSize; row++) {

            boolean isFullRow = true;
            for (int col = 0; col < colSize; col++) {
                if (map[row][col] == 0 || map[row][col] == 8) {
                    isFullRow = false;
                    break;
                }
            }
            if (isFullRow) {
                candidateRow.add(row);
            }
        }
        if (candidateRow.size() >= 3) {
            otherPlayer.makeBadBlock();
            otherPlayer.setLevel(otherPlayer.level + 1);
            otherPlayer.view.changeLevel(otherPlayer.level);
        }
        for (int targetRow : candidateRow) {
            for (int row = targetRow; row > 0; row--) {
                for (int col = 0; col < colSize; col++) {
                    map[row][col] = map[row - 1][col];
                }
            }
            for (int col = 0; col < colSize; col++) {
                map[0][col] = 0;
            }
        }
    }

    private void makeBadBlock() {
        removeBlock();
        for (int row = 0; row < rowSize - 1; row++) {
            map[row] = Arrays.copyOf(map[row + 1], 12);
        }
        Arrays.fill(map[20], 8);
        drawBlock();
    }

    private long calculateBlockCount() {
        return blockCount / 10;
    }

    private void printMap() {
        for (int[] ints : map) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
    }

    private void checkGameOver() {
        int[] blockRow = currentBlock.getRowLocation();
        int[] blockCol = currentBlock.getColLocation();
        for (int i = 0; i < 4; i++) {
            if (map[blockRow[i]][blockCol[i]] != 0) {
                gameContinue = false;
                otherPlayer.gameThread.interrupt();
            }
        }
    }

    private void gameOver() {
        String loser = JOptionPane.showInputDialog(null, "Lose User 랭킹 등록", "Loser");
        Rank.registerLose(loser, 1);
        Rank.showRankers();
    }

    class GameThread extends Thread {



        @Override
        public synchronized void run() {

            try {
                while (gameContinue) {
                    Thread.sleep(DELAY_TIME - (level * 100) - (blockCount * 10));
                    dropBlock();
                    drawBlock();
                    view.drawMap(map);
                    view.drawGrid();
                    view.repaint();

                    if (reGame) {
                        throw new ReGameException();
                    }
                    if (isPause) {
                        throw new PauseException();
                    }
                }
                otherPlayer.gameThread.join();
                gameOver();
            } catch (InterruptedException e) {
                if (!otherPlayer.gameContinue) {
                    String winner = JOptionPane.showInputDialog(null, "Win User 랭킹 등록", "Winner");
                    Rank.registerWin(winner, 1);
                }

            } catch (ReGameException e) {
                reGame = false;
                Thread.currentThread().run();
            } catch (PauseException e) {
                while (isPause) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) { }
                }
                Thread.currentThread().run();
            }
        }
    }
}
