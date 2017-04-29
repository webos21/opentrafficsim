package org.opentrafficsim.core.units.distributions;

import java.io.Serializable;

import org.djunits.unit.AbsoluteLinearUnit;
import org.djunits.unit.AbsoluteTemperatureUnit;
import org.djunits.unit.AccelerationUnit;
import org.djunits.unit.AngleSolidUnit;
import org.djunits.unit.AngleUnit;
import org.djunits.unit.AreaUnit;
import org.djunits.unit.DensityUnit;
import org.djunits.unit.DimensionlessUnit;
import org.djunits.unit.DirectionUnit;
import org.djunits.unit.DurationUnit;
import org.djunits.unit.ElectricalChargeUnit;
import org.djunits.unit.ElectricalCurrentUnit;
import org.djunits.unit.ElectricalPotentialUnit;
import org.djunits.unit.ElectricalResistanceUnit;
import org.djunits.unit.EnergyUnit;
import org.djunits.unit.FlowMassUnit;
import org.djunits.unit.FlowVolumeUnit;
import org.djunits.unit.ForceUnit;
import org.djunits.unit.FrequencyUnit;
import org.djunits.unit.LengthUnit;
import org.djunits.unit.LinearDensityUnit;
import org.djunits.unit.MassUnit;
import org.djunits.unit.PositionUnit;
import org.djunits.unit.PowerUnit;
import org.djunits.unit.PressureUnit;
import org.djunits.unit.SpeedUnit;
import org.djunits.unit.TemperatureUnit;
import org.djunits.unit.TimeUnit;
import org.djunits.unit.TorqueUnit;
import org.djunits.unit.Unit;
import org.djunits.unit.VolumeUnit;
import org.djunits.value.Absolute;
import org.djunits.value.Relative;
import org.djunits.value.vdouble.scalar.AbsoluteTemperature;
import org.djunits.value.vdouble.scalar.AbstractDoubleScalarAbs;
import org.djunits.value.vdouble.scalar.AbstractDoubleScalarRel;
import org.djunits.value.vdouble.scalar.Acceleration;
import org.djunits.value.vdouble.scalar.Angle;
import org.djunits.value.vdouble.scalar.AngleSolid;
import org.djunits.value.vdouble.scalar.Area;
import org.djunits.value.vdouble.scalar.Density;
import org.djunits.value.vdouble.scalar.Dimensionless;
import org.djunits.value.vdouble.scalar.Direction;
import org.djunits.value.vdouble.scalar.DoubleScalar;
import org.djunits.value.vdouble.scalar.Duration;
import org.djunits.value.vdouble.scalar.ElectricalCharge;
import org.djunits.value.vdouble.scalar.ElectricalCurrent;
import org.djunits.value.vdouble.scalar.ElectricalPotential;
import org.djunits.value.vdouble.scalar.ElectricalResistance;
import org.djunits.value.vdouble.scalar.Energy;
import org.djunits.value.vdouble.scalar.FlowMass;
import org.djunits.value.vdouble.scalar.FlowVolume;
import org.djunits.value.vdouble.scalar.Force;
import org.djunits.value.vdouble.scalar.Frequency;
import org.djunits.value.vdouble.scalar.Length;
import org.djunits.value.vdouble.scalar.LinearDensity;
import org.djunits.value.vdouble.scalar.Mass;
import org.djunits.value.vdouble.scalar.Position;
import org.djunits.value.vdouble.scalar.Power;
import org.djunits.value.vdouble.scalar.Pressure;
import org.djunits.value.vdouble.scalar.Speed;
import org.djunits.value.vdouble.scalar.Temperature;
import org.djunits.value.vdouble.scalar.Time;
import org.djunits.value.vdouble.scalar.Torque;
import org.djunits.value.vdouble.scalar.Volume;

import nl.tudelft.simulation.jstats.distributions.DistContinuous;

/**
 * <p>
 * Copyright (c) 2013-2017 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved. <br>
 * BSD-style license. See <a href="http://opentrafficsim.org/docs/license.html">OpenTrafficSim License</a>.
 * <p>
 * $LastChangedDate: 2015-07-26 01:01:13 +0200 (Sun, 26 Jul 2015) $, @version $Revision: 1155 $, by $Author: averbraeck $,
 * initial version Feb 2, 2015 <br>
 * @author <a href="http://www.tbm.tudelft.nl/averbraeck">Alexander Verbraeck</a>
 */
public interface ContinuousDistDoubleScalar
{
    /**
     * Absolute value.
     * @param <T> The absolute DoubleScalar type
     * @param <AU> The absolute unit type used
     * @param <RU> The relative unit type belonging to AU
     */
    class Abs<T extends AbstractDoubleScalarAbs<AU, T, RU, ?>, AU extends AbsoluteLinearUnit<AU, RU>, RU extends Unit<RU>>
            extends AbstractContinuousDistScalar implements Absolute, Serializable
    {
        /** */
        private static final long serialVersionUID = 20150000L;

        /**
         * @param distribution the wrapped distribution function.
         * @param unit the unit.
         */
        public Abs(final DistContinuous distribution, final AU unit)
        {
            super(distribution, unit);
        }

        /**
         * @param constant the constant value.
         * @param unit the unit.
         */
        public Abs(final double constant, final AU unit)
        {
            super(constant, unit);
        }

        /**
         * @return a drawn number from the distribution in the given unit.
         */
        @SuppressWarnings({ "unchecked", "rawtypes" })
        public final T draw()
        {
            switch (getUnit().getClass().getSimpleName())
            {
                case "DirectionUnit":
                    return (T) new Direction(getDistribution().draw(), (DirectionUnit) getUnit());

                case "PositionUnit":
                    return (T) new Position(getDistribution().draw(), (PositionUnit) getUnit());

                case "AbsoluteTemperatureUnit":
                    return (T) new AbsoluteTemperature(getDistribution().draw(), (AbsoluteTemperatureUnit) getUnit());

                case "TimeUnit":
                    return (T) new Time(getDistribution().draw(), (TimeUnit) getUnit());

                default:
                    return (T) new DoubleScalar.Abs(getDistribution().draw(), (AU) getUnit());
            }
        }

        /** {@inheritDoc} */
        @Override
        public final String toString()
        {
            return "ContinuousDistDoubleScalar.Abs [T=" + getUnit().getClass().getSimpleName() + "]";
        }

    }

    /**
     * Relative value.
     * @param <T> The absolute DoubleScalar type
     * @param <U> The unit type used
     */
    class Rel<T extends AbstractDoubleScalarRel<U, T>, U extends Unit<U>> extends AbstractContinuousDistScalar
            implements Relative, Serializable
    {
        /** */
        private static final long serialVersionUID = 20150000L;

        /**
         * @param distribution the wrapped distribution function.
         * @param unit the unit.
         */
        public Rel(final DistContinuous distribution, final U unit)
        {
            super(distribution, unit);
        }

        /**
         * @param constant the constant value.
         * @param unit the unit.
         */
        public Rel(final double constant, final U unit)
        {
            super(constant, unit);
        }

        /**
         * @return a drawn number from the distribution in the given unit.
         */
        @SuppressWarnings({ "unchecked", "rawtypes" })
        public final T draw()
        {
            switch (getUnit().getClass().getSimpleName())
            {
                case "AccelerationUnit":
                    return (T) new Acceleration(getDistribution().draw(), (AccelerationUnit) getUnit());

                case "AngleUnit":
                    return (T) new Angle(getDistribution().draw(), (AngleUnit) getUnit());

                case "AngleSolidUnit":
                    return (T) new AngleSolid(getDistribution().draw(), (AngleSolidUnit) getUnit());

                case "AreaUnit":
                    return (T) new Area(getDistribution().draw(), (AreaUnit) getUnit());

                case "DensityUnit":
                    return (T) new Density(getDistribution().draw(), (DensityUnit) getUnit());

                case "DimensionlessUnit":
                    return (T) new Dimensionless(getDistribution().draw(), (DimensionlessUnit) getUnit());

                case "DurationUnit":
                    return (T) new Duration(getDistribution().draw(), (DurationUnit) getUnit());

                case "ElectricalChargeUnit":
                    return (T) new ElectricalCharge(getDistribution().draw(), (ElectricalChargeUnit) getUnit());

                case "ElectricalCurrentUnit":
                    return (T) new ElectricalCurrent(getDistribution().draw(), (ElectricalCurrentUnit) getUnit());

                case "ElectricalPotentialUnit":
                    return (T) new ElectricalPotential(getDistribution().draw(), (ElectricalPotentialUnit) getUnit());

                case "ElectricalResistanceUnit":
                    return (T) new ElectricalResistance(getDistribution().draw(), (ElectricalResistanceUnit) getUnit());

                case "EnergyUnit":
                    return (T) new Energy(getDistribution().draw(), (EnergyUnit) getUnit());

                case "FlowMassUnit":
                    return (T) new FlowMass(getDistribution().draw(), (FlowMassUnit) getUnit());

                case "FlowVolumeUnit":
                    return (T) new FlowVolume(getDistribution().draw(), (FlowVolumeUnit) getUnit());

                case "ForceUnit":
                    return (T) new Force(getDistribution().draw(), (ForceUnit) getUnit());

                case "FrequencyUnit":
                    return (T) new Frequency(getDistribution().draw(), (FrequencyUnit) getUnit());

                case "LengthUnit":
                    return (T) new Length(getDistribution().draw(), (LengthUnit) getUnit());

                case "LinearDensityUnit":
                    return (T) new LinearDensity(getDistribution().draw(), (LinearDensityUnit) getUnit());

                case "MassUnit":
                    return (T) new Mass(getDistribution().draw(), (MassUnit) getUnit());

                case "PowerUnit":
                    return (T) new Power(getDistribution().draw(), (PowerUnit) getUnit());

                case "PressureUnit":
                    return (T) new Pressure(getDistribution().draw(), (PressureUnit) getUnit());

                case "SpeedUnit":
                    return (T) new Speed(getDistribution().draw(), (SpeedUnit) getUnit());

                case "TemperatureUnit":
                    return (T) new Temperature(getDistribution().draw(), (TemperatureUnit) getUnit());

                case "TorqueUnit":
                    return (T) new Torque(getDistribution().draw(), (TorqueUnit) getUnit());

                case "VolumeUnit":
                    return (T) new Volume(getDistribution().draw(), (VolumeUnit) getUnit());

                default:
                    return (T) new DoubleScalar.Rel(getDistribution().draw(), getUnit());
            }
        }

        /** {@inheritDoc} */
        @Override
        public final String toString()
        {
            return "ContinuousDistDoubleScalar.Rel [T=" + getUnit().getClass().getSimpleName() + "]";
        }

    }

}
