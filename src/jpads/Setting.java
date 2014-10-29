/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpads;

import java.util.Observable;

/**
 *
 * @author ashutosh
 */
public class Setting extends Observable {

    private final char sett[][];
    private final int rows;
    private final int columns;

    public Setting(int r, int c, String ar[]) {
        if (ar.length != r) {
            throw new IllegalArgumentException();
        }

        this.rows = r;
        this.columns = c;

        sett = new char[r][];
        for (int i = 0; i < r; i++) {
            if (ar[i].length() != c) {
                throw new IllegalArgumentException();
            }
            sett[i] = ar[i].toCharArray();
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public char getSett(Index idx) {
        return getSett(idx.getX(), idx.getY());
    }
    
    public char getSett(int x, int y) {
        return sett[x][y];
    }

    public void setSett(char ch, Index idx) {
        int x = idx.getX(), y = idx.getY();
        this.sett[x][y] = ch;
        setChanged();
        notifyObservers(new Index(x, y));
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder(rows * (columns + 1));
        for (int i = 0; i < rows; i++) {
            buffer.append(sett[i]).append('\n');
        }
        return buffer.toString();
    }
}
