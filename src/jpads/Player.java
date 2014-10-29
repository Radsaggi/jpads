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
public class Player {

    private final Simulator sim;
    
    private final int id;
    private final Direction direction;
    private Index idx;
    private Letter letter;

    Player(int id, Index idx, Direction d, Simulator sim) {
        this.sim = sim;
        this.direction = d;
        this.id = id;
        this.idx = idx;
    }

    public Letter getLetter() {
        return letter;
    }

    public Direction getDirection() {
        return direction;
    }

    public Index getIdx() {
        return idx;
    }

    public int getX() {
        return idx.getX();
    }

    public int getY() {
        return idx.getY();
    }
    
    public int getID() {
        return id;
    }

    public void move() {
        sim.movePlayer(this);
    }
    
    public int clone(Direction d) {
        return sim.clonePlayer(this, d);
    }

    @Override
    public String toString() {
        return "Player" + id;
    }
    
    void setLetter(Letter letter) {
        this.letter = letter;
    }

    void setX(int x) {
        this.idx = idx.setX(x);
    }

    void setY(int y) {
        this.idx = idx.setY(y);
    }
    
    void setIndex(Index idx) {
        this.idx = idx;
    }
    
    public static enum Direction {
        UP(-1, 0), DOWN(1, 0), LEFT(0, -1), RIGHT(0, 1);
        
        int dx, dy;

        private Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
        
        Index move(Index idx) {
            return new Index(idx.getX() + dx, idx.getY() + dy);
        }
        
        int moveX(int x) {
            return x + dx;
        }
        
        int moveY(int y) {
            return y + dy;
        }
    }
}
