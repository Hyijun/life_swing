import javax.swing.*;
import java.awt.*;

public class Life_swing {
    private int times = 0;

    private JTextArea jt = new JTextArea(700, 150);


    private boolean[][] form = new boolean[50][50]; //定义一个二维布尔数组，用于存储整个场景

    public Life_swing() {
        for (int i = 0; i < form.length; i++) {
            for (int j = 0; j < form.length; j++) {
                if (new java.util.Random().nextBoolean()) {
                    form[i][j] = true;
                }
            }
        } //初始化场景，使用随机
        JFrame jf = new JFrame("Life");
        jf.setSize(550, 850);
        jf.setVisible(true);
        Container con = jf.getContentPane();
        con.add(jt);
    }

    private int get_amount(int x, int y) { //用于计算一个生命周围的生命数
        int count = 0; //计数
        if ((x != 0) && (y != 0)) {
            if (y + 1 != form[x].length && x + 1 != form.length) {
                count += get_number(this.form[x - 1][y - 1]) + get_number(this.form[x - 1][y]) + get_number(this.form[x - 1][y + 1]);
                count += get_number(this.form[x][y - 1]) + get_number(this.form[x][y + 1]);
                count += get_number(this.form[x + 1][y - 1]) + get_number(this.form[x + 1][y]) + get_number(this.form[x + 1][y + 1]);

            } else if (x + 1 != form.length) {
                count += get_number(this.form[x - 1][y - 1]) + get_number(this.form[x - 1][y]);
                count += get_number(this.form[x][y - 1]);
                count += get_number(this.form[x + 1][y - 1]) + get_number(this.form[x + 1][y]);
            } else {
                count += get_number(this.form[x - 1][y - 1]) + get_number(this.form[x - 1][y]);
                count += get_number(this.form[x][y - 1]);
            }
        } else if (x != 0) {
            if (x + 1 != this.form.length) {
                count += get_number(this.form[x - 1][y]) + get_number(this.form[x - 1][y + 1]);
                count += get_number(this.form[x][y + 1]);
                count += get_number(this.form[x + 1][y]) + get_number(this.form[x + 1][y + 1]);
            } else {
                count += get_number(this.form[x - 1][y]) + get_number(this.form[x - 1][y + 1]);
                count += get_number(this.form[x][y + 1]);
            }
        } else {
            if (y != 0 && y + 1 != form[x].length) {
                count += get_number(this.form[x][y - 1]) + get_number(this.form[x][y + 1]);
                count += get_number(this.form[x + 1][y - 1]) + get_number(this.form[x + 1][y]) + get_number(this.form[x + 1][y + 1]);
            } else if (y != 0) {
                count += get_number(this.form[x][y - 1]);
                count += get_number(this.form[x + 1][y - 1]) + get_number(this.form[x + 1][y]);
            } else if (y + 1 != form[x].length) {
                count += get_number(this.form[x][y + 1]);
                count += get_number(this.form[x + 1][y]) + get_number(this.form[x + 1][y + 1]);
            }
        }
        return count;
    }

    private int get_number(boolean a) { //判断是否有生命存在
        if (a) {
            return 1;
        } else {
            return 0;
        }
    }

    private void play() {
        this.times++;
        place[] re = new place[16384];
        int seek = 0;
        for (int i = 0; i < form.length; i++) {
            for (int j = 0; j < form.length; j++) {
                int LIVE_FEW = 2;
                int LIVE_MANY = 3;
                if ((get_amount(i, j) == LIVE_MANY || get_amount(i, j) == LIVE_FEW) && form[i][j]) {
                    re[seek] = new place(i, j);
                    seek++;
                }
                if (get_amount(i, j) == LIVE_MANY && !this.form[i][j]) {
                    re[seek] = new place(i, j);
                    seek++;
                }

            }
        }
        for (int i = 0; i < form.length; i++) {
            for (int j = 0; j < form.length; j++) {
                this.form[i][j] = false;
            }
        }
        for (place place : re) {
            try {
                form[place.x][place.y] = true;
            } catch (NullPointerException e) {
                break;
            }
        }
        try {
            Thread.sleep(230);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                jt.replaceRange("", 0, 50);
            } catch (java.lang.IllegalArgumentException e) {
                break;
            }
        }
        StringBuilder s = new StringBuilder();
        for (boolean[] booleans : form) {
            for (int j = 0; j < form.length; j++) {
                if (booleans[j]) {
                    s.append("⬛");
                } else {
                    s.append("⬜");
                }
            }
            s.append("\n");

        }
        jt.append(s.toString());
        System.out.println("===============end=第" + this.times + "回合====================");
    }


    public static void main(String[] args) {
        Life_swing lifeSwing = new Life_swing();
        while (true) {
            try {
                lifeSwing.play();
            } catch (Exception e) {
                break;
            }
        }
    }
}


class place {
    final int x;
    final int y;

    place(int x, int y) {
        this.x = x;
        this.y = y;
    }
}