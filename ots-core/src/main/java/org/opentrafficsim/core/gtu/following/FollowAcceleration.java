package org.opentrafficsim.core.gtu.following;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.opentrafficsim.core.gtu.RelativePosition;
import org.opentrafficsim.core.gtu.lane.LaneBasedGTU;
import org.opentrafficsim.core.network.NetworkException;
import org.opentrafficsim.core.network.lane.CrossSectionLink;
import org.opentrafficsim.core.network.lane.Lane;
import org.opentrafficsim.core.unit.AccelerationUnit;
import org.opentrafficsim.core.unit.LengthUnit;
import org.opentrafficsim.core.unit.SpeedUnit;
import org.opentrafficsim.core.unit.TimeUnit;
import org.opentrafficsim.core.value.ValueException;
import org.opentrafficsim.core.value.vdouble.scalar.DoubleScalar;
import org.opentrafficsim.core.value.vdouble.scalar.MutableDoubleScalar;
import org.opentrafficsim.core.value.vdouble.vector.DoubleVector;

/**
 * Determine acceleration (deceleration) for a GTU that follows another GTU.
 * <p>
 * Copyright (c) 2013-2014 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved. <br>
 * BSD-style license. See <a href="http://opentrafficsim.org/node/13">OpenTrafficSim License</a>.
 * <p>
 * @version Sep 19, 2014 <br>
 * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
 */
public final class FollowAcceleration
{
    /**
     * Return a prohibitive negative value.
     * @return DoubleVector.Abs.Dense&lt;AccelerationUnit&gt;
     * @throws ValueException when something that cannot happen happens
     */
    private static DoubleVector.Abs.Dense<AccelerationUnit> tooDangerous() throws ValueException
    {
        return new DoubleVector.Abs.Dense<AccelerationUnit>(
            new double[] {Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY}, AccelerationUnit.METER_PER_SECOND_2);
    }

    /**
     * This class should never be instantiated.
     */
    private FollowAcceleration()
    {
        // This class should never be instantiated
    }

    /**
     * Compute the acceleration (or deceleration) for a GTU following another GTU.
     * @param follower LaneBaseGTU; the GTU that is following
     * @param leader LaneBaseGTU; the GTU that is leading
     * @param when DoubleScalar.Abs&lt;TimeUnit&gt;; the current time
     * @param gtuFollowingModel GTUFollowingModel; the GTU following model that is used to compute the result
     * @param speedLimit DoubleScalar.Abs&lt;SpeedUnit&gt;; the speed limit
     * @return DoubleScalar.Abs&lt;AccelerationUnit&gt;; the acceleration (deceleration) for the following GTU in order to not
     *         collide with the leader GTU
     * @throws RemoteException on communication failure
     * @throws NetworkException if follower and leader do not have a common CrossSectionLink
     */
    public static DoubleScalar.Abs<AccelerationUnit> acceleration(final LaneBasedGTU<?> follower,
        final LaneBasedGTU<?> leader, final DoubleScalar.Abs<TimeUnit> when,
        final GTUFollowingModel gtuFollowingModel, final DoubleScalar.Abs<SpeedUnit> speedLimit) throws RemoteException,
        NetworkException
    {
        if (null != leader && null != follower)
        {
            // find a lane where follower and leader are jointly
            // OOOPS DONT DO IT THIS WAY: Set<Lane> lanes = leader.getLongitudinalPositions().keySet();
            // Create a NEW Set (so we don't modify the HashSet of the leader).
            Set<Lane> lanes = new HashSet<Lane>(leader.positions(leader.getFront()).keySet());
            lanes.retainAll(follower.positions(follower.getFront()).keySet());
            // TODO expand to lanes for next links as well, to a certain distance (which is...?)
            if (lanes.size() > 0)
            {
                Lane lane = lanes.iterator().next(); // Use the first one
                try
                {
                    MutableDoubleScalar.Rel<LengthUnit> headway =
                        DoubleScalar.minus(leader.position(lane, leader.getRear(), when), 
                            follower.position(lane, follower.getFront(), when));
                    if (headway.getSI() <= 0)
                    {
                        // Immediate collision; return a prohibitive negative value
                        return new DoubleScalar.Abs<AccelerationUnit>(Double.NEGATIVE_INFINITY,
                            AccelerationUnit.METER_PER_SECOND_2);
                    }
                }
                catch (NetworkException ne)
                {
                    throw new Error("Cannot happen -- both vehicles are on these lanes");
                }
            }
            else
            {
                // Not on the same lane. Find a Link that is shared by both
                Set<CrossSectionLink<?, ?>> links = new HashSet<CrossSectionLink<?, ?>>();
                for (Lane lane : leader.positions(leader.getFront()).keySet())
                {
                    links.add(lane.getParentLink());
                }
                if (links.size() == 0)
                {
                    throw new NetworkException("Leader is not on any link");
                }
                Set<CrossSectionLink<?, ?>> followerLinks = new HashSet<CrossSectionLink<?, ?>>();
                for (Lane lane : follower.positions(follower.getFront()).keySet())
                {
                    followerLinks.add(lane.getParentLink());
                }
                links.retainAll(followerLinks);
                if (links.size() == 0)
                {
                    throw new NetworkException("Leader and follower do not have a common CrossSectionLink");
                }
                CrossSectionLink<?, ?> commonLink = links.iterator().next(); // Use the first one
                Lane leaderLane = null;
                for (Lane lane : leader.positions(leader.getFront()).keySet())
                {
                    if (lane.getParentLink() == commonLink)
                    {
                        leaderLane = lane;
                        break;
                    }
                }
                if (null == leaderLane)
                {
                    throw new Error("Cannot happen -- Cannot find leaderLane");
                }
                Lane followerLane = null;
                for (Lane lane : follower.positions(RelativePosition.REFERENCE_POSITION).keySet())
                {
                    if (lane.getParentLink() == commonLink)
                    {
                        followerLane = lane;
                        break;
                    }
                }
                if (null == followerLane)
                {
                    throw new Error("Cannot happen -- Cannot find leaderLane");
                }
                // Get the difference of the projections of both lanes onto the design line of the link
                // leader.positionOfRear(leaderLane, when);
                double leaderRatio = leader.fractionalPosition(leaderLane, leader.getRear(), when);
                double followerRatio = follower.fractionalPosition(followerLane, follower.getFront(), when);
                double ratioDifference = leaderRatio - followerRatio; // TODO prove that this is always correct
                // Scale that difference by the length of the lane of the follower to obtain the headway
                DoubleScalar.Rel<LengthUnit> headway =
                        new DoubleScalar.Rel<LengthUnit>(followerLane.getLength().getSI() * ratioDifference,
                                LengthUnit.METER);
                return gtuFollowingModel.computeAcceleration(follower, leader.getLongitudinalVelocity(when), headway,
                    speedLimit).getAcceleration();
            }
        }
        return gtuFollowingModel.computeAcceleration(follower, leader, speedLimit).getAcceleration();
    }

    /**
     * Compute the acceleration of this car and the (new) follower car in the current lane, or after a considered lane change.
     * Both values are returned; the acceleration for the reference car at index 0, the acceleration of the (new) follower car
     * at index 1. If changing lane is not possible because it would result in dangerous deceleration or collision, the returned
     * value in both fields is Double.NEGATIVE_INFINITY.
     * @param referenceGTU AbstractLaneBasedGTU&lt;?&gt;; the car that considers changing lane and whose gtuFollowingModel will
     *            be used
     * @param otherCars Set&lt;Car&gt;; the cars in the target lane
     * @param speedLimit DoubleScalar.Abs&lt;SpeedUnit&gt;; the speed limit
     * @return DoubleScalar.Abs&lt;AccelerationUnit&gt;; the lowest acceleration (highest deceleration) incurred if the lane
     *         change is carried out
     * @throws RemoteException on communication failure
     * @throws NetworkException when the network is inconsistent
     */
    @SuppressWarnings("unchecked")
    public static DoubleVector.Abs.Dense<AccelerationUnit> acceleration(final LaneBasedGTU<?> referenceGTU,
        final Collection<LaneBasedGTU<?>> otherCars, final DoubleScalar.Abs<SpeedUnit> speedLimit)
        throws RemoteException, NetworkException
    {
        final DoubleScalar.Abs<AccelerationUnit> maximumDeceleration =
            referenceGTU.getGTUFollowingModel().maximumSafeDeceleration();
        DoubleScalar.Abs<TimeUnit> when = referenceGTU.getSimulator().getSimulatorTime().get();
        LaneBasedGTU<?> leader = null;
        DoubleScalar.Rel<LengthUnit> leaderHeadway = null;
        LaneBasedGTU<?> follower = null;
        DoubleScalar.Rel<LengthUnit> followerHeadway = null;
        Lane referenceLane = referenceGTU.positions(referenceGTU.getFront()).keySet().iterator().next();
        DoubleScalar.Rel<LengthUnit> referenceCarPosition =
            referenceGTU.position(referenceLane, referenceGTU.getFront(), when);
        if (otherCars.size() > 0)
        {
            LaneBasedGTU<?> otherCar = otherCars.iterator().next();
            Lane otherLane = otherCar.positions(otherCar.getFront()).keySet().iterator().next();
            if (otherLane != referenceLane)
            {
                // Project the referenceCarPosition on the lane of the otherCars
                referenceCarPosition =
                    new DoubleScalar.Rel<LengthUnit>(referenceCarPosition.getSI() / referenceLane.getLength().getSI()
                        * otherLane.getLength().getSI(), LengthUnit.METER);
            }
        }
        // Find the nearest leader and the nearest follower.
        for (LaneBasedGTU<?> c : otherCars)
        {
            if (c == referenceGTU)
            {
                continue;
            }
            Lane otherLane = c.positions(c.getFront()).keySet().iterator().next();
            DoubleScalar.Rel<LengthUnit> headway =
                DoubleScalar.minus(c.position(otherLane, c.getFront(), when), referenceCarPosition).immutable();
            if (headway.getSI() < 0)
            {
                if (null == follower || followerHeadway.getSI() < headway.getSI())
                {
                    follower = c;
                    followerHeadway = headway;
                }
            }
            else
            {
                if (null == leader || leaderHeadway.getSI() > headway.getSI())
                {
                    leader = c;
                    leaderHeadway = headway;
                }
            }
        }
        // System.out.println("leader is " + (null == leader ? "NULL" : leader));
        try
        {
            if (null != followerHeadway && DoubleScalar.plus(referenceGTU.getLength(), followerHeadway).getSI() >= 0)
            {
                // Immediate collision with follower
                return tooDangerous();
            }
            if (null != leader && DoubleScalar.minus(leaderHeadway, leader.getLength()).getSI() <= 0)
            {
                // Immediate collision with leader
                return tooDangerous();
            }
            GTUFollowingModel gtuFollowingModel = referenceGTU.getGTUFollowingModel();
            // System.out.println("referenceGTU: " + referenceGTU);
            DoubleScalar.Abs<AccelerationUnit> followerAcceleration =
                null == follower ? new DoubleScalar.Abs<AccelerationUnit>(0, AccelerationUnit.METER_PER_SECOND_2)
                    : FollowAcceleration.acceleration(follower, referenceGTU, when, gtuFollowingModel, speedLimit);
            if (followerAcceleration.getSI() >= -maximumDeceleration.getSI())
            {
                if (null != leaderHeadway && leaderHeadway.getSI() <= referenceGTU.getLength().getSI())
                {
                    return tooDangerous();
                }
                DoubleScalar.Abs<AccelerationUnit> referenceAcceleration =
                    FollowAcceleration.acceleration(referenceGTU, leader, when, gtuFollowingModel, speedLimit);
                if (referenceAcceleration.getSI() >= -maximumDeceleration.getSI())
                {
                    return new DoubleVector.Abs.Dense<AccelerationUnit>(new DoubleScalar.Abs[] {referenceAcceleration,
                        followerAcceleration});
                }
            }
            // System.out.println("Imposed follower acceleration is dangerous: " + followerAcceleration);
            return tooDangerous();
        }
        catch (ValueException exception)
        {
            throw new Error("Cannot happen");
        }
    }

}
