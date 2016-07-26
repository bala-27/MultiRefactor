/*
 * @(#)BringToFrontCommand.java
 *
 * Project:		JHotdraw - a GUI framework for technical drawings
 *				http://www.jhotdraw.org
 *				http://jhotdraw.sourceforge.net
 * Copyright:	� by the original author(s) and all contributors
 * License:		Lesser GNU Public License (LGPL)
 *				http://www.opensource.org/licenses/lgpl-license.html
 */

package CH.ifa.draw.standard;

import CH.ifa.draw.framework.*;
import CH.ifa.draw.util.UndoableAdapter;
import CH.ifa.draw.util.Undoable;
import java.util.*;

import CH.ifa.draw.framework.*;

/**
 * BringToFrontCommand brings the selected figures in the front of
 * the other figures.
 *
 * @see SendToBackCommand
 * @version <$CURRENT_VERSION$>
 */
public class BringToFrontCommand extends AbstractCommand {

    /**
	 * Constructs a bring to front command.
	 * @param name the command name
	 * @param newDrawingEditor the DrawingEditor which manages the views
	 */
    public BringToFrontCommand(String name, DrawingEditor newDrawingEditor) {
        super(name, newDrawingEditor);
    }

    public void execute() {
        super.execute();
        setUndoActivity(createUndoActivity());
        getUndoActivity().setAffectedFigures(view().selectionElements());
        FigureEnumeration fe = getUndoActivity().getAffectedFigures();
        while (fe.hasMoreElements()) {
            view().drawing().bringToFront(fe.nextFigure());
        }
        view().checkDamage();
    }

    public boolean isExecutableWithView() {
        return view().selectionCount() > 0;
    }

    public static class UndoActivity extends SendToBackCommand.UndoActivity {

        public void setAffectedFigures(FigureEnumeration fe) {
            // first make copy of FigureEnumeration in superclass
            super.setAffectedFigures(fe);
            // then get new FigureEnumeration of copy to save attributes
            FigureEnumeration copyFe = getAffectedFigures();
            while (copyFe.hasMoreElements()) {
                Figure f = copyFe.nextFigure();
                int originalLayer = getDrawingView().drawing().getLayer(f);
                addOriginalLayer(f, originalLayer);
            }
        }
        public UndoActivity(DrawingView newDrawingView) {
            super(newDrawingView);
        }

        protected void sendToCommand(Figure f) {
            getDrawingView().drawing().bringToFront(f);
        }
    }
}