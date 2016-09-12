package org.opentrafficsim.imb.simulators;

import org.opentrafficsim.core.dsol.OTSDEVSRealTimeClock;
import org.opentrafficsim.core.dsol.OTSDEVSSimulatorInterface;
import org.opentrafficsim.imb.IMBException;
import org.opentrafficsim.imb.transceiver.AbstractTransceiver;
import org.opentrafficsim.imb.transceiver.Connector;
import org.opentrafficsim.imb.transceiver.OTSToIMBTransformer;

import nl.tudelft.simulation.dsol.simulators.SimulatorInterface;
import nl.tudelft.simulation.event.EventInterface;

/**
 * The SimulatorTransceiver publishes the following information on the IMB bus:
 * <ol>
 * <li>SimulatorInterface.START_EVENT when the simulator starts executing.</li>
 * <li>SimulatorInterface.STOP_EVENT when the simulator stops executing.</li>
 * <li>SimulatorInterface.START_EVENT when the simulator starts executing.</li>
 * </ol>
 * GTUs are identified by their gtuId.
 * <p>
 * Copyright (c) 2013-2016 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved. <br>
 * BSD-style license. See <a href="http://opentrafficsim.org/docs/license.html">OpenTrafficSim License</a>.
 * </p>
 * $LastChangedDate: 2015-07-24 02:58:59 +0200 (Fri, 24 Jul 2015) $, @version $Revision: 1147 $, by $Author: averbraeck $,
 * initial version Sep 11, 2016 <br>
 * @author <a href="http://www.tbm.tudelft.nl/averbraeck">Alexander Verbraeck</a>
 * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
 * @author <a href="http://www.transport.citg.tudelft.nl">Wouter Schakel</a>
 */
public class SimulatorTransceiver extends AbstractTransceiver
{
    /** */
    private static final long serialVersionUID = 20160911L;

    /** the Empty transformer for IMB. */
    private final EmptyTransformer emptyTransformer = new EmptyTransformer();

    /** the simulation speed transformer for IMB. */
    private final SpeedTransformer speedTransformer = new SpeedTransformer();

    /**
     * Construct a new GTUTransceiver.
     * @param connector Connector; the IMB connector through which this transceiver communicates
     * @param simulator OTSDEVSSimulatorInterface; the simulator to schedule the incoming notifications on
     * @throws IMBException when the registration of one of the channels fails
     * @throws NullPointerException in case one of the arguments is null.
     */
    public SimulatorTransceiver(final Connector connector, final OTSDEVSSimulatorInterface simulator) throws IMBException
    {
        super("Simulator Control", connector, simulator);

        // register the OTS to IMB updates for the simulator
        OTSDEVSRealTimeClock animator = (OTSDEVSRealTimeClock) simulator;
        addOTSToIMBChannel(animator, SimulatorInterface.START_EVENT, "Sim_Start", new Object[] {}, this.emptyTransformer);
        addOTSToIMBChannel(animator, SimulatorInterface.STOP_EVENT, "Sim_Stop", new Object[] {}, this.emptyTransformer);
        addOTSToIMBChannel(animator, SimpleIMBAnimator.CHANGE_SPEED_FACTOR_EVENT, "Sim_Speed",
                new Object[] { new Float(1.0) }, this.speedTransformer);
        
        
    }

    /**
     * Transform the an event without content to a corresponding IMB message.
     * <p>
     * Copyright (c) 2013-2016 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved.
     * <br>
     * BSD-style license. See <a href="http://opentrafficsim.org/docs/license.html">OpenTrafficSim License</a>.
     * </p>
     * $LastChangedDate: 2015-07-24 02:58:59 +0200 (Fri, 24 Jul 2015) $, @version $Revision: 1147 $, by $Author: averbraeck $,
     * initial version Sep 11, 2016 <br>
     * @author <a href="http://www.tbm.tudelft.nl/averbraeck">Alexander Verbraeck</a>
     * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
     * @author <a href="http://www.transport.citg.tudelft.nl">Wouter Schakel</a>
     */
    static class EmptyTransformer implements OTSToIMBTransformer
    {
        /** {@inheritDoc} */
        @Override
        public Object[] transform(final EventInterface event)
        {
            return new Object[] {};
        }
    }

    /**
     * Transform the a simulation speed change event to a corresponding IMB message.
     * <p>
     * Copyright (c) 2013-2016 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved.
     * <br>
     * BSD-style license. See <a href="http://opentrafficsim.org/docs/license.html">OpenTrafficSim License</a>.
     * </p>
     * $LastChangedDate: 2015-07-24 02:58:59 +0200 (Fri, 24 Jul 2015) $, @version $Revision: 1147 $, by $Author: averbraeck $,
     * initial version Sep 11, 2016 <br>
     * @author <a href="http://www.tbm.tudelft.nl/averbraeck">Alexander Verbraeck</a>
     * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
     * @author <a href="http://www.transport.citg.tudelft.nl">Wouter Schakel</a>
     */
    static class SpeedTransformer implements OTSToIMBTransformer
    {
        /** {@inheritDoc} */
        @Override
        public Object[] transform(final EventInterface event)
        {
            float speed = ((Double) event.getContent()).floatValue();
            return new Object[] { speed };
        }
    }
}
