package ru.justnero.study.s6.cg.e4.gui;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import ru.justnero.study.s6.cg.e4.main.Quad;
import ru.justnero.study.s6.cg.e4.util.Vector2D;

class TransferableFigure implements Transferable {

    protected static DataFlavor figureFlavor =
            new DataFlavor(Quad.class, "A Quad Object");

    protected static DataFlavor velocityFlavor =
            new DataFlavor(Vector2D.class, "A Vector2D Object");


    protected static DataFlavor colorFlavor =
            new DataFlavor(Color.class, "A Color Object");

    protected static DataFlavor[] supportedFlavors = {
            figureFlavor,
            velocityFlavor,
            colorFlavor,
    };

    Quad figure;
    Vector2D velocity;
    Color color;

    public TransferableFigure(Quad figure, Vector2D velocity) {
        this.figure = figure;
        this.velocity = velocity;
    }

    public DataFlavor[] getTransferDataFlavors() {
        return supportedFlavors;
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        if (flavor.equals(figureFlavor) ||
                flavor.equals(velocityFlavor) ||
                flavor.equals(colorFlavor)) return true;

        return false;
    }


    public Object getTransferData(DataFlavor flavor)
            throws UnsupportedFlavorException {

        if (flavor.equals(figureFlavor))
            return figure;
        else if (flavor.equals(velocityFlavor))
            return velocity;
        else if (flavor.equals(colorFlavor))
            return color;
        else throw new UnsupportedFlavorException(flavor);
    }
}
