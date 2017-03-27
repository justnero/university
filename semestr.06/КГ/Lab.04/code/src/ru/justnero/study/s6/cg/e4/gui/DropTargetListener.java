package ru.justnero.study.s6.cg.e4.gui;

import java.awt.*;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;

import javax.swing.*;

import ru.justnero.study.s6.cg.e4.main.ClipBox;
import ru.justnero.study.s6.cg.e4.main.Lab4Scene;
import ru.justnero.study.s6.cg.e4.main.Labyrinth;
import ru.justnero.study.s6.cg.e4.main.Quad;
import ru.justnero.study.s6.cg.e4.util.TransformMatrix;
import ru.justnero.study.s6.cg.e4.util.Vector2D;

public class DropTargetListener extends DropTargetAdapter {
    protected final static TransformMatrix transformMatrix = new TransformMatrix();
    private JPanel panel;
    private Lab4Scene scene;
    private DropTarget dropTarget;

    public DropTargetListener(JPanel panel, Lab4Scene scene) {
        this.panel = panel;
        this.scene = scene;

        dropTarget = new DropTarget(panel, DnDConstants.ACTION_COPY,
                this, true, null);
    }

    public void drop(DropTargetDropEvent event) {
        try {

            Transferable tr = event.getTransferable();

            Quad quad = (Quad) tr.getTransferData(TransferableFigure.figureFlavor);
            Vector2D velocity = (Vector2D) tr.getTransferData(TransferableFigure.velocityFlavor);

            setTransform(event.getLocation());
            quad.transform(transformMatrix);
            quad.applyChange();

            if (event.isDataFlavorSupported(TransferableFigure.figureFlavor)) {
                event.acceptDrop(DnDConstants.ACTION_COPY);

                if (quad instanceof ClipBox) {
                    scene.addClipBox((ClipBox) quad);
                } else if (quad instanceof Labyrinth) {
                    scene.addLabyrinth((Labyrinth) quad);
                } else {
                    scene.addQuad(quad, velocity);
                }

                event.dropComplete(true);
                return;
            }
            event.rejectDrop();
        } catch (Exception e) {
            e.printStackTrace();
            event.rejectDrop();
        }
    }

    private void setTransform(Point location) {
        float x, y;

        x = 2 * location.x / (float) panel.getWidth();
        y = 2 * (1 - location.y / (float) panel.getHeight());

        x -= 1;
        y -= 1;

        transformMatrix.translate(x, y);
    }


}
