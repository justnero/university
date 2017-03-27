package ru.justnero.study.s6.cg.e4.gui.demo;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class ComplexExample extends JFrame
        implements DragGestureListener {

    JPanel panel;
    JPanel left;

    public ComplexExample() {

        setTitle("Complex Example");

        panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 15));

        JButton openb = new JButton("Choose Color");
        openb.setFocusable(false);

        left = new JPanel();
        left.setBackground(Color.red);
        left.setPreferredSize(new Dimension(100, 100));

        openb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JColorChooser clr = new JColorChooser();
                Color color = clr.showDialog(panel, "Choose Color", Color.white);
                left.setBackground(color);
            }
        });

        JPanel right = new JPanel();
        right.setBackground(Color.white);
        right.setPreferredSize(new Dimension(100, 100));

        new MyDropTargetListener(right);

        DragSource ds = new DragSource();
        ds.createDefaultDragGestureRecognizer(left,
                DnDConstants.ACTION_COPY, this);

        panel.add(openb);
        panel.add(left);
        panel.add(right);
        add(panel);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ComplexExample();
    }

    public void dragGestureRecognized(DragGestureEvent event) {
        Cursor cursor = null;
        JPanel panel = (JPanel) event.getComponent();

        Color color = panel.getBackground();

        if (event.getDragAction() == DnDConstants.ACTION_COPY) {
            cursor = DragSource.DefaultCopyDrop;
        }

        event.startDrag(cursor, new TransferableColor(color));
    }

    class MyDropTargetListener extends DropTargetAdapter {

        private DropTarget dropTarget;
        private JPanel panel;

        public MyDropTargetListener(JPanel panel) {
            this.panel = panel;

            dropTarget = new DropTarget(panel, DnDConstants.ACTION_COPY,
                    this, true, null);
        }


        public void drop(DropTargetDropEvent event) {
            try {

                Transferable tr = event.getTransferable();
                Color color = (Color) tr.getTransferData(TransferableColor.colorFlavor);

                if (event.isDataFlavorSupported(TransferableColor.colorFlavor)) {

                    event.acceptDrop(DnDConstants.ACTION_COPY);
                    this.panel.setBackground(color);
                    event.dropComplete(true);
                    return;
                }
                event.rejectDrop();
            } catch (Exception e) {
                e.printStackTrace();
                event.rejectDrop();
            }
        }
    }
}


class TransferableColor implements Transferable {

    protected static DataFlavor colorFlavor =
            new DataFlavor(Color.class, "A Color Object");

    protected static DataFlavor[] supportedFlavors = {
            colorFlavor,
            DataFlavor.stringFlavor,
    };

    Color color;

    public TransferableColor(Color color) {
        this.color = color;
    }

    public DataFlavor[] getTransferDataFlavors() {
        return supportedFlavors;
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        if (flavor.equals(colorFlavor) ||
                flavor.equals(DataFlavor.stringFlavor)) return true;
        return false;
    }


    public Object getTransferData(DataFlavor flavor)
            throws UnsupportedFlavorException {
        if (flavor.equals(colorFlavor))
            return color;
        else if (flavor.equals(DataFlavor.stringFlavor))
            return color.toString();
        else
            throw new UnsupportedFlavorException(flavor);
    }
}