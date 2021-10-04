package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Main extends JFrame {
    public static void main(String[] args) {
        JFrame app = new Main();
        app.setVisible(true);
    }

    private JPanel playGround = new JPanel(new GridLayout(4, 4, 2, 2));
    private JMenuBar menu = null;
    private final String fileItems[] = new String []{"New", "Exit"};
    private static Random generator = new Random();
    private int[][] field = new int[4][4];

    public Main() {
        super("Пятнашки");
        setBounds(660, 240,300, 300);
        setResizable(false);
        createMenu();
        setJMenuBar(menu);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = getContentPane();
        base_generation();
        playGround.setDoubleBuffered(true);
        container.add(playGround);
        rebuild();
    }

    public void base_generation() {
        int[] invariants = new int[16];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                field[i][j] = 0;
                invariants[i*4 + j] = 0;
            }
        }

        for (int i = 1; i < 16; i++) {
            int k;
            int l;
            do {
                k = generator.nextInt(100) % 4;
                l = generator.nextInt(100) % 4;
            }
            while (field[k][l] != 0);
            field[k][l] = i;
            invariants[k*4+l] = i;
        }

        boolean change = true;
        int counter = 1;
        while (change) {
            change = false;
            for (int i = 0; i < 16; i++) {
                if (invariants[i] != i) {
                    for (int j = 0; j < 16; j++) {
                        if (invariants[j] == i) {
                            int temp = invariants[i];
                            invariants[i] = invariants[j];
                            invariants[j] = temp;
                            change = true;
                            counter++;
                            break;
                        }
                    }
                    break;
                }
            }
        }

        if (counter % 2 != 0) {
            int temp = field[0][0];
            field[0][0] = field[3][3];
            field[3][3] = temp;
        }
    }

    public void rebuild() {
        playGround.removeAll();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                JButton button = new JButton(Integer.toString(field[i][j]));
                button.setFocusable(false);
                playGround.add(button);
                if (field[i][j] == 0)
                    button.setVisible(false);
                else
                    button.addActionListener(new ClickListener());
            }
        }
        playGround.validate();
    }

    private void createMenu() {
        menu = new JMenuBar();
        JMenu fileMenu = new JMenu("Файл");
        for (int i = 0; i < fileItems.length; i++) {
            JMenuItem item = new JMenuItem(fileItems[i]);
            item.setActionCommand(fileItems[i].toLowerCase());
            item.addActionListener(new NewMenuListener());
            fileMenu.add(item);
        }
        fileMenu.insertSeparator(1);
        menu.add(fileMenu);
    }

    private class NewMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if ("exit".equals(command)) {
                System.exit(0);
            }
            if ("new".equals(command)) {
                base_generation();
                rebuild();
            }
        }
    }

    private class ClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            button.setVisible(false);
            String name = button.getText();
            change(Integer.parseInt(name));
        }
    }

    public void change(int num) {
        int i = 0, j = 0;
        for (int k = 0; k < 4; k++) {
            for (int l = 0; l < 4; l++) {
                if (field[k][l] == num) {
                    i = k;
                    j = l;
                }
            }
        }
        if (i > 0) {
            if (field[i - 1][j] == 0) {
                field[i - 1][j] = num;
                field[i][j] = 0;
            }
        }
        if (i < 3) {
            if (field[i + 1][j] == 0) {
                field[i + 1][j] = num;
                field[i][j] = 0;
            }
        }
        if (j > 0) {
            if (field[i][j - 1] == 0) {
                field[i][j - 1] = num;
                field[i][j] = 0;
            }
        }
        if (j < 3) {
            if (field[i][j + 1] == 0) {
                field[i][j + 1] = num;
                field[i][j] = 0;
            }
        }
        rebuild();
    }
}