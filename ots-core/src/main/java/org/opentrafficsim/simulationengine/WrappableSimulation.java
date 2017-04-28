package org.opentrafficsim.simulationengine;

import java.util.ArrayList;

import javax.naming.NamingException;

import org.djunits.value.vdouble.scalar.Duration;
import org.djunits.value.vdouble.scalar.Time;
import org.opentrafficsim.base.modelproperties.AbstractProperty;
import org.opentrafficsim.base.modelproperties.PropertyException;
import org.opentrafficsim.core.network.NetworkException;

import nl.tudelft.simulation.dsol.SimRuntimeException;

/**
 * Requirements for demonstration that can be shown in the SuperDemo.
 * <p>
 * Copyright (c) 2013-2017 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved. <br>
 * BSD-style license. See <a href="http://opentrafficsim.org/docs/license.html">OpenTrafficSim License</a>.
 * <p>
 * $LastChangedDate$, @version $Revision$, by $Author$,
 * initial version 17 dec. 2014 <br>
 * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
 */
public interface WrappableSimulation
{
    /**
     * Build the simulation.
     * @param startTime Time; the start time of the simulation
     * @param warmupPeriod Duration; the warm up period of the simulation (use new Duration(0, SECOND) if you don't know what
     *            this is)
     * @param runLength Duration; the duration of the simulation
     * @param properties ArrayList&lt;AbstractProperty&lt;?&gt;&gt;; the (possibly user-modified) properties. This list must
     *            contain all the properties returned by getProperties(); any additional properties may be ignored
     * @return SimpleSimulation; the new simulation
     * @throws SimRuntimeException on ???
     * @throws NetworkException on Network inconsistency
     * @throws NamingException when context for the animation cannot be created
     * @throws OTSSimulationException when the construction of the simulation, the control panel, the animation, or the charts
     *             fails
     * @throws PropertyException when one of the user modified properties has the empty string as key
     */
    SimpleSimulatorInterface buildSimulator(Time startTime, Duration warmupPeriod, Duration runLength,
            ArrayList<AbstractProperty<?>> properties)
            throws SimRuntimeException, NetworkException, NamingException, OTSSimulationException, PropertyException;

    /**
     * Return a very short description of the simulation.
     * @return String; short description of the simulation
     */
    String shortName();

    /**
     * Return a description of the simulation (HTML formatted).
     * @return String; HTML text describing the simulation
     */
    String description();

    /**
     * Retrieve a list of visible properties of the simulation. <br>
     * The caller can modify the returned result. If the internal format is also an ArrayList it is highly recommended to make a
     * protective copy and return that.
     * @return ArrayList&lt;AbstractProperty&lt;?&gt;&gt;; the list of visible properties
     */
    ArrayList<AbstractProperty<?>> getProperties();
}
