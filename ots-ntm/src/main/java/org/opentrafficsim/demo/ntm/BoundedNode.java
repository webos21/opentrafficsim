package org.opentrafficsim.demo.ntm;

import org.opentrafficsim.core.unit.LengthUnit;
import org.opentrafficsim.core.unit.SpeedUnit;
import org.opentrafficsim.core.value.vdouble.scalar.DoubleScalar;

import com.vividsolutions.jts.geom.Point;

/**
 * <p>
 * Copyright (c) 2013-2014 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights
 * reserved. <br>
 * BSD-style license. See <a href="http://opentrafficsim.org/node/13">OpenTrafficSim License</a>.
 * <p>
 * @version Sep 12, 2014 <br>
 * @author <a href="http://www.tbm.tudelft.nl/averbraeck">Alexander Verbraeck</a>
 * @author <a href="http://Hansvanlint.weblog.tudelft.nl">Hans van Lint</a>
 * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
 * @author <a href="http://www.citg.tudelft.nl">Guus Tamminga</a>
 * @author <a href="http://www.citg.tudelft.nl">Yufei Yuan</a>
 */
public class BoundedNode extends Node
{
    /** */
    private static final long serialVersionUID = 20140920L;

    /** */
    private Area area;

    /** */
    private CellBehaviour cellBehaviour;

    /**
     * /**
     * @param centroid the center of the area for the simplified graph.
     * @param nr Node number
     * @param area the area to which the node belongs.
     * @param behaviourType describes behaviour of the node depending on its type
     * @param parametersNTM
     */
    public BoundedNode(final Point centroid, final String nr, final Area area,
            final TrafficBehaviourType behaviourType)
    {
        super(nr, centroid, behaviourType);
        if (behaviourType == TrafficBehaviourType.ROAD)
        {
            this.setCellBehaviour(null);
            this.setArea(area);
        }
        else if (behaviourType == TrafficBehaviourType.NTM)
        {
            DoubleScalar.Abs<SpeedUnit> speed = new DoubleScalar.Abs<SpeedUnit>(10, SpeedUnit.KM_PER_HOUR);
            DoubleScalar.Abs<LengthUnit> roadLength = new DoubleScalar.Abs<LengthUnit>(10, LengthUnit.KILOMETER);
            //TODO parameters should depend on area characteristics
            ParametersNTM parametersNTM = new ParametersNTM(25.0, 50.0, 100, speed, roadLength);
            this.setCellBehaviour(new CellBehaviourNTM(area, parametersNTM));
            this.setArea(area);

        }
        else if (behaviourType == TrafficBehaviourType.FLOW)
        {
            this.setCellBehaviour(new CellBehaviourFlow());
            this.setArea(area);
        }
        else if (behaviourType == TrafficBehaviourType.CORDON)
        {
            this.setCellBehaviour(new CellBehaviourCordon());
            this.setArea(area);
        }
        else
        {
            this.setCellBehaviour(null);
            this.setArea(area);
        }
    }

   

    /**
     * @return area.
     */
    public final Area getArea()
    {
        return this.area;
    }

    /**
     * @param area set area.
     */
    public final void setArea(final Area area)
    {
        this.area = area;
    }

    /**
     * @return cellBehaviour.
     */
    public final CellBehaviour getCellBehaviour()
    {
        return this.cellBehaviour;
    }

    /**
     * @param cellBehaviour set cellBehaviour.
     */
    public final void setCellBehaviour(final CellBehaviour cellBehaviour)
    {
        this.cellBehaviour = cellBehaviour;
    }

}
