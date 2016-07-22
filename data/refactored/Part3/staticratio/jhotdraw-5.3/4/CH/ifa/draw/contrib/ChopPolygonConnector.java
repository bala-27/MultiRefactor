/*
 * @(#)ChopPolygonConnector.java
 *
 * Project:		JHotdraw - a GUI framework for technical drawings
 *				http://www.jhotdraw.org
 *				http://jhotdraw.sourceforge.net
 * Copyright:	� by the original author(s) and all contributors
 * License:		Lesser GNU Public License (LGPL)
 *				http://www.opensource.org/licenses/lgpl-license.html
 */

package CH.ifa.draw.contrib;

import java.awt.*;
import CH.ifa.draw.framework.*;
import CH.ifa.draw.standard.*;

/**
 * A ChopPolygonConnector locates a connection point by
 * chopping the connection at the polygon boundary.
 *
 * @author Erich Gamma
 * @version <$CURRENT_VERSION$>
 */
public class ChopPolygonConnector extends ChopBoxConnector {

    public ChopPolygonConnector() {
    }

    public ChopPolygonConnector(Figure owner) {
        super(owner);
    }

    protected Point chop(Figure target, Point from) {
        return ((PolygonFigure)target).chop(from);
    }
}
