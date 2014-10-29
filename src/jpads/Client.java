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
public interface Client {
    public void execute(Player[] players, int init);
    public Player.Direction getDefaultDirection();
}
