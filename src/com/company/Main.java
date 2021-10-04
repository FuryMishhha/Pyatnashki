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
        playGround.setDoubleBuffered(true);
        container.add(playGround);
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
            }
        }
    }
}