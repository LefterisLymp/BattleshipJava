package battleship.javaproj;

public class Board {
    private int gsize;
    private int ship_num;
    private int[] grid;

    public Board (int size, int ship_num) {
        gsize = size;
        this.ship_num = ship_num;
        grid = new int[gsize*gsize];
        int i;
        //Initialization
        for (i = 0; i < gsize*gsize; i++) {
            grid[i] = 0;
        }
    }

    public int getGridSize() {return gsize;}

    public void set(int index, int r) {grid[index] = r;}

    public int at(int index) {return grid[index];}

    public void print() {
        int i = 0, j = 0;
        for (; i < gsize; i++) {
            for (j = 0; j < gsize; j++) {
                int index = i*gsize + j;
                if (grid[index] == 0) System.out.print('.');
                else if (grid[index] == ship_num + 1) System.out.print('W');
                else if (grid[index] == ship_num + 2) System.out.print('R');
                else System.out.print('S');
            }
            System.out.println("");
        }
    }
}