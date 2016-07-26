/*
 * @(#)SendToBackCommand.java
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

/**
 * A command to send the selection to the back of the drawing.
 *
 * @version <$CURRENT_VERSION$>
 */
public class SendToBackCommand extends AbstractCommand {

    /**
	 * Constructs a send to back command.
	 * @param name the command name
	 * @param newDrawingEditor the DrawingEditor which manages the views
	 */
    public SendToBackCommand(String name, DrawingEditor newDrawingEditor) {
        super(name, newDrawingEditor);
    }

    public void execute() {
        super.execute();
        setUndoActivity(createUndoActivity());
        getUndoActivity().setAffectedFigures(new ReverseFigureEnumerator(view().selectionZOrdered()));
        FigureEnumeration fe = getUndoActivity().getAffectedFigures();
        while (fe.hasMoreElements()) {
            view().drawing().sendToBack(fe.nextFigure());
        }
        view().checkDamage();
    }

    protected boolean isExecutableWithView() {
        return view().selectionCount() > 0;
    }

    protected Undoable createUndoActivity() {
        return new SendToBackCommand.UndoActivity(view());
    }

    public static class UndoActivity extends UndoableAdapter {
        private Hashtable myOriginalLayers;

        public UndoActivity(DrawingView newDrawingView) {
            super(newDrawingView);
            myOriginalLayers = new Hashtable();
            setUndoable(true);
            setRedoable(true);
        }

        public boolean redo() {
            // do not call execute directly as the selection might has changed
            if (!isRedoable()) {
                return false;
            }

            FigureEnumeration fe = getAffectedFigures();
            while (fe.hasMoreElements()) {
                sendToCommand(fe.nextFigure());
            }

            return true;
        }

        protected void sendToCommand(Figure f) {
            getDrawingView().drawing().sendToBack(f);
        }

        protected void addOriginalLayer(Figure affectedFigure, int newOriginalLayer) {
            myOriginalLayers.put(affectedFigure, new Integer(newOriginalLayer));
        }

        protected int getOriginalLayer(Figure lookupAffectedFigure) {
            return ((Integer)myOriginalLayers.get(lookupAffectedFigure)).intValue();
        }
    }
}