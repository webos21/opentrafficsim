package org.opentrafficsim.core.gtu.generator;

import java.rmi.RemoteException;

import nl.tudelft.simulation.dsol.SimRuntimeException;

import org.djunits.unit.LengthUnit;
import org.djunits.unit.SpeedUnit;
import org.djunits.unit.TimeUnit;
import org.djunits.value.vdouble.scalar.DoubleScalar;
import org.opentrafficsim.core.dsol.OTSDEVSSimulatorInterface;
import org.opentrafficsim.core.gtu.GTUType;
import org.opentrafficsim.core.gtu.animation.GTUColorer;
import org.opentrafficsim.core.gtu.following.GTUFollowingModel;
import org.opentrafficsim.core.gtu.lane.changing.LaneChangeModel;
import org.opentrafficsim.core.network.lane.Lane;
import org.opentrafficsim.core.network.route.LaneBasedRouteGenerator;
import org.opentrafficsim.units.distributions.DistContinuousDoubleScalar;

/**
 * Generate GTUs.
 * <p>
 * Copyright (c) 2013-2015 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved. <br>
 * BSD-style license. See <a href="http://opentrafficsim.org/docs/license.html">OpenTrafficSim License</a>.
 * <p>
 * @version $Revision$, $LastChangedDate$, by $Author$,
 *          initial version Feb 2, 2015 <br>
 * @author <a href="http://www.tbm.tudelft.nl/averbraeck">Alexander Verbraeck</a>
 * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
 */
public class GTUGeneratorIndividual extends AbstractGTUGenerator
{
    /** simulator to schedule next arrival events. */
    private final OTSDEVSSimulatorInterface simulator;

    /** distribution of the length of the GTU. */
    private final DistContinuousDoubleScalar.Rel<LengthUnit> lengthDist;

    /** distribution of the width of the GTU. */
    private final DistContinuousDoubleScalar.Rel<LengthUnit> widthDist;

    /** distribution of the maximum speed of the GTU. */
    private final DistContinuousDoubleScalar.Abs<SpeedUnit> maximumSpeedDist;

    /**
     * @param name the name of the generator
     * @param gtuType the type of GTU to generate
     * @param gtuClass the gtu class to instantiate
     * @param gtuFollowingModel the GTU following model to use
     * @param laneChangeModel the lane change model to use
     * @param initialSpeedDist distribution of the initial speed of the GTU
     * @param interarrivelTimeDist distribution of the interarrival time
     * @param maxGTUs maximum number of GTUs to generate
     * @param startTime start time of generation (delayed start)
     * @param endTime end time of generation
     * @param simulator simulator to schedule next arrival events
     * @param lengthDist distribution of the length of the GTU
     * @param widthDist distribution of the width of the GTU
     * @param maximumSpeedDist distribution of the maximum speed of the GTU
     * @param lane Lane on which newly GTUs are placed
     * @param position position on the lane, relative to the design line of the link
     * @param routeGenerator RouteGenerator; the route generator that will create a route for each newly constructed GTU
     * @param gtuColorer the GTUColorer to use
     * @throws SimRuntimeException when simulation scheduling fails
     * @throws RemoteException when remote simulator cannot be reached
     */
    @SuppressWarnings("checkstyle:parameternumber")
    public GTUGeneratorIndividual(final String name, final OTSDEVSSimulatorInterface simulator, final GTUType gtuType,
        final Class<?> gtuClass, final GTUFollowingModel gtuFollowingModel, final LaneChangeModel laneChangeModel,
        final DistContinuousDoubleScalar.Abs<SpeedUnit> initialSpeedDist,
        final DistContinuousDoubleScalar.Rel<TimeUnit> interarrivelTimeDist,
        final DistContinuousDoubleScalar.Rel<LengthUnit> lengthDist,
        final DistContinuousDoubleScalar.Rel<LengthUnit> widthDist,
        final DistContinuousDoubleScalar.Abs<SpeedUnit> maximumSpeedDist, final long maxGTUs,
        final DoubleScalar.Abs<TimeUnit> startTime, final DoubleScalar.Abs<TimeUnit> endTime, final Lane lane,
        final DoubleScalar.Rel<LengthUnit> position, final LaneBasedRouteGenerator routeGenerator,
        final GTUColorer gtuColorer) throws RemoteException, SimRuntimeException
    {
        super(name, simulator, gtuType, gtuClass, gtuFollowingModel, laneChangeModel, initialSpeedDist,
            interarrivelTimeDist, maxGTUs, startTime, endTime, lane, position, routeGenerator, gtuColorer);
        this.simulator = simulator;
        this.lengthDist = lengthDist;
        this.widthDist = widthDist;
        this.maximumSpeedDist = maximumSpeedDist;
    }

    /** {@inheritDoc} */
    @Override
    public final OTSDEVSSimulatorInterface getSimulator()
    {
        return this.simulator;
    }

    /**
     * @return lengthDist.
     */
    public final DistContinuousDoubleScalar.Rel<LengthUnit> getLengthDist()
    {
        return this.lengthDist;
    }

    /**
     * @return widthDist.
     */
    public final DistContinuousDoubleScalar.Rel<LengthUnit> getWidthDist()
    {
        return this.widthDist;
    }

    /**
     * @return maximumSpeedDist.
     */
    public final DistContinuousDoubleScalar.Abs<SpeedUnit> getMaximumSpeedDist()
    {
        return this.maximumSpeedDist;
    }

}
