/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import javax.swing.SwingUtilities;
import jpads.*;
import static jpads.Levels.*;
import static jpads.Player.Direction;
import static jpads.Player.Direction.*;

/**
 *
 * @author ashutosh
 */
public class ClientImpl implements Client {
    
    private final Jpads system;    
    
    private ClientImpl() {
        system = new Jpads(LEVEL1, this);
    }
    
    @Override
    public Direction getDefaultDirection() {
        return UP;
    }
    
    @Override
    public void execute(Player[] players, int init) {
        Player p = players[init];
        Letter l[] = new Letter[5];
        for (int i = 0; i < 3; i++) {
            p.move();
            l[i] = p.getLetter();
        }
        
        int idx = p.clone(RIGHT);
        Player right = players[idx];
        
        right.move();
        
        
        system.submitWord(l[0]);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                Client c = new ClientImpl();
            }
        });
    }
}
