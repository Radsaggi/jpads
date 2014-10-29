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
public class Letter {
    private final char letter;
    private Letter next;

    Letter(char letter) {
        this.letter = letter;
        next = null;
    }

    public char getLetter() {
        return letter;
    }

    public Letter getNext() {
        return next;
    }

    public void setNext(Letter next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Letter(" + letter + ')';
    }
}
