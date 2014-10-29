/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpads;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

/**
 *
 * @author ashutosh
 */
public class Jpads implements Simulator {

    public static final int INIT_CLONE_IDX = 0,
            DEFAULT_SIM_DELAY = 500;

    private final Levels level;
    private final Player players[];
    private final ArrayList<Integer> emptyPlayer;
    private final Client client;
    private int cloneCount;

    private int simDelay;
    private final Thread simThread;
    private JFrame frame;
    private final Renderer renderer;
    private final Setting arenaBuffer;

    public Jpads(Levels level, Client c) {
        this.level = level;

        this.client = c;

        this.renderer = new Renderer(level.getRows(), level.getColumns(),
                level.getSetting());
        this.arenaBuffer = level.getSetting();

        players = new Player[level.getMaxClones()];
        players[INIT_CLONE_IDX] = new Player(INIT_CLONE_IDX,
                level.getInit(), client.getDefaultDirection(), this);
        showPlayer(players[INIT_CLONE_IDX]);
        emptyPlayer = new ArrayList<>(10);
        cloneCount = 0;
        for (int i = 1; i < 10; i++) {
            emptyPlayer.add(i);
        }

        simDelay = DEFAULT_SIM_DELAY;
        simThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client.execute(players, INIT_CLONE_IDX);
                    stopGame(false);
                } catch (GameOverException e) {
                    System.out.println("Finishing code execution...");
                }
            }
        });

        showDisplay();
    }

    //public functions - 
    //meant to be called by client code
    public Player[] getPlayers() {
        return players;
    }

    public int getCloneCount() {
        return cloneCount;
    }

    public void submitWord(Letter l) {
        if (checkWord(l)) {
            stopGame(true);
        }
    }

    //Not public yet public functions -
    //meant to be a part of simulator interface
    //does not matter if called by client code
    @Override
    public void movePlayer(Player p) {
        Player.Direction d = p.getDirection();
        Index newIdx = d.move(p.getIdx());

        if (!checkIndex(newIdx)) {
            throw new IllegalStateException(p + " cannot move further!");
        }

        clearPlayer(p);
        p.setIndex(newIdx);

        char ch;
        if ((ch = findLetter(newIdx)) != 0) {
            Letter l = new Letter(ch);
            showFoundLetter(p, l);
            p.setLetter(l);
        }

        showPlayer(p);

        try {
            System.out.println(p + " moved to " + newIdx);
            Thread.sleep(simDelay);
        } catch (InterruptedException e) {
        }
    }

    @Override
    public int clonePlayer(Player p, Player.Direction d) {
        int maxC = level.getMaxClones();
        if (cloneCount == maxC) {
            throw new IllegalStateException("Cannot create more than " + maxC
                    + " clones at any time in game!");
        }
        if (p.getDirection() == d) {
            throw new IllegalStateException("Cannot create player in the same "
                    + "direction as source " + p + "!");
        }

        int lim = maxC - cloneCount;
        int idx = (int) (Math.random() * lim);

        Index newIdx = d.move(p.getIdx());
        if (!checkIndex(newIdx)) {
            throw new IllegalStateException(p + " cannot clone in "
                    + "Direction " + d + "!");
        }

        players[idx] = new Player(idx, newIdx, d, this);
        emptyPlayer.remove(new Integer(idx));
        cloneCount++;

        showPlayer(players[idx]);

        try {
            System.out.println(p + " cloned to " + players[idx]);
            Thread.sleep(simDelay);
        } catch (InterruptedException e) {
        }

        return idx;
    }

    //Non public functions -
    //meant to be a part of simulator interface
    //not to be called by client code
    //simple computational functions
    private boolean checkWord(Letter l) {
        int i = 0;
        String word = level.getWord();
        System.out.print("Checking Submission: ");
        while (l != null) {
            System.out.print(l + " ");
            if (l.getLetter() != word.charAt(i)) {
                return false;
            }
            l = l.getNext();
            i++;
        }
        System.out.println();

        return i == word.length();
    }

    private boolean checkIndex(Index idx) {
        return checkXY(idx.getX(), idx.getY());
    }

    private boolean checkXY(int x, int y) {
        return x >= 0 && x < level.getRows()
                && y >= 0 && y < level.getColumns()
                && arenaBuffer.getSett(x, y) != Levels.BLOCKER;
    }

    private char findLetter(Index idx) {
        char ch = arenaBuffer.getSett(idx);
        return Character.isAlphabetic(ch) ? ch : 0;
    }

    //renderer update functions
    private void startGame() {
        simThread.start();
    }

    private void stopGame(boolean won) {
        if (simThread.isAlive()) {
            showGameOver(won);

            throw new GameOverException();
        }
    }

    private void showDisplay() {
        frame = new JFrame("jpads game simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(renderer);
        frame.pack();
        frame.setLocationRelativeTo(null);

        JMenuBar mb = new JMenuBar();
        JMenu m = new JMenu("Game");
        m.add("Start").addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        m.add("Exit").addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        mb.add(m);
        frame.setJMenuBar(mb);

        frame.setVisible(true);
    }

    private void showFoundLetter(Player p, Letter l) {
        renderer.showMessage(p + " found letter " + l);
    }

    private void showGameOver(boolean won) {
        renderer.showMessage("You " + (won ? "WON!" : "LOST!"));
    }

    private void clearPlayer(Player p) {
        char ch = Levels.CLEAR;
        arenaBuffer.setSett(ch, p.getIdx());
    }

    private void showPlayer(Player p) {
        char ch = (char) (p.getID() + 48);
        arenaBuffer.setSett(ch, p.getIdx());
    }

    static class GameOverException extends RuntimeException {
    }
}
