package org.opentrafficsim.demo.conflict;

import static org.opentrafficsim.core.gtu.GTUType.VEHICLE;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.naming.NamingException;
import javax.swing.SwingUtilities;

import org.djunits.unit.DurationUnit;
import org.djunits.unit.LengthUnit;
import org.djunits.value.vdouble.scalar.Duration;
import org.djunits.value.vdouble.scalar.Length;
import org.djunits.value.vdouble.scalar.Time;
import org.opentrafficsim.base.modelproperties.Property;
import org.opentrafficsim.base.modelproperties.PropertyException;
import org.opentrafficsim.core.dsol.OTSDEVSSimulatorInterface;
import org.opentrafficsim.core.dsol.OTSModelInterface;
import org.opentrafficsim.core.dsol.OTSSimTimeDouble;
import org.opentrafficsim.core.gtu.animation.GTUColorer;
import org.opentrafficsim.core.network.OTSNetwork;
import org.opentrafficsim.road.animation.AnimationToggles;
import org.opentrafficsim.road.network.factory.xml.XmlNetworkLaneParser;
import org.opentrafficsim.road.network.lane.CrossSectionLink;
import org.opentrafficsim.road.network.lane.Lane;
import org.opentrafficsim.road.network.lane.conflict.ConflictBuilder;
import org.opentrafficsim.road.network.lane.object.trafficlight.SimpleTrafficLight;
import org.opentrafficsim.road.network.lane.object.trafficlight.TrafficLightColor;
import org.opentrafficsim.simulationengine.AbstractWrappableAnimation;
import org.opentrafficsim.simulationengine.OTSSimulationException;

import nl.tudelft.simulation.dsol.SimRuntimeException;
import nl.tudelft.simulation.dsol.simulators.SimulatorInterface;
import nl.tudelft.simulation.language.io.URLResource;

/**
 * <p>
 * Copyright (c) 2013-2017 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved. <br>
 * BSD-style license. See <a href="http://opentrafficsim.org/node/13">OpenTrafficSim License</a>.
 * <p>
 * @version $Revision$, $LastChangedDate$, by $Author$, initial version 11 dec. 2016 <br>
 * @author <a href="http://www.tbm.tudelft.nl/averbraeck">Alexander Verbraeck</a>
 * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
 * @author <a href="http://www.transport.citg.tudelft.nl">Wouter Schakel</a>
 */
public class TJunctionDemo extends AbstractWrappableAnimation
{

    /** */
    private static final long serialVersionUID = 20161211L;

    /** {@inheritDoc} */
    @Override
    protected final OTSModelInterface makeModel(final GTUColorer colorer) throws OTSSimulationException
    {
        return new TJunctionModel();
    }

    /** {@inheritDoc} */
    @Override
    protected final void addAnimationToggles()
    {
        AnimationToggles.setIconAnimationTogglesFull(this);
    }

    /** {@inheritDoc} */
    @Override
    public final String shortName()
    {
        return "T-junction demonstration";
    }

    /** {@inheritDoc} */
    @Override
    public final String description()
    {
        return "T-junction demonstration";
    }

    /**
     * The simulation model.
     */
    class TJunctionModel implements OTSModelInterface
    {

        /** */
        private static final long serialVersionUID = 20161211L;

        /** The network. */
        private OTSNetwork network;

        /** Simulator. */
        private OTSDEVSSimulatorInterface simulator;

        /** {@inheritDoc} */
        @Override
        public void constructModel(final SimulatorInterface<Time, Duration, OTSSimTimeDouble> arg0)
                throws SimRuntimeException, RemoteException
        {
            this.simulator = (OTSDEVSSimulatorInterface) arg0;
            try
            {
                URL url = URLResource.getResource("/conflict/TJunction.xml");
                XmlNetworkLaneParser nlp = new XmlNetworkLaneParser(this.simulator);
                this.network = nlp.build(url);

                // add conflicts
                // ((CrossSectionLink) this.network.getLink("SCEC")).setPriority(Priority.STOP);
                // ((CrossSectionLink) this.network.getLink("SCWC")).setPriority(Priority.STOP);
                ConflictBuilder.buildConflicts(this.network, VEHICLE, this.simulator,
                        new ConflictBuilder.FixedWidthGenerator(new Length(2.0, LengthUnit.SI)));

                // add trafficlight after
                Lane lane = ((CrossSectionLink) this.network.getLink("ECE")).getLanes().get(0);
                SimpleTrafficLight trafficLight =
                        new SimpleTrafficLight("light", lane, new Length(50.0, LengthUnit.SI), this.simulator);
                trafficLight.setTrafficLightColor(TrafficLightColor.RED);
                changePhase(trafficLight);

            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }

        /**
         * Changes color of traffic light.
         * @param trafficLight traffic light
         * @throws SimRuntimeException scheduling error
         */
        private void changePhase(final SimpleTrafficLight trafficLight) throws SimRuntimeException
        {
            switch (trafficLight.getTrafficLightColor())
            {
                case RED:
                {
                    trafficLight.setTrafficLightColor(TrafficLightColor.GREEN);
                    this.simulator.scheduleEventRel(new Duration(30.0, DurationUnit.SECOND), this, this, "changePhase",
                            new Object[] { trafficLight });
                    break;
                }
                case YELLOW:
                {
                    trafficLight.setTrafficLightColor(TrafficLightColor.RED);
                    this.simulator.scheduleEventRel(new Duration(56.0, DurationUnit.SECOND), this, this, "changePhase",
                            new Object[] { trafficLight });
                    break;
                }
                case GREEN:
                {
                    trafficLight.setTrafficLightColor(TrafficLightColor.YELLOW);
                    this.simulator.scheduleEventRel(new Duration(4.0, DurationUnit.SECOND), this, this, "changePhase",
                            new Object[] { trafficLight });
                    break;
                }
                default:
                {
                    //
                }
            }
        }

        /** {@inheritDoc} */
        @Override
        public SimulatorInterface<Time, Duration, OTSSimTimeDouble> getSimulator() throws RemoteException
        {
            return this.simulator;
        }

        /** {@inheritDoc} */
        @Override
        public OTSNetwork getNetwork()
        {
            return this.network;
        }

    }

    /**
     * Main program.
     * @param args String[]; the command line arguments (not used)
     * @throws SimRuntimeException should never happen
     */
    public static void main(final String[] args) throws SimRuntimeException
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    TJunctionDemo animation = new TJunctionDemo();
                    // 1 hour simulation run for testing
                    animation.buildAnimator(Time.ZERO, Duration.ZERO, new Duration(60.0, DurationUnit.MINUTE),
                            new ArrayList<Property<?>>(), null, true);
                }
                catch (SimRuntimeException | NamingException | OTSSimulationException | PropertyException exception)
                {
                    exception.printStackTrace();
                }
            }
        });
    }

}
