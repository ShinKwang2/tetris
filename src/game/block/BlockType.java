package game.block;

public enum BlockType {

    EMPTY(new int[] {0, 0, 0, 0}, new int[] {0, 0, 0, 0}),

    O(new int[] {0, 0, 1, 1}, new int[] {5, 6, 5, 6}),
    J(new int[] {0, 0, 0, 1}, new int[] {5, 6, 7, 7}),
    L(new int[] {0, 0, 0, 1}, new int[] {5, 6, 7, 5}),
    Z(new int[] {0, 0, 1, 1}, new int[] {5, 6, 6, 7}),
    S(new int[] {0, 0, 1, 1}, new int[] {6, 7, 5, 6}),
    I(new int[] {0, 0, 0, 0}, new int[] {4, 5, 6, 7}),
    T(new int[] {0, 0, 0, 1}, new int[] {5, 6, 7, 6});

    private int[] initRowLocation;
    private int[] initColLocation;

    BlockType(int[] initRowLocation, int[] initColLocation) {
        this.initRowLocation = initRowLocation;
        this.initColLocation = initColLocation;
    }

    public int[] getInitRowLocation() {
        return initRowLocation.clone();
    }

    public int[] getInitColLocation() {
        return initColLocation.clone();
    }

    public static BlockType getBlock(int type) {
        BlockType[] values = BlockType.values();
        return values[type];
    }
}
