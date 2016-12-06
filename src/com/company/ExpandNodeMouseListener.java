package com.company;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;

/**
 * Created by MasterWillis on 06/12/2016.
 */
public class ExpandNodeMouseListener implements MouseListener{

    Turtle turtle;
    Point mouseP;

    public ExpandNodeMouseListener(Turtle turtle){
        this.turtle = turtle;
        turtle.addMouseListener(this);
        System.out.println(turtle.getTestHashMap());
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        int x = e.getX();
        int y = e.getY();
        int allowedDiff = 5;

        mouseP = new Point(x,y);

        Collection<Point> nonTerminalPoints = turtle.getTestHashMap().values();

        for (Point p : nonTerminalPoints ) {
            System.out.println("Dette er mit punkt "+ p);
            if( ((p.getX() - mouseP.getX()) < allowedDiff && (p.getY() - mouseP.getY()) < allowedDiff) || ((mouseP.getX() - p.getX()) < allowedDiff && (mouseP.getY() - p.getY()) < allowedDiff )){

                System.out.println("JEG SKAL EKSPANDERE på punkt nr " + mouseP);
            }
            else{
                System.out.println("ØV");
            }

        }






    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}