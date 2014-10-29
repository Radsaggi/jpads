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
public class Levels {

    private final int maxClones;
    private final int rows;
    private final int columns;
    private final Index init;
    private final String word;
    private final Setting setting;

    public Levels(int maxClones, Index init, String word, Setting setting) {
        this.maxClones = maxClones;
        this.init = init;
        this.rows = setting.getRows();
        this.columns = setting.getColumns();
        this.word = word.toUpperCase();
        this.setting = setting;
    }
   
    public int getMaxClones() {
        return maxClones;
    }

    public Index getInit() {
        return init;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public String getWord() {
        return word;
    }

    public Setting getSetting() {
        return setting;
    }

    public static final Levels LEVEL1 = new Levels(10, new Index(9, 0), "START",
            new Setting(10, 10, new String[]{
                " *      * ",
                "*        *",
                "          ",
                "   * * *  ",
                "T   A*T   ",
                "R  * * *  ",
                "T         ",
                "A        *",
                "S        *",
                "        *S"
            })
    );

    public static final Levels LEVEL2 = new Levels(10, new Index(9, 0), "START", 
            new Setting(10, 10, new String[]{
                " *  *S*  *",
                "*        *",
                "          ",
                "*  * * * *",
                "A    R   T",
                "*  * * * *",
                "          ",
                "         *",
                "         *",
                "    *T*  *"
            })
    );

    public static final Levels LEVEL3 = new Levels(10, new Index(9, 0), "START", 
            new Setting(10, 10, new String[]{
                "T*      *S",
                "*        *",
                "          ",
                "   * * *  ",
                "    T*R   ",
                "   * * *  ",
                "          ",
                "         *",
                "         *",
                "        *A"
            })
    );

    static final char BLOCKER = '*', CLEAR = ' ';
}
