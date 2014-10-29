/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpads;

/**
 *
 * @author ashutosh
 */
public class Index {

    private int x;
    private int y;

    public Index(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public Index setX(int x) {
        return new Index(x, y);
    }

    public Index setY(int y) {
        return new Index(x, y);
    }

    @Override
    public String toString() {
        return "(" + "x=" + x + ", y=" + y + ')';
    }
}
