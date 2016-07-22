/*
 * @(#)ChopBoxConnector.java
 *
 * Project:		JHotdraw - a GUI framework for technical drawings
 *				http://www.jhotdraw.org
 *				http://jhotdraw.sourceforge.net
 * Copyright:	� by the original author(s) and all contributors
 * License:		Lesser GNU Public License (LGPL)
 *				http://www.opensource.org/licenses/lgpl-license.html
 */

package CH.ifa.draw.standard;

import java.awt.*;
import CH.ifa.draw.framework.*;
import CH.ifa.draw.util.Geom;

/**
 * A ChopBoxConnector locates connection points by
 * choping the connection between the centers of the
 * two figures at the display box.
 *
 * @see Connector
 *
 * @version <$CURRENT_VERSION$>
 */
public class ChopBoxConnector extends AbstractConnector {

    /*
	 * Serialization support.
	 */
    private static final long serialVersionUID = -1461450322712345462L;

    public ChopBoxConnector() { // only used for Storable implementation
    }

    public ChopBoxConnector(Figure owner) {
        super(owner);
    }

    protected Point chop(Figure target, Point from) {
        Rectangle r = target.displayBox();
        return Geom.angleToPoint(r, (Geom.pointToAngle(r, from)));
    }
}
