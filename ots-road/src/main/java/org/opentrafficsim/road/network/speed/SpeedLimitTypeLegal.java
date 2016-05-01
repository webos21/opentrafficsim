package org.opentrafficsim.road.network.speed;


/**
 * One could regard this class as a SpeedLimitTypeSpeed implementing a marker interface 'Legal'. However, as legal speed limits 
 * always pertain to Speed info (and not e.g. curve radius), no separate interface is required. 
 * <p>
 * Copyright (c) 2013-2016 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved. <br>
 * BSD-style license. See <a href="http://opentrafficsim.org/node/13">OpenTrafficSim License</a>.
 * <p>
 * @version $Revision$, $LastChangedDate$, by $Author$, initial version Apr 30, 2016 <br>
 * @author <a href="http://www.transport.citg.tudelft.nl">Wouter Schakel</a>
 */

public class SpeedLimitTypeLegal extends SpeedLimitTypeSpeed
{

    /** */
    private static final long serialVersionUID = 20160501L;
    
    /**
     * Constructor.
     * @param id id of this speed limit type, which must be unique
     */
    public SpeedLimitTypeLegal(final String id)
    {
        super(id);
    }

}
