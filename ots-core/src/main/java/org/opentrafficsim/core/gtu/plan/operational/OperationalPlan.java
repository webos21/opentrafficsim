package org.opentrafficsim.core.gtu.plan.operational;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import nl.tudelft.simulation.language.d3.DirectedPoint;

import org.djunits.unit.AccelerationUnit;
import org.djunits.unit.LengthUnit;
import org.djunits.unit.SpeedUnit;
import org.djunits.unit.TimeUnit;
import org.djunits.value.vdouble.scalar.Acceleration;
import org.djunits.value.vdouble.scalar.Length;
import org.djunits.value.vdouble.scalar.Speed;
import org.djunits.value.vdouble.scalar.Time;
import org.opentrafficsim.core.geometry.OTSLine3D;
import org.opentrafficsim.core.network.NetworkException;

/**
 * An OperationalPlan indicates a shape in the world with a speed profile that a GTU will use to move. The OperationalPlan can
 * be updated or replaced at any time (also before it has been totally executed), for which a tactical planner is responsible.
 * The operational plan is implemented using segments of the movement (time, location, speed, acceleration) that the GTU will
 * use to plan its location and movement.
 * <p>
 * Copyright (c) 2013-2015 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved. <br>
 * BSD-style license. See <a href="http://opentrafficsim.org/docs/license.html">OpenTrafficSim License</a>.
 * </p>
 * $LastChangedDate: 2015-07-24 02:58:59 +0200 (Fri, 24 Jul 2015) $, @version $Revision: 1147 $, by $Author: averbraeck $,
 * initial version Nov 14, 2015 <br>
 * @author <a href="http://www.tbm.tudelft.nl/averbraeck">Alexander Verbraeck</a>
 * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
 */
public class OperationalPlan implements Serializable
{
    /** */
    private static final long serialVersionUID = 20151114L;

    /** the path to follow from a certain time till a certain time. */
    private final OTSLine3D path;

    /** the absolute start time when we start executing the path. */
    private final Time.Abs startTime;

    /** the GTU speed when we start executing the path. */
    private final Speed startSpeed;

    /** the segments that make up the path with an acceleration, constant speed or deceleration profile. */
    private final List<OperationalPlan.Segment> operationalPlanSegmentList;

    /** the duration of executing the entire operational plan. */
    private final Time.Rel totalDuration;

    /** the speed at the end of the operational plan. */
    private final Speed endSpeed;

    /**
     * an array of relative start times of each segment, expressed in the SI unit, where the last time is the overall ending
     * time of the operational plan.
     */
    private final double[] segmentStartTimesSI;

    /**
     * the maximum difference in the length of the path and the calculated driven distance implied by the segment list. The same
     * constant is also used as a maximum between speeds of segments that should align in terms of speed.
     */
    static final double MAX_DELTA_SI = 1E-6;

    /** the drifting speed. Speeds under this value will be cropped to zero. */
    static final double DRIFTING_SPEED_SI = 1E-3;

    /** a constant for zero speed. */
    static final Speed SPEED_0 = new Speed(0.0, SpeedUnit.SI);

    // TODO add method for timeAtDistance
    
    /**
     * Construct an operational plan.
     * @param path the path to follow from a certain time till a certain time
     * @param startTime the absolute start time when we start executing the path
     * @param startSpeed the GTU speed when we start executing the path
     * @param operationalPlanSegmentList the segments that make up the path with an acceleration, constant speed or deceleration
     *            profile
     * @throws NetworkException when the length of the path and the calculated driven distance implied by the segment list
     *             differ more than a given threshold
     */
    public OperationalPlan(final OTSLine3D path, final Time.Abs startTime, final Speed startSpeed,
        final List<Segment> operationalPlanSegmentList) throws NetworkException
    {
        super();
        this.path = path;
        this.startTime = startTime;
        this.startSpeed = startSpeed;
        this.operationalPlanSegmentList = operationalPlanSegmentList;
        this.segmentStartTimesSI = new double[operationalPlanSegmentList.size() + 1];

        // check the driven distance of the segments
        Speed v0 = this.startSpeed;
        double distanceSI = 0.0;
        double durationSI = 0.0;
        for (int i = 0; i < this.operationalPlanSegmentList.size(); i++)
        {
            if (Math.abs(v0.si) < DRIFTING_SPEED_SI)
            {
                v0 = SPEED_0;
            }
            Segment segment = this.operationalPlanSegmentList.get(i);
            segment.setV0(v0);
            this.segmentStartTimesSI[i] = durationSI;
            distanceSI += segment.distanceSI();
            v0 = segment.endSpeed();
            durationSI += segment.getDuration().si;
        }
        this.segmentStartTimesSI[this.segmentStartTimesSI.length - 1] = durationSI;
        if (Math.abs(distanceSI - this.path.getLengthSI()) > MAX_DELTA_SI)
        {
            throw new NetworkException("Path length and calculated driven distance differ too much: delta = "
                + Math.abs(distanceSI - this.path.getLengthSI()) + " m");
        }
        this.totalDuration = new Time.Rel(durationSI, TimeUnit.SI);
        this.endSpeed = v0;
    }

    /**
     * Return the path that will be traveled.
     * @return the path
     */
    public final OTSLine3D getPath()
    {
        return this.path;
    }

    /**
     * Return the (absolute) start time of the operational plan.
     * @return startTime
     */
    public final Time.Abs getStartTime()
    {
        return this.startTime;
    }

    /**
     * Return the start speed of the entire plan.
     * @return startSpeed
     */
    public final Speed getStartSpeed()
    {
        return this.startSpeed;
    }

    /**
     * @return the end speed when completing the entire plan.
     */
    public final Speed getEndSpeed()
    {
        return this.endSpeed;
    }

    /**
     * Return the segments (parts with constant speed, acceleration or deceleration) of the operational plan.
     * @return operationalPlanSegmentList
     */
    public final List<OperationalPlan.Segment> getOperationalPlanSegmentList()
    {
        return this.operationalPlanSegmentList;
    }

    /**
     * Return the time it will take to complete the entire operational plan.
     * @return the time it will take to complete the entire operational plan
     */
    public final Time.Rel getTotalDuration()
    {
        return this.totalDuration;
    }

    /**
     * Return the time it will take to complete the entire operational plan.
     * @return the time it will take to complete the entire operational plan
     */
    public final Time.Abs getEndTime()
    {
        return this.startTime.plus(this.totalDuration);
    }

    /**
     * Provide the end location of this operational plan as a DirectedPoint.
     * @return the end location
     */
    public final DirectedPoint getEndLocation()
    {
        try
        {
            return this.path.getLocationFraction(1.0);
        }
        catch (NetworkException exception)
        {
            // should not happen -- only for fractions less than 0.0 or larger than 1.0.
            throw new RuntimeException(exception);
        }
    }

    /**
     * Return the time it will take to travel the given distance.
     * @param distance the distance to calculate the time for
     * @return the time it will take to have traveled the given distance
     */
    public final Time.Abs timeAtDistance(final Length.Rel distance)
    {
        // TODO
        return this.startTime.plus(this.totalDuration);
    }

    /**
     * Calculate the location at the given time.
     * @param time the absolute time to look for a location
     * @return the location at the given time.
     * @throws NetworkException when the time is after the validity of the operational plan
     */
    public final DirectedPoint getLocation(final Time.Abs time) throws NetworkException
    {
        // TODO...
        double fraction = time.minus(this.startTime).si / this.totalDuration.si;
        return this.path.getLocationFraction(fraction);
    }

    /**
     * Calculate the velocity of the GTU after the given duration since the start of the plan.
     * @param time the relative time to look for a location
     * @return the location after the given duration since the start of the plan.
     * @throws NetworkException when the time is after the validity of the operational plan
     */
    public final Speed getVelocity(final Time.Rel time) throws NetworkException
    {
        // TODO
        return SPEED_0;
    }

    /**
     * Calculate the velocity of the GTU at the given time.
     * @param time the absolute time to look for a location
     * @return the location at the given time.
     * @throws NetworkException when the time is after the validity of the operational plan
     */
    public final Speed getVelocity(final Time.Abs time) throws NetworkException
    {
        // TODO
        return SPEED_0;
    }

    /**
     * Calculate the acceleration of the GTU after the given duration since the start of the plan.
     * @param time the relative time to look for a location
     * @return the location after the given duration since the start of the plan.
     * @throws NetworkException when the time is after the validity of the operational plan
     */
    public final Acceleration getAcceleration(final Time.Rel time) throws NetworkException
    {
        // TODO
        return new Acceleration(0.0, AccelerationUnit.SI);
    }

    /**
     * Calculate the acceleration of the GTU at the given time.
     * @param time the absolute time to look for a location
     * @return the location at the given time.
     * @throws NetworkException when the time is after the validity of the operational plan
     */
    public final Acceleration getAcceleration(final Time.Abs time) throws NetworkException
    {
        // TODO
        return new Acceleration(0.0, AccelerationUnit.SI);
    }

    /**
     * Calculate the location after the given duration since the start of the plan.
     * @param time the relative time to look for a location
     * @return the location after the given duration since the start of the plan.
     * @throws NetworkException when the time is after the validity of the operational plan
     */
    public final DirectedPoint getLocation(final Time.Rel time) throws NetworkException
    {
        double fraction = time.si / this.totalDuration.si;
        return this.path.getLocationFraction(fraction);
    }

    /**
     * Calculate the distance traveled as part of this plan after the given duration since the start of the plan. This method
     * returns the traveled distance as a double in SI units.
     * @param duration the relative time to calculate the traveled distance
     * @return the distance traveled as part of this plan after the given duration since the start of the plan.
     * @throws NetworkException when the time is after the validity of the operational plan
     */
    public final double getTraveledDistanceSI(final Time.Rel duration) throws NetworkException
    {
        double timeSI = duration.si;
        double traveledDistance = 0.0;
        for (int i = 0; i < this.segmentStartTimesSI.length; i++)
        {
            if (timeSI >= this.segmentStartTimesSI[i] && timeSI <= this.segmentStartTimesSI[i + 1])
            {
                return traveledDistance
                    + this.operationalPlanSegmentList.get(i).distanceSI(timeSI - this.segmentStartTimesSI[i]);
            }
            traveledDistance += this.operationalPlanSegmentList.get(i).distanceSI();
        }
        throw new NetworkException("getTraveledDistance has a time " + duration
            + " which is longer than the duration of the entire plan: " + this.totalDuration);
    }

    /**
     * Calculate the distance traveled as part of this plan after the given duration since the start of the plan.
     * @param duration the relative time to calculate the traveled distance
     * @return the distance traveled as part of this plan after the given duration since the start of the plan.
     * @throws NetworkException when the time is after the validity of the operational plan
     */
    public final Length.Rel getTraveledDistance(final Time.Rel duration) throws NetworkException
    {
        return new Length.Rel(getTraveledDistanceSI(duration), LengthUnit.SI);
    }

    /**
     * Calculate the distance traveled as part of this plan at the given absolute time. This method returns the traveled
     * distance as a double in SI units.
     * @param time the absolute time to calculate the traveled distance for as part of this plan
     * @return the distance traveled as part of this plan at the given time
     * @throws NetworkException when the time is after the validity of the operational plan
     */
    public final double getTraveledDistanceSI(final Time.Abs time) throws NetworkException
    {
        return getTraveledDistanceSI(time.minus(this.startTime));
    }

    /**
     * Calculate the distance traveled as part of this plan at the given absolute time.
     * @param time the absolute time to calculate the traveled distance for as part of this plan
     * @return the distance traveled as part of this plan at the given time
     * @throws NetworkException when the time is after the validity of the operational plan
     */
    public final Length.Rel getTraveledDistance(final Time.Abs time) throws NetworkException
    {
        return new Length.Rel(getTraveledDistanceSI(time.minus(this.startTime)), LengthUnit.SI);
    }

    /** {@inheritDoc} */
    @SuppressWarnings("checkstyle:designforextension")
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result =
            prime * result
                + ((this.operationalPlanSegmentList == null) ? 0 : this.operationalPlanSegmentList.hashCode());
        result = prime * result + ((this.path == null) ? 0 : this.path.hashCode());
        result = prime * result + ((this.startSpeed == null) ? 0 : this.startSpeed.hashCode());
        result = prime * result + ((this.startTime == null) ? 0 : this.startTime.hashCode());
        return result;
    }

    /** {@inheritDoc} */
    @SuppressWarnings({"checkstyle:needbraces", "checkstyle:designforextension"})
    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OperationalPlan other = (OperationalPlan) obj;
        if (this.operationalPlanSegmentList == null)
        {
            if (other.operationalPlanSegmentList != null)
                return false;
        }
        else if (!this.operationalPlanSegmentList.equals(other.operationalPlanSegmentList))
            return false;
        if (this.path == null)
        {
            if (other.path != null)
                return false;
        }
        else if (!this.path.equals(other.path))
            return false;
        if (this.startSpeed == null)
        {
            if (other.startSpeed != null)
                return false;
        }
        else if (!this.startSpeed.equals(other.startSpeed))
            return false;
        if (this.startTime == null)
        {
            if (other.startTime != null)
                return false;
        }
        else if (!this.startTime.equals(other.startTime))
            return false;
        return true;
    }

    /** {@inheritDoc} */
    @SuppressWarnings("checkstyle:designforextension")
    @Override
    public String toString()
    {
        return "OperationalPlan [path=" + this.path + ", startTime=" + this.startTime + ", startSpeed="
            + this.startSpeed + ", operationalPlanSegmentList=" + this.operationalPlanSegmentList + ", totalDuration="
            + this.totalDuration + ", segmentStartTimesSI=" + Arrays.toString(this.segmentStartTimesSI)
            + ", endSpeed = " + this.endSpeed + "]";
    }

    /****************************************************************************************************************/
    /******************************************** SEGMENT DEFINITIONS ***********************************************/
    /****************************************************************************************************************/

    /**
     * The segment of an operational plan contains a part of the speed profile of a movement in which some of the variables
     * determining movement (speed, acceleration) are constant.
     * <p>
     * Copyright (c) 2013-2015 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. <br>
     * All rights reserved. <br>
     * BSD-style license. See <a href="http://opentrafficsim.org/docs/license.html">OpenTrafficSim License</a>.
     * </p>
     * $LastChangedDate: 2015-07-24 02:58:59 +0200 (Fri, 24 Jul 2015) $, @version $Revision: 1147 $, by $Author: averbraeck $,
     * initial version Nov 14, 2015 <br>
     * @author <a href="http://www.tbm.tudelft.nl/averbraeck">Alexander Verbraeck</a>
     * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
     */
    public abstract static class Segment implements Serializable
    {
        /** */
        private static final long serialVersionUID = 20151114L;

        /** the duration of the acceleration or speed for this segment. */
        @SuppressWarnings("checkstyle:visibilitymodifier")
        protected final Time.Rel duration;

        /** the initial speed for this segment. */
        @SuppressWarnings("checkstyle:visibilitymodifier")
        protected Speed v0;

        /**
         * @param duration the duration of the acceleration or speed for this segment
         */
        public Segment(final Time.Rel duration)
        {
            super();
            this.duration = duration;
        }

        /**
         * @param v0 the initial speed of this segment; called from the Operational Plan constructor.
         */
        final void setV0(final Speed v0)
        {
            this.v0 = v0;
        }

        /**
         * @return duration the duration of the acceleration or speed for this segment
         */
        public final Time.Rel getDuration()
        {
            return this.duration;
        }

        /**
         * @return duration the duration of the acceleration or speed for this segment
         */
        public final double getDurationSI()
        {
            return this.duration.si;
        }

        /**
         * Calculate the distance covered by a GTU in this segment.
         * @return distance covered
         */
        final double distanceSI()
        {
            return distanceSI(getDuration().si);
        }

        /**
         * Calculate the distance covered by a GTU in this segment after relative time t.
         * @param t the relative time since starting this segment for which to calculate the distance covered
         * @return distance covered
         */
        abstract double distanceSI(double t);

        /**
         * Calculate the speed of a GTU in this segment after relative time t.
         * @param t the relative time since starting this segment for which to calculate the speed
         * @return speed at relative time t
         */
        abstract double speedSI(double t);

        /**
         * Calculate the acceleration of a GTU in this segment after relative time t.
         * @param t the relative time since starting this segment for which to calculate the acceleration
         * @return acceleration at relative time t
         */
        abstract double accelerationSI(double t);

        /**
         * Calculate the end speed for this segment.
         * @return speed at end of the segment
         */
        abstract Speed endSpeed();

        /** {@inheritDoc} */
        @SuppressWarnings("checkstyle:designforextension")
        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((this.duration == null) ? 0 : this.duration.hashCode());
            result = prime * result + ((this.v0 == null) ? 0 : this.v0.hashCode());
            return result;
        }

        /** {@inheritDoc} */
        @SuppressWarnings({"checkstyle:needbraces", "checkstyle:designforextension"})
        @Override
        public boolean equals(final Object obj)
        {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Segment other = (Segment) obj;
            if (this.duration == null)
            {
                if (other.duration != null)
                    return false;
            }
            else if (!this.duration.equals(other.duration))
                return false;
            if (this.v0 == null)
            {
                if (other.v0 != null)
                    return false;
            }
            else if (!this.v0.equals(other.v0))
                return false;
            return true;
        }
    }

    /**
     * The segment of an operational plan in which the acceleration is constant.
     * <p>
     * Copyright (c) 2013-2015 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. <br>
     * All rights reserved. <br>
     * BSD-style license. See <a href="http://opentrafficsim.org/docs/license.html">OpenTrafficSim License</a>.
     * </p>
     * $LastChangedDate: 2015-07-24 02:58:59 +0200 (Fri, 24 Jul 2015) $, @version $Revision: 1147 $, by $Author: averbraeck $,
     * initial version Nov 14, 2015 <br>
     * @author <a href="http://www.tbm.tudelft.nl/averbraeck">Alexander Verbraeck</a>
     * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
     */
    public static class AccelerationSegment extends Segment
    {
        /** */
        private static final long serialVersionUID = 20151114L;

        /** the acceleration for the given duration. */
        private final Acceleration acceleration;

        /**
         * @param duration the duration of the constant acceleration for this segment
         * @param acceleration the acceleration for the given duration
         */
        public AccelerationSegment(final Time.Rel duration, final Acceleration acceleration)
        {
            super(duration);
            this.acceleration = acceleration;
        }

        /** {@inheritDoc} */
        @Override
        final double distanceSI(final double t)
        {
            return this.v0.si * t + 0.5 * this.acceleration.si * t * t;
        }

        /** {@inheritDoc} */
        @Override
        final double accelerationSI(final double t)
        {
            return this.acceleration.si;
        }

        /** {@inheritDoc} */
        @Override
        final double speedSI(final double t)
        {
            return this.v0.si + this.acceleration.si * t;
        }

        /** {@inheritDoc} */
        @Override
        final Speed endSpeed()
        {
            return this.v0.plus(this.acceleration.multiplyBy(getDuration()));
        }

        /** {@inheritDoc} */
        @Override
        public final int hashCode()
        {
            final int prime = 31;
            int result = super.hashCode();
            result = prime * result + ((this.acceleration == null) ? 0 : this.acceleration.hashCode());
            return result;
        }

        /** {@inheritDoc} */
        @Override
        @SuppressWarnings("checkstyle:needbraces")
        public final boolean equals(final Object obj)
        {
            if (this == obj)
                return true;
            if (!super.equals(obj))
                return false;
            if (getClass() != obj.getClass())
                return false;
            AccelerationSegment other = (AccelerationSegment) obj;
            if (this.acceleration == null)
            {
                if (other.acceleration != null)
                    return false;
            }
            else if (!this.acceleration.equals(other.acceleration))
                return false;
            return true;
        }

        /** {@inheritDoc} */
        @Override
        public final String toString()
        {
            return "AccelerationSegment [t=" + this.duration + ", v0=" + this.v0 + ", a=" + this.acceleration + "]";
        }
    }

    /**
     * The segment of an operational plan in which the speed is constant.
     * <p>
     * Copyright (c) 2013-2015 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. <br>
     * All rights reserved. <br>
     * BSD-style license. See <a href="http://opentrafficsim.org/docs/license.html">OpenTrafficSim License</a>.
     * </p>
     * $LastChangedDate: 2015-07-24 02:58:59 +0200 (Fri, 24 Jul 2015) $, @version $Revision: 1147 $, by $Author: averbraeck $,
     * initial version Nov 14, 2015 <br>
     * @author <a href="http://www.tbm.tudelft.nl/averbraeck">Alexander Verbraeck</a>
     * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
     */
    public static class SpeedSegment extends Segment
    {
        /** */
        private static final long serialVersionUID = 20151114L;

        /**
         * @param duration the duration of the constant speed for this segment
         */
        public SpeedSegment(final Time.Rel duration)
        {
            super(duration);
        }

        /** {@inheritDoc} */
        @Override
        final double distanceSI(final double t)
        {
            return this.v0.si * t;
        }

        /** {@inheritDoc} */
        @Override
        final double accelerationSI(final double t)
        {
            return 0.0;
        }

        /** {@inheritDoc} */
        @Override
        final double speedSI(final double t)
        {
            return this.v0.si;
        }

        /** {@inheritDoc} */
        @Override
        final Speed endSpeed()
        {
            return this.v0;
        }

        /**
         * @return speed
         */
        public final Speed getSpeed()
        {
            return this.v0;
        }

        /** {@inheritDoc} */
        @Override
        public final String toString()
        {
            return "SpeedSegment [t=" + this.duration + ", v0=" + this.v0 + "]";
        }
    }
}
