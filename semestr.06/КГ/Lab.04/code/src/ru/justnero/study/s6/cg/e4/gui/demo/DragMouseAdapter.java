package ru.justnero.study.s6.cg.e4.gui.demo;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class DragMouseAdapter extends MouseAdapter {

    public void mousePressed(MouseEvent e) {
        JComponent c = (JComponent) e.getSource();

//        dragListener.dragGestureRecognized(new DragGestureEvent());
        TransferHandler handler = c.getTransferHandler();

        handler.exportAsDrag(c, e, TransferHandler.COPY);
    }
}
