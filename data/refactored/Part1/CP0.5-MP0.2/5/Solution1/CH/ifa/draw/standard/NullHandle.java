/*
 * @(#)NullHandle.java
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

import java.awt.*;
import CH.ifa.draw.figures.*;

/**
 * A handle that doesn't change the owned figure. Its only purpose is
 * to show feedback that a figure is selected.
 * <hr>
 * <b>Design Patterns</b><P>
 * <img src="images/red-ball-small.gif" width=6 height=6 alt=" o ">
 * <b>NullObject</b><br>
 * NullObject enables to treat handles that don't do
 * anything in the same way as other handles.
 *
 * @version <$CURRENT_VERSION$>
 */
public class NullHandle extends LocatorHandle {

    /**
	 * Draws the Group handle.
	 */
    public void draw(Graphics g) {
        Rectangle r = displayBox();

        g.setColor(Color.black);
        g.drawRect(r.x, r.y, r.width, r.height);
        r.grow(-1, -1);
        g.setColor(Color.white);
        g.drawRect(r.x, r.y, r.width, r.height);
    }

    public NullHandle(Figure owner, Locator locator) {
        super(owner, locator);
    }
}