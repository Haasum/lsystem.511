package com.lsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.lsystem.StaticView.MENU_WIDTH;
import static com.lsystem.StaticView.screenSize;
import static com.lsystem.Texture.*;

public class DynamicView extends JPanel {

    ButtonExpandListener buttonListener;
    RecursiveLsys lsys;
    Graphics2D turtle;
    Graphics2D g2d;
    ArrayList<NonTerminal> listOfNT;
    NonTerminal nt;
    ButtonExpandListener buttonExpandListener;
    ArrayList<NonTerminal> ntArray;

    ExpandNodeMouseListener expandNodeMouseListener;
    private Map<NonTerminal, Point> testHashMap;

    static int screenHeight = (int) screenSize.getHeight();
    static int screenWidth = (int) screenSize.getWidth();
    static int middleX = (screenWidth - MENU_WIDTH) / 2;
    private static final int BRANCH_HEIGHT = -40;
    AffineTransform originalTrans = AffineTransform.getTranslateInstance(middleX, screenHeight - 100);
    ArrayList<AffineTransform> subTrees = new ArrayList<AffineTransform>();


    public DynamicView(RecursiveLsys lsys) {
        super();
        this.lsys = lsys;
        System.out.println("Jeg er axiomet: " + lsys.getTree());

        makeMouseListener();
        testHashMap = new HashMap<>();
        listOfNT = new ArrayList<NonTerminal>();

    }

    private void makeMouseListener() {
        expandNodeMouseListener = new ExpandNodeMouseListener(this);
    }


    @Override
    public void paintComponent(Graphics g) {
        listOfNT.clear();
        testHashMap.clear();
        super.paintComponent(g);

        turtle = (Graphics2D) g.create();
        g2d = (Graphics2D) g.create();

        makeBackground(turtle);

        turtle.setTransform(originalTrans);

        int j = 0;
        for (int i = 0; i < lsys.getTree().length(); i++) {
            char currentCheck = lsys.getTree().charAt(i);


            switch (currentCheck) {
                case 'F':
                    j++;
                    if (j == 1) {
                        makeLog(turtle);
                    } else {
                        growBranch(turtle);
                    }
                    break;
                case 'A':
                    drawNonTerminal(turtle, g2d, i, currentCheck);
                    break;
                case 'B':
                    drawNonTerminal(turtle, g2d, i, currentCheck);
                    break;
                case 'C':
                    drawNonTerminal(turtle, g2d, i, currentCheck);
                    break;
                case '+':
                    rotateRight(turtle);
                    break;
                case '-':
                    rotateLeft(turtle);
                    break;
                case '[':
                    push(turtle);
                    break;
                case ']':
                    pop(turtle);
                    break;
                default:
                    System.out.println("Char not in alphabet");
                    break;
            }
        }
        repaint();
        requestFocus(); //keyexpand will work, even if buttons in the left panel are pressed last
    }

    private void push(Graphics2D turtle) {
        subTrees.add(turtle.getTransform());
    }

    private void pop(Graphics2D turtle) {
        AffineTransform t = subTrees.get(subTrees.size() - 1);
        turtle.setTransform(t);
        subTrees.remove(subTrees.size() - 1);
    }

    private void growBranch(Graphics2D turtle) {
        turtle.setStroke(new BasicStroke(2.0f));

        turtle.drawLine(0, 0, 0, BRANCH_HEIGHT);
        turtle.translate(0, BRANCH_HEIGHT);
        drawLeafs(turtle);
    }

    private void drawLeafs(Graphics2D turtle) {

        for (int i = 0; i < 4; i++) { //loop for drawing the leafs
            turtle.drawImage(leafImg, 0, (i - 1) * ((-1) * BRANCH_HEIGHT / 4), this);
            turtle.drawImage(leafImg2, -15, (i - 1) * ((-1) * (10 * BRANCH_HEIGHT) / 47), this); //the leafs are drawn with a spacing of 4,7 pixel
        }

    }

    private void rotateLeft(Graphics2D turtle) {
        turtle.rotate(Math.PI / 8);
    }

    private void rotateRight(Graphics2D turtle) {
        turtle.rotate(-Math.PI / 8);
    }

    private void makeLog(Graphics2D turtle) {
        GeneralPath logShape = new GeneralPath();
        final double points[][] = {
                {-2, -200}, {2, -200},
                {6, 0}, {-6, 0}
        };

        logShape.moveTo(points[0][0], points[0][1]);
        for (int k = 1; k < points.length; k++)
            logShape.lineTo(points[k][0], points[k][1]);

        logShape.closePath();

        turtle.fill(logShape);
        turtle.translate(0, -200);

    }

    private void makeBackground(Graphics2D turtle) {
        g2d.drawImage(backImg, 0, 0, screenWidth, screenHeight, this); //backgroundIMG. placed on position 0,0 - and scaled to fit screensize
        turtle.setColor(Color.BLACK);
        turtle.setPaint(Texture.barkTex);

    }

    public void drawNonTerminal(Graphics2D turtle, Graphics2D g2dd, int i, char c) {
        AffineTransform newTransform = turtle.getTransform();
        nt = new NonTerminal(g2dd, newTransform, this, i, c);//, affineTransform);
        listOfNT.add(nt);
    }

    public void ntClicked(int mouseX, int mouseY) {

        ntArray = new ArrayList<NonTerminal>();


        for (NonTerminal nt : listOfNT) {

            if (nt.getNtCircle().contains(mouseX, mouseY) == true) {
                System.out.println("jeg er inde i en cirkel");
                ntArray.add(nt);
            } else {

                System.out.println("Du har klikket ved siden af.");
            }

        }
        System.out.println(ntArray.size());
        if (ntArray.isEmpty() == false) {
            expandNode(ntArray);
        }
    }

    private void expandNode(ArrayList ntArray) {
        buttonExpandListener = new ButtonExpandListener(ntArray, lsys);
    }
}