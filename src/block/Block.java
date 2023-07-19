package block;

import java.util.Arrays;

public class Block {

    private BlockType type;

    private int blockIndex;

    private int[] rowLocation;
    private int[] colLocation;

    private int currentPosition;

    private int[][] nextRow;
    private int[][] nextCol;

    public Block(int blockIndex) {
        this.blockIndex = blockIndex;
        this.type = BlockType.getBlock(blockIndex);
        rowLocation = type.getInitRowLocation();
        colLocation = type.getInitColLocation();
        this.currentPosition = 0;

        nextRow = createNextRow();
        nextCol = createNextCol();
    }

    public int getBlockIndex() {
        return blockIndex;
    }

    public int[] getRowLocation() {
        return rowLocation;
    }

    public int[] getColLocation() {
        return colLocation;
    }

    public void moveRowLocation(int length) {
        for (int i = 0; i < rowLocation.length; i++) {
            rowLocation[i] = rowLocation[i] + length;
        }
    }

    public void moveColLocation(int length) {
        for (int i = 0; i < colLocation.length; i++) {
            colLocation[i] = colLocation[i] + length;
        }
    }

    public int[] getNextRow() {
        return nextRow[currentPosition];
    }

    public int[] getNextCol() {
        return nextCol[currentPosition];
    }

    public void setRowLocation(int[] rowLocation) {
        this.rowLocation = rowLocation;
    }

    public void setColLocation(int[] colLocation) {
        this.colLocation = colLocation;
    }

    public void addCurrentPosition() {
        currentPosition++;
        if (currentPosition >= 4) {
            currentPosition %= 4;
        }
    }

    private int[][] createNextRow() {
        if (type == BlockType.O) {
            return new int[][] {
                    {0, 0, 0, 0},
                    {0, 0, 0, 0},
                    {0, 0, 0, 0},
                    {0, 0, 0, 0}
            };
        }
        if (type == BlockType.J) {
            return new int[][] {
                    {-1, 0, 1, 0},
                    {1, 0, -1, -2},
                    {1, 0, -1, 0},
                    {-1, 0, 1, 2}
            };
        }
        if (type == BlockType.L) {
            return new int[][] {
                    {-1, 0, 1, -2},
                    {1, 0, -1, 0},
                    {1, 0, -1, 2},
                    {-1, 0, 1, 0}
            };

        }
        if (type == BlockType.Z) {
            return new int[][] {
                    {-1, 0, -1, 0},
                    {1, 0, 1, 0},
                    {-1, 0, -1, 0},
                    {1, 0, 1, 0}
            };
        }
        if (type == BlockType.S) {
            return new int[][] {
                    {0, -1, 0, -1},
                    {0, 1, 0, 1},
                    {0, -1, 0, -1},
                    {0, 1, 0, 1}
            };

        }
        if (type == BlockType.I) {
            return new int[][] {
                    {3, 2, 1, 0},
                    {-3, -2, -1, 0},
                    {3, 2, 1, 0},
                    {-3, -2, -1, 0},
            };
        }
        if (type == BlockType.T) {
            return new int[][] {
                    {-1, 0, 1, -1},
                    {1, 0, -1, -1},
                    {1, 0, -1, 1},
                    {-1, 0, 1, 1}
            };
        }
        return null;
    }

    private int[][] createNextCol() {
        if (type == BlockType.O) {
            return new int[][] {
                    {0, 0, 0, 0},
                    {0, 0, 0, 0},
                    {0, 0, 0, 0},
                    {0, 0, 0, 0},
            };
        }
        if (type == BlockType.J) {
            return new int[][] {
                    {1, 0, -1, -2},
                    {1, 0, -1, 0},
                    {-1, 0, 1, 2},
                    {-1, 0, 1, 0}
            };
        }
        if (type == BlockType.L) {
            return new int[][] {
                    {1, 0, -1, 0},
                    {1, 0, -1, 2},
                    {-1, 0, 1, 0},
                    {-1, 0, 1, -2}
            };
        }
        if (type == BlockType.Z) {
            return new int[][] {
                    {1, 0, -1, -2},
                    {-1, 0, 1, 2},
                    {1, 0, -1, -2},
                    {-1, 0, 1, 2},
            };
        }
        if (type == BlockType.S) {
            return new int[][] {
                    {0, -1, 2, 1},
                    {0, 1, -2, -1},
                    {0, -1, 2, 1},
                    {0, 1, -2, -1}
            };
        }
        if (type == BlockType.I) {
            return new int[][] {
                    {3, 2, 1, 0},
                    {-3, -2, -1, 0},
                    {3, 2, 1, 0},
                    {-3, -2, -1, 0},
            };
        }
        if (type == BlockType.T) {
            return new int[][] {
                    {1, 0, -1, -1},
                    {1, 0, -1, 1},
                    {-1, 0, 1, 1},
                    {-1, 0, 1, -1}
            };
        }
        return null;
    }

    @Override
    public String toString() {
        return "Block{" +
                "type=" + type +
                ", blockIndex=" + blockIndex +
                ", rowLocation=" + Arrays.toString(rowLocation) +
                ", colLocation=" + Arrays.toString(colLocation) +
                ", currentPosition=" + currentPosition +
                ", nextRow=" + Arrays.toString(nextRow) +
                ", nextCol=" + Arrays.toString(nextCol) +
                '}';
    }
}
