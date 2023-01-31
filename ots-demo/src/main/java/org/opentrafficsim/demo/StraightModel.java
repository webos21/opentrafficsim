package org.opentrafficsim.demo;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.djunits.unit.DurationUnit;
import org.djunits.unit.LengthUnit;
import org.djunits.unit.SpeedUnit;
import org.djunits.unit.TimeUnit;
import org.djunits.unit.util.UNITS;
import org.djunits.value.vdouble.scalar.Direction;
import org.djunits.value.vdouble.scalar.Duration;
import org.djunits.value.vdouble.scalar.Frequency;
import org.djunits.value.vdouble.scalar.Length;
import org.djunits.value.vdouble.scalar.Speed;
import org.djunits.value.vdouble.scalar.Time;
import org.opentrafficsim.base.parameters.ParameterException;
import org.opentrafficsim.base.parameters.ParameterSet;
import org.opentrafficsim.core.compatibility.Compatible;
import org.opentrafficsim.core.definitions.DefaultsNl;
import org.opentrafficsim.core.distributions.ConstantGenerator;
import org.opentrafficsim.core.distributions.Distribution;
import org.opentrafficsim.core.distributions.Distribution.FrequencyAndObject;
import org.opentrafficsim.core.distributions.Generator;
import org.opentrafficsim.core.distributions.ProbabilityException;
import org.opentrafficsim.core.dsol.AbstractOtsModel;
import org.opentrafficsim.core.dsol.OtsSimulatorInterface;
import org.opentrafficsim.core.geometry.OtsGeometryException;
import org.opentrafficsim.core.geometry.OtsPoint3D;
import org.opentrafficsim.core.gtu.GtuException;
import org.opentrafficsim.core.gtu.GtuType;
import org.opentrafficsim.core.idgenerator.IdGenerator;
import org.opentrafficsim.core.network.NetworkException;
import org.opentrafficsim.core.network.route.FixedRouteGenerator;
import org.opentrafficsim.core.network.route.Route;
import org.opentrafficsim.core.units.distributions.ContinuousDistDoubleScalar;
import org.opentrafficsim.road.definitions.DefaultsRoadNl;
import org.opentrafficsim.road.gtu.generator.GeneratorPositions;
import org.opentrafficsim.road.gtu.generator.LaneBasedGtuGenerator;
import org.opentrafficsim.road.gtu.generator.TtcRoomChecker;
import org.opentrafficsim.road.gtu.generator.characteristics.LaneBasedTemplateGtuType;
import org.opentrafficsim.road.gtu.generator.characteristics.LaneBasedTemplateGtuTypeDistribution;
import org.opentrafficsim.road.gtu.lane.tactical.following.AbstractIdm;
import org.opentrafficsim.road.gtu.lane.tactical.following.IdmPlusFactory;
import org.opentrafficsim.road.gtu.lane.tactical.lmrs.DefaultLmrsPerceptionFactory;
import org.opentrafficsim.road.gtu.lane.tactical.lmrs.LmrsFactory;
import org.opentrafficsim.road.gtu.strategical.LaneBasedStrategicalPlanner;
import org.opentrafficsim.road.gtu.strategical.LaneBasedStrategicalPlannerFactory;
import org.opentrafficsim.road.gtu.strategical.route.LaneBasedStrategicalRoutePlannerFactory;
import org.opentrafficsim.road.network.OtsRoadNetwork;
import org.opentrafficsim.road.network.factory.LaneFactory;
import org.opentrafficsim.road.network.lane.CrossSectionLink;
import org.opentrafficsim.road.network.lane.Lane;
import org.opentrafficsim.road.network.lane.LanePosition;
import org.opentrafficsim.road.network.lane.LaneType;
import org.opentrafficsim.road.network.lane.OtsRoadNode;
import org.opentrafficsim.road.network.lane.object.detector.SinkDetector;
import org.opentrafficsim.road.network.lane.object.trafficlight.SimpleTrafficLight;
import org.opentrafficsim.road.network.lane.object.trafficlight.TrafficLightColor;

import nl.tudelft.simulation.dsol.SimRuntimeException;
import nl.tudelft.simulation.dsol.model.inputparameters.InputParameterException;
import nl.tudelft.simulation.jstats.distributions.DistUniform;
import nl.tudelft.simulation.jstats.streams.MersenneTwister;
import nl.tudelft.simulation.jstats.streams.StreamInterface;

/**
 * Simulate a single lane road of 5 km length. Vehicles are generated at a constant rate of 1500 veh/hour. At time 300s a
 * blockade is inserted at position 4 km; this blockade is removed at time 500s. The used car following algorithm is IDM+
 * <a href="http://opentrafficsim.org/downloads/MOTUS%20reference.pdf"><i>Integrated Lane Change Model with Relaxation and
 * Synchronization</i>, by Wouter J. Schakel, Victor L. Knoop and Bart van Arem, 2012</a>. <br>
 * Output is a set of block charts:
 * <ul>
 * <li>Traffic density</li>
 * <li>Speed</li>
 * <li>Flow</li>
 * <li>Acceleration</li>
 * </ul>
 * All these graphs display simulation time along the horizontal axis and distance along the road along the vertical axis.
 * <p>
 * Copyright (c) 2013-2022 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved. <br>
 * BSD-style license. See <a href="https://opentrafficsim.org/docs/license.html">OpenTrafficSim License</a>.
 * <p>
 * @author <a href="https://tudelft.nl/staff/p.knoppers-1">Peter Knoppers</a>
 */
public class StraightModel extends AbstractOtsModel implements UNITS
{
    /** */
    private static final long serialVersionUID = 20140815L;

    /** The network. */
    private final OtsRoadNetwork network = new OtsRoadNetwork("network", getSimulator());

    /** The probability that the next generated GTU is a passenger car. */
    private double carProbability;

    /** The blocking, implemented as a traffic light. */
    private SimpleTrafficLight block = null;

    /** Maximum distance. */
    private Length maximumDistance = new Length(5000, METER);

    /** The Lane that contains the simulated Cars. */
    private Lane lane;

    /** The random number generator used to decide what kind of GTU to generate. */
    private StreamInterface stream = new MersenneTwister(12345);

    /** The sequence of Lanes that all vehicles will follow. */
    private List<Lane> path = new ArrayList<>();

    /** The speed limit on all Lanes. */
    private Speed speedLimit = new Speed(120, KM_PER_HOUR);

    /**
     * @param simulator OTSSimulatorInterface; the simulator for this model
     */
    public StraightModel(final OtsSimulatorInterface simulator)
    {
        super(simulator);
        InputParameterHelper.makeInputParameterMapCarTruck(this.inputParameterMap, 1.0);
    }

    /** {@inheritDoc} */
    @Override
    public final void constructModel() throws SimRuntimeException
    {
        try
        {
            OtsRoadNode from = new OtsRoadNode(this.network, "From", new OtsPoint3D(0.0, 0, 0), Direction.ZERO);
            OtsRoadNode to =
                    new OtsRoadNode(this.network, "To", new OtsPoint3D(this.maximumDistance.getSI(), 0, 0), Direction.ZERO);
            OtsRoadNode end = new OtsRoadNode(this.network, "End", new OtsPoint3D(this.maximumDistance.getSI() + 50.0, 0, 0),
                    Direction.ZERO);
            LaneType laneType = DefaultsRoadNl.TWO_WAY_LANE;
            this.lane = LaneFactory.makeLane(this.network, "Lane", from, to, null, laneType, this.speedLimit, this.simulator,
                    DefaultsNl.VEHICLE);
            this.path.add(this.lane);
            CrossSectionLink endLink = LaneFactory.makeLink(this.network, "endLink", to, end, null, this.simulator);
            // No overtaking, single lane
            Lane sinkLane = new Lane(endLink, "sinkLane", this.lane.getLateralCenterPosition(1.0),
                    this.lane.getLateralCenterPosition(1.0), this.lane.getWidth(1.0), this.lane.getWidth(1.0), laneType,
                    Map.of(DefaultsNl.VEHICLE, this.speedLimit), false);
            new SinkDetector(sinkLane, new Length(10.0, METER), Compatible.EVERYTHING, this.simulator);
            this.path.add(sinkLane);

            this.carProbability = (double) getInputParameter("generic.carProbability");

            // Generation of a new car / truck
            TtcRoomChecker roomChecker = new TtcRoomChecker(new Duration(10.0, DurationUnit.SI));
            IdGenerator idGenerator = new IdGenerator("");
            ParameterSet params = new ParameterSet();
            params.setDefaultParameter(AbstractIdm.DELTA);
            GtuType car = DefaultsNl.CAR;
            GtuType truck = DefaultsNl.TRUCK;
            ContinuousDistDoubleScalar.Rel<Speed, SpeedUnit> speedCar =
                    new ContinuousDistDoubleScalar.Rel<>(new DistUniform(this.stream, 90.0, 110.0), SpeedUnit.KM_PER_HOUR);
            ContinuousDistDoubleScalar.Rel<Speed, SpeedUnit> speedTruck =
                    new ContinuousDistDoubleScalar.Rel<>(new DistUniform(this.stream, 80, 95), SpeedUnit.KM_PER_HOUR);
            Generator<Route> routeGenerator = new FixedRouteGenerator(null);
            LaneBasedStrategicalPlannerFactory<LaneBasedStrategicalPlanner> strategicalPlannerFactoryCars =
                    new LaneBasedStrategicalRoutePlannerFactory(
                            new LmrsFactory(new IdmPlusFactory(this.stream), new DefaultLmrsPerceptionFactory()));
            LaneBasedStrategicalPlannerFactory<LaneBasedStrategicalPlanner> strategicalPlannerFctoryTrucks =
                    new LaneBasedStrategicalRoutePlannerFactory(
                            new LmrsFactory(new IdmPlusFactory(this.stream), new DefaultLmrsPerceptionFactory()));
            LaneBasedTemplateGtuType carTemplate = new LaneBasedTemplateGtuType(car,
                    new ConstantGenerator<>(Length.instantiateSI(4.0)), new ConstantGenerator<>(Length.instantiateSI(2.0)),
                    speedCar, strategicalPlannerFactoryCars, routeGenerator);
            LaneBasedTemplateGtuType truckTemplate = new LaneBasedTemplateGtuType(truck,
                    new ConstantGenerator<>(Length.instantiateSI(15.0)), new ConstantGenerator<>(Length.instantiateSI(2.5)),
                    speedTruck, strategicalPlannerFctoryTrucks, routeGenerator);
            Distribution<LaneBasedTemplateGtuType> gtuTypeDistribution = new Distribution<>(this.stream);
            gtuTypeDistribution.add(new FrequencyAndObject<>(this.carProbability, carTemplate));
            gtuTypeDistribution.add(new FrequencyAndObject<>(1.0 - this.carProbability, truckTemplate));
            Generator<Duration> headwayGenerator = new HeadwayGenerator(new Frequency(1500.0, PER_HOUR));
            Set<LanePosition> initialLongitudinalPositions = new LinkedHashSet<>();
            initialLongitudinalPositions.add(new LanePosition(this.lane, new Length(5.0, LengthUnit.SI)));
            LaneBasedTemplateGtuTypeDistribution characteristicsGenerator =
                    new LaneBasedTemplateGtuTypeDistribution(gtuTypeDistribution);
            new LaneBasedGtuGenerator("Generator", headwayGenerator, characteristicsGenerator,
                    GeneratorPositions.create(initialLongitudinalPositions, this.stream), this.network, getSimulator(),
                    roomChecker, idGenerator);
            // End generation

            this.block = new SimpleTrafficLight(this.lane.getId() + "_TL", this.lane,
                    new Length(new Length(4000.0, LengthUnit.METER)), this.simulator);
            this.block.setTrafficLightColor(TrafficLightColor.GREEN);
            // Create a block at t = 5 minutes
            this.simulator.scheduleEventAbsTime(new Time(300, TimeUnit.BASE_SECOND), this, "createBlock", null);
            // Remove the block at t = 7 minutes
            this.simulator.scheduleEventAbsTime(new Time(420, TimeUnit.BASE_SECOND), this, "removeBlock", null);
        }
        catch (SimRuntimeException | NetworkException | OtsGeometryException | InputParameterException | GtuException
                | ParameterException | ProbabilityException exception)
        {
            exception.printStackTrace();
        }
    }

    /**
     * Set up the block.
     */
    protected final void createBlock()
    {
        this.block.setTrafficLightColor(TrafficLightColor.RED);
    }

    /**
     * Remove the block.
     */
    protected final void removeBlock()
    {
        this.block.setTrafficLightColor(TrafficLightColor.GREEN);
    }

    /** {@inheritDoc} */
    @Override
    public OtsRoadNetwork getNetwork()
    {
        return this.network;
    }

    /**
     * @return the path for sampling the graphs
     */
    public final List<Lane> getPath()
    {
        return this.path;
    }

    /**
     * <p>
     * Copyright (c) 2013-2022 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved.
     * <br>
     * BSD-style license. See <a href="https://opentrafficsim.org/docs/license.html">OpenTrafficSim License</a>.
     * <p>
     * @author <a href="https://github.com/averbraeck">Alexander Verbraeck</a>
     * @author <a href="https://tudelft.nl/staff/p.knoppers-1">Peter Knoppers</a>
     * @author <a href="https://dittlab.tudelft.nl">Wouter Schakel</a>
     */
    private static class HeadwayGenerator implements Generator<Duration>
    {
        /** Demand level. */
        private final Frequency demand;

        /** a random stream. */
        private StreamInterface stream = new MersenneTwister(4L);

        /**
         * @param demand Frequency; demand
         */
        HeadwayGenerator(final Frequency demand)
        {
            this.demand = demand;
        }

        /** {@inheritDoc} */
        @Override
        public Duration draw() throws ProbabilityException, ParameterException
        {
            return new Duration(-Math.log(this.stream.nextDouble()) / this.demand.si, DurationUnit.SI);
        }

    }

}
