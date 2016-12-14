package com.lsystem.view;

import com.lsystem.control.UserInput;
import com.lsystem.model.RecursiveLsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

import static com.lsystem.view.DynamicView.screenHeight;
import static com.lsystem.view.DynamicView.screenWidth;




public class StaticView extends JFrame {
    final static Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    JPanel dynamicView;
    MenuPanel menuPanel;
    RecursiveLsystem lsystem;
    VisualComponents visualComponents;

    final static int MENU_WIDTH = 150;

    public StaticView(RecursiveLsystem lsystem){
        this.lsystem = lsystem;
        drawMainFrame();
        drawMainPanel();
        drawMenuPanel();



        visualComponents = new VisualComponents(true);

    }

    private void drawMainPanel() {
        dynamicView = new DynamicView(lsystem);
        dynamicView.setVisible(true);
        dynamicView.setSize(screenWidth-MENU_WIDTH,screenHeight);
        dynamicView.setBackground(Color.WHITE);
        dynamicView.setLayout(null);
        dynamicView.setLocation(MENU_WIDTH,0);
        this.add(dynamicView);
    }

    private void drawMainFrame() {
        setTitle("Growing Tree");
        setSize(SCREEN_SIZE);
        setVisible(true);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void drawMenuPanel() {

        menuPanel = new MenuPanel(lsystem, dynamicView, this);
        menuPanel.setVisible(true);
        menuPanel.setSize(MENU_WIDTH, screenHeight);
        menuPanel.setBackground(new Color(255,255,255));
        menuPanel.setLayout(null);
        menuPanel.setLocation(0,0);
        this.add(menuPanel);
    }

    public void addListeners(UserInput userInput){

            this.addKeyListener((KeyListener) userInput);
            dynamicView.addKeyListener((KeyListener) userInput);

    }
}
