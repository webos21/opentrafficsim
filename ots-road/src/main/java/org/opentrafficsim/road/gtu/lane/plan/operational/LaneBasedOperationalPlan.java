package org.opentrafficsim.road.gtu.lane.plan.operational;

import java.util.List;

import org.djunits.value.vdouble.scalar.Direction;
import org.djunits.value.vdouble.scalar.Duration;
import org.djunits.value.vdouble.scalar.Length;
import org.djunits.value.vdouble.scalar.Speed;
import org.djunits.value.vdouble.scalar.Time;
import org.opentrafficsim.core.geometry.DirectedPoint;
import org.opentrafficsim.core.geometry.OtsGeometryException;
import org.opentrafficsim.core.geometry.OtsLine3D;
import org.opentrafficsim.core.geometry.OtsLine3D.FractionalFallback;
import org.opentrafficsim.core.geometry.OtsPoint3D;
import org.opentrafficsim.core.gtu.GtuException;
import org.opentrafficsim.core.gtu.plan.operational.OperationalPlan;
import org.opentrafficsim.core.gtu.plan.operational.OperationalPlanException;
import org.opentrafficsim.road.gtu.lane.LaneBasedGtu;
import org.opentrafficsim.road.network.lane.Lane;
import org.opentrafficsim.road.network.lane.LanePosition;

/**
 * An operational plan with some extra information about the lanes and lane changes so this information does not have to be
 * recalculated multiple times. Furthermore, it is quite expensive to check whether a lane change is part of the oprtational
 * plan based on geographical data.
 * <p>
 * Copyright (c) 2013-2022 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved. <br>
 * BSD-style license. See <a href="https://opentrafficsim.org/docs/license.html">OpenTrafficSim License</a>.
 * </p>
 * @author <a href="https://github.com/averbraeck">Alexander Verbraeck</a>
 * @author <a href="https://tudelft.nl/staff/p.knoppers-1">Peter Knoppers</a>
 */
public class LaneBasedOperationalPlan extends OperationalPlan
{
    /** */
    private static final long serialVersionUID = 20160120L;

    /** Deviative; meaning not along lane center lines. */
    private final boolean deviative;

    /**
     * Construct an operational plan with or without a lane change.
     * @param gtu LaneBasedGtu; the GTU for debugging purposes
     * @param path OTSLine3D; the path to follow from a certain time till a certain time. The path should have &lt;i&gt;at
     *            least&lt;/i&gt; the length
     * @param startTime Time; the absolute start time when we start executing the path
     * @param startSpeed Speed; the GTU speed when we start executing the path
     * @param operationalPlanSegmentList List&lt;Segment&gt;; the segments that make up the path with an acceleration, constant
     *            speed or deceleration profile
     * @param deviative boolean; whether the path is not along lane center lines
     * @throws OperationalPlanException when the path is too short for the operation
     */
    @SuppressWarnings("checkstyle:parameternumber")
    public LaneBasedOperationalPlan(final LaneBasedGtu gtu, final OtsLine3D path, final Time startTime, final Speed startSpeed,
            final List<Segment> operationalPlanSegmentList, final boolean deviative) throws OperationalPlanException
    {
        super(gtu, path, startTime, startSpeed, operationalPlanSegmentList);
        this.deviative = deviative;
    }

    /**
     * Build a plan where the GTU will wait for a certain time. Of course no lane change takes place.
     * @param gtu LaneBasedGtu; the GTU for debugging purposes
     * @param waitPoint DirectedPoint; the point at which the GTU will wait
     * @param startTime Time; the current time or a time in the future when the plan should start
     * @param duration Duration; the waiting time
     * @param deviative boolean; whether the path is not along lane center lines
     * @throws OperationalPlanException when construction of a waiting path fails
     */
    public LaneBasedOperationalPlan(final LaneBasedGtu gtu, final DirectedPoint waitPoint, final Time startTime,
            final Duration duration, final boolean deviative) throws OperationalPlanException
    {
        super(gtu, waitPoint, startTime, duration);
        this.deviative = deviative;
    }

    /**
     * Check if we deviate from the center line.
     * @return whether this maneuver involves deviation from the center line.
     */
    public final boolean isDeviative()
    {
        return this.deviative;
    }

    /**
     * Returns the total length along the reference lane that the GTU travels. In case of a deviative plan this involves
     * projection of the actual path to the lane center lines.
     * @param gtu LaneBasedGtu; GTU
     * @return Length; total length along the path
     * @throws GtuException if the GTU has not reference position
     */
    public final Length getTotalLengthAlongLane(final LaneBasedGtu gtu) throws GtuException
    {
        if (!this.deviative)
        {
            // along the lane center lines
            return getTotalLength();
        }

        // let's project the end position of the plan
        return getDistanceAlongLane(gtu, getEndLocation());
    }

    /**
     * Helper method to get rotation at start or end of lane.
     * @param lane LaneDirection; lane
     * @param start boolean; start (or end)
     * @return rotation at start or end of lane
     */
    private double getRotZAtFraction(final Lane lane, final boolean start)
    {
        double f = start ? 0.0 : 1.0;
        try
        {
            return lane.getCenterLine().getLocationFraction(f).getRotZ();
        }
        catch (OtsGeometryException exception)
        {
            // should not occur, we use 0.0 and 1.0
            throw new RuntimeException("Unexpected exception while assessing if a GTU is between lanes.", exception);
        }
    }

    /**
     * Returns the distance along the reference lane that the GTU travels from the current location up to the point.
     * @param gtu LaneBasedGtu; GTU
     * @param point DirectedPoint; point where the GTU is or will be
     * @return Length; total length along the path
     * @throws GtuException if the GTU has not reference position
     */
    public final Length getDistanceAlongLane(final LaneBasedGtu gtu, final DirectedPoint point) throws GtuException
    {

        // start lane center lines at current reference lane
        LanePosition pos = gtu.getReferencePosition();
        Lane lane = pos.getLane();

        // initialize loop data
        double length = -lane.coveredDistance(pos.getPosition().si / pos.getLane().getLength().si).si;
        double f = Double.NaN;
        Direction prevDir = Direction.instantiateSI(getRotZAtFraction(lane, true));

        // move to next lane while projection fails
        while (Double.isNaN(f))
        {
            Lane nextLane = gtu.getNextLaneForRoute(lane);
            Direction nextDir = Direction.instantiateSI(nextLane == null ? getRotZAtFraction(lane, false)
                    : .5 * getRotZAtFraction(lane, false) + .5 * getRotZAtFraction(nextLane, true));
            f = lane.getCenterLine().projectFractional(prevDir, nextDir, point.x, point.y, FractionalFallback.NaN);

            // check if the GTU is adjacent to the bit between the lanes (if there is such a bit)
            if (Double.isNaN(f))
            {
                if (nextLane == null)
                {
                    // projection error on dad-end lane, add the length of the lane
                    f = 1.0;
                    length += lane.coveredDistance(f).si;
                }
                else
                {
                    try
                    {
                        // compose gap line
                        OtsPoint3D last = lane.getCenterLine().getLast();
                        OtsPoint3D first = nextLane.getCenterLine().get(0);
                        if (!(last).equals(first))
                        {
                            OtsLine3D gap = new OtsLine3D(last, first);
                            double fGap = gap.projectFractional(null, null, point.x, point.y, FractionalFallback.NaN);
                            if (!Double.isNaN(fGap))
                            {
                                f = (lane.getLength().si + fGap * gap.getLengthSI()) / lane.getLength().si;
                            }
                            else
                            {
                                // gap, but no successful projection, use next lane in next loop, increase length so far
                                length += lane.getLength().si;
                                lane = nextLane;
                                prevDir = nextDir;
                            }
                        }
                        else
                        {
                            // no gap, use next lane in next loop, increase length so far
                            length += lane.getLength().si;
                            lane = nextLane;
                            prevDir = nextDir;
                        }
                    }
                    catch (OtsGeometryException exception)
                    {
                        // should not occur, we use get(0) and getLast()
                        throw new RuntimeException("Unexpected exception while assessing if a GTU is between lanes.",
                                exception);
                    }
                }
            }
            else
            {
                // projection is ok on lane, increase length so far
                length += lane.coveredDistance(f).si;
            }
        }
        // add length on lane where the reference position was projected to (or to it's consecutive gap between lanes)
        return Length.instantiateSI(length);
    }

    /** {@inheritDoc} */
    @Override
    public final String toString()
    {
        return "LaneBasedOperationalPlan [deviative=" + this.deviative + "]";
    }
}
