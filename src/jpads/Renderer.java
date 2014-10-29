/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpads;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

/**
 *
 * @author ashutosh
 */
public class Renderer extends JPanel implements Observer {

    private static final Color BLOCKED_COLOR = new Color(255, 75, 74),
            CLEAR_COLOR = new Color(178, 161, 161),
            LETTER_COLOR = new Color(255, 254, 115),
            PLAYER_COLOR = new Color(39, 139, 204);

    private final int r, c;
    private final Setting arenaBuffer;

    private final JLabel[][] arenaL;
    private final JLabel messageL;

    public Renderer(int r, int c, Setting initArena) {
        this.r = r;
        this.c = c;
        this.arenaBuffer = initArena;
        initArena.addObserver(this);

        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel innerP = new JPanel(new GridLayout(r, c));
        innerP.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEtchedBorder(),
                BorderFactory.createEmptyBorder(7, 7, 7, 7)));
        arenaL = new JLabel[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                char ch = initArena.getSett(i, j);
                arenaL[i][j] = new JLabel("" + ch);
                arenaL[i][j].setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                arenaL[i][j].setOpaque(true);
                if (ch == Levels.BLOCKER) {
                    arenaL[i][j].setBackground(BLOCKED_COLOR);
                } else if (ch == Levels.CLEAR) {
                    arenaL[i][j].setBackground(CLEAR_COLOR);
                } else {
                    arenaL[i][j].setBackground(LETTER_COLOR);
                }
                innerP.add(arenaL[i][j]);
            }
        }
        messageL = new JLabel("Its Jpads TIME!");
        messageL.setHorizontalAlignment(JLabel.CENTER);

        this.add(innerP, BorderLayout.CENTER);
        this.add(messageL, BorderLayout.SOUTH);
    }

    void showMessage(String msg) {
        messageL.setText(msg);
    }

    @Override
    public void update(Observable o, Object arg) {
        Index idx = (Index) arg;
        int x = idx.getX(), y = idx.getY();
        char ch = arenaBuffer.getSett(idx);
        arenaL[x][y].setText("" + ch);
        if (Character.isDigit(ch)) {
            arenaL[x][y].setBackground(PLAYER_COLOR);
        } else {
            arenaL[x][y].setBackground(CLEAR_COLOR);
        }
    }
}
