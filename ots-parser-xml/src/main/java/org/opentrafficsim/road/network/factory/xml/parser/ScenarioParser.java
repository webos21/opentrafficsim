package org.opentrafficsim.road.network.factory.xml.parser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.djunits.value.vdouble.scalar.Dimensionless;
import org.djutils.base.Identifiable;
import org.djutils.eval.Eval;
import org.djutils.eval.RetrieveValue;
import org.opentrafficsim.road.network.factory.xml.CircularDependencyException;
import org.opentrafficsim.xml.bindings.types.ExpressionType;
import org.opentrafficsim.xml.generated.Demand;
import org.opentrafficsim.xml.generated.InputParameters;
import org.opentrafficsim.xml.generated.ModelIdReferralType;
import org.opentrafficsim.xml.generated.ScenarioType;
import org.opentrafficsim.xml.generated.Scenarios;

/**
 * Parser of scenario tags.
 * <p>
 * Copyright (c) 2013-2024 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved. <br>
 * BSD-style license. See <a href="https://opentrafficsim.org/docs/license.html">OpenTrafficSim License</a>.
 * </p>
 * @author <a href="https://github.com/averbraeck">Alexander Verbraeck</a>
 * @author <a href="https://tudelft.nl/staff/p.knoppers-1">Peter Knoppers</a>
 * @author <a href="https://github.com/wjschakel">Wouter Schakel</a>
 */
public class ScenarioParser
{

    /** Last object looked up via evaluator. */
    @Deprecated
    // TODO: delete this and make sure regular evaluation output is used where this is used
    public static Object lastLookedUp;
    
    /**
     * Parse input parameters for scenario.
     * @param scenarios Scenarios; scenarios tag.
     * @param scenario String; name of scenario tp parse.
     * @return Eval; expression evaluator for all expression in XML.
     * @throws CircularDependencyException when there is circular dependency between parameters.
     */
    public static Eval parseInputParameters(final Scenarios scenarios, final String scenario) throws CircularDependencyException
    {
        if (scenarios == null)
        {
            return new Eval();
        }
        ScenariosWrapper scenariosWrapper = new ScenariosWrapper()
        {
            /** {@inheritDoc} */
            @Override
            public Iterable<ParameterWrapper> getDefaultInputParameters()
            {
                return getInputParameterIterator(scenarios.getDefaultInputParameters());
            }

            /** {@inheritDoc} */
            @Override
            public Iterable<ParameterWrapper> getScenarioInputParameters()
            {
                for (ScenarioType scenarioTag : scenarios.getScenario())
                {
                    if (scenarioTag.getId().equals(scenario))
                    {
                        return getInputParameterIterator(scenarioTag.getInputParameters());
                    }
                }
                return null;
            }
        };
        return parseInputParameters(scenariosWrapper);
    }

    /**
     * Parse input parameters for scenario.
     * @param scenariosWrapper ScenariosWrapper; scenarios wrapper, from XML or Xsd Tree nodes in editor.
     * @return Eval; expression evaluator for all expression in XML.
     * @throws CircularDependencyException when there is circular dependency between parameters.
     */
    public static Eval parseInputParameters(final ScenariosWrapper scenariosWrapper) throws CircularDependencyException
    {
        Map<String, Supplier<?>> defaultsMap = new LinkedHashMap<>();
        ParameterMap defaults = new ParameterMap(defaultsMap);
        Eval eval = new Eval().setRetrieveValue(defaults);
        parseInputParameters(scenariosWrapper.getDefaultInputParameters(), defaultsMap, defaults);
        ParameterMap inputParameters = defaults;
        if (scenariosWrapper.getScenarioInputParameters() != null)
        {
            Map<String, Supplier<?>> inputParametersMap = new LinkedHashMap<>();
            inputParameters = new ParameterMap(inputParametersMap);
            defaults.setScenarioMap(inputParameters);
            parseInputParameters(scenariosWrapper.getScenarioInputParameters(), inputParametersMap, defaults);
        }
        return eval;
    }

    /**
     * Creates parsable parameters from an InputParameters XML tag (default or of a scenario).
     * @param inputParameters InputParameters; parameters XML tag.
     * @return ITerable&lt;ParameterWrapper&gt;; parameters in generic form for parsing.
     */
    private static Iterable<ParameterWrapper> getInputParameterIterator(final InputParameters inputParameters)
    {
        List<ParameterWrapper> parameters = new ArrayList<>();
        if (inputParameters == null)
        {
            return parameters;
        }
        for (Serializable parameter : inputParameters.getDurationOrLengthOrSpeed())
        {
            if (parameter instanceof org.opentrafficsim.xml.generated.InputParameters.Duration)
            {
                org.opentrafficsim.xml.generated.InputParameters.Duration p =
                        (org.opentrafficsim.xml.generated.InputParameters.Duration) parameter;
                parameters.add(new ParameterWrapper(p.getId(), p.getValue()));
            }
            else if (parameter instanceof org.opentrafficsim.xml.generated.InputParameters.Length)
            {
                org.opentrafficsim.xml.generated.InputParameters.Length p =
                        (org.opentrafficsim.xml.generated.InputParameters.Length) parameter;
                parameters.add(new ParameterWrapper(p.getId(), p.getValue()));
            }
            else if (parameter instanceof org.opentrafficsim.xml.generated.InputParameters.Speed)
            {
                org.opentrafficsim.xml.generated.InputParameters.Speed p =
                        (org.opentrafficsim.xml.generated.InputParameters.Speed) parameter;
                parameters.add(new ParameterWrapper(p.getId(), p.getValue()));
            }
            else if (parameter instanceof org.opentrafficsim.xml.generated.InputParameters.Acceleration)
            {
                org.opentrafficsim.xml.generated.InputParameters.Acceleration p =
                        (org.opentrafficsim.xml.generated.InputParameters.Acceleration) parameter;
                parameters.add(new ParameterWrapper(p.getId(), p.getValue()));
            }
            else if (parameter instanceof org.opentrafficsim.xml.generated.InputParameters.LinearDensity)
            {
                org.opentrafficsim.xml.generated.InputParameters.LinearDensity p =
                        (org.opentrafficsim.xml.generated.InputParameters.LinearDensity) parameter;
                parameters.add(new ParameterWrapper(p.getId(), p.getValue()));
            }
            else if (parameter instanceof org.opentrafficsim.xml.generated.InputParameters.Frequency)
            {
                org.opentrafficsim.xml.generated.InputParameters.Frequency p =
                        (org.opentrafficsim.xml.generated.InputParameters.Frequency) parameter;
                parameters.add(new ParameterWrapper(p.getId(), p.getValue()));
            }
            else if (parameter instanceof org.opentrafficsim.xml.generated.InputParameters.Double)
            {
                org.opentrafficsim.xml.generated.InputParameters.Double p =
                        (org.opentrafficsim.xml.generated.InputParameters.Double) parameter;
                parameters.add(new ParameterWrapper(p.getId(), p.getValue()));
            }
            else if (parameter instanceof org.opentrafficsim.xml.generated.InputParameters.Fraction)
            {
                org.opentrafficsim.xml.generated.InputParameters.Fraction p =
                        (org.opentrafficsim.xml.generated.InputParameters.Fraction) parameter;
                parameters.add(new ParameterWrapper(p.getId(), p.getValue()));
            }
            else if (parameter instanceof org.opentrafficsim.xml.generated.InputParameters.Integer)
            {
                org.opentrafficsim.xml.generated.InputParameters.Integer p =
                        (org.opentrafficsim.xml.generated.InputParameters.Integer) parameter;
                parameters.add(new ParameterWrapper(p.getId(), p.getValue()));
            }
            else if (parameter instanceof org.opentrafficsim.xml.generated.InputParameters.Boolean)
            {
                org.opentrafficsim.xml.generated.InputParameters.Boolean p =
                        (org.opentrafficsim.xml.generated.InputParameters.Boolean) parameter;
                parameters.add(new ParameterWrapper(p.getId(), p.getValue()));
            }
            else if (parameter instanceof org.opentrafficsim.xml.generated.InputParameters.String)
            {
                org.opentrafficsim.xml.generated.InputParameters.String p =
                        (org.opentrafficsim.xml.generated.InputParameters.String) parameter;
                parameters.add(new ParameterWrapper(p.getId(), p.getValue()));
            }
            else if (parameter instanceof org.opentrafficsim.xml.generated.InputParameters.Class)
            {
                org.opentrafficsim.xml.generated.InputParameters.Class p =
                        (org.opentrafficsim.xml.generated.InputParameters.Class) parameter;
                parameters.add(new ParameterWrapper(p.getId(), p.getValue()));
            }
        }
        return parameters;
    }

    /**
     * Parse input parameters.
     * @param inputParameters Iterable&lt;ParameterWrapper&gt;; xml tag.
     * @param map Map&lt;String, Supplier&lt;?&gt;&gt;; map that underlines inputParameters.
     * @param retrieve ParameterMap; value retrieval.
     * @throws CircularDependencyException when there is circular dependency between parameters.
     */
    private static void parseInputParameters(final Iterable<ParameterWrapper> inputParameters,
            final Map<String, Supplier<?>> map, final ParameterMap retrieve) throws CircularDependencyException
    {
        boolean failed = true;
        int pass = 1;
        while (failed)
        {
            failed = false;
            int size = map.size();
            boolean empty = true;
            for (ParameterWrapper parameter : inputParameters)
            {
                empty = false;
                try
                {
                    // need to create a new Eval each time, as input parameters may depend on others
                    // NOTE: if Eval has additional user defined functions or unit parsers, that't not included here
                    String id = parameter.getId();
                    map.put(id.substring(1, id.length() - 1), () -> parameter.get().get(new Eval().setRetrieveValue(retrieve)));
                }
                catch (RuntimeException e)
                {
                    failed = true;
                }
            }
            if ((map.size() == size && !empty) || pass == 50)
            {
                throw new CircularDependencyException("Could not parse input parameters due to circular dependency.");
            }
            pass++;
        }
    }

    /**
     * Parse model ID referrals.
     * @param scenario List&lt;ScenarioType&gt;; scenario
     * @param demand Demand; demand
     * @param eval Eval; expression evaluator.
     * @return map from ID to ID
     */
    public static final Map<String, String> parseModelIdReferral(final List<ScenarioType> scenario, final Demand demand,
            final Eval eval)
    {
        // TODO: use run to select scenario (probably outside this class, and accept a single Scenario
        Map<String, String> map = new LinkedHashMap<>();
        for (ModelIdReferralType modelIdReferral : demand.getModelIdReferral())
        {
            map.put(modelIdReferral.getId(), modelIdReferral.getModelId().get(eval));
        }
        // overwrite with scenario level ID referrals
        if (!scenario.isEmpty())
        {
            for (ModelIdReferralType modelIdReferral : scenario.get(0).getModelIdReferral())
            {
                map.put(modelIdReferral.getId(), modelIdReferral.getModelId().get(eval));
            }
        }
        return map;
    }

    /**
     * Generic scenario form for parsing.
     * <p>
     * Copyright (c) 2023-2024 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved.
     * <br>
     * BSD-style license. See <a href="https://opentrafficsim.org/docs/license.html">OpenTrafficSim License</a>.
     * </p>
     * @author <a href="https://github.com/wjschakel">Wouter Schakel</a>
     */
    public static interface ScenariosWrapper
    {
        /**
         * Returns default parameters in generic form for parsing.
         * @return Iterable&lt;ParameterWrapper&gt;; default parameters in generic form for parsing.
         */
        Iterable<ParameterWrapper> getDefaultInputParameters();

        /**
         * Returns scenario parameters in generic form for parsing.
         * @return Iterable&lt;ParameterWrapper&gt;; scenario parameters in generic form for parsing.
         */
        Iterable<ParameterWrapper> getScenarioInputParameters();
    }

    /**
     * Generic parameters form for parsing.
     * <p>
     * Copyright (c) 2023-2024 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved.
     * <br>
     * BSD-style license. See <a href="https://opentrafficsim.org/docs/license.html">OpenTrafficSim License</a>.
     * </p>
     * @author <a href="https://github.com/wjschakel">Wouter Schakel</a>
     */
    public static class ParameterWrapper implements Identifiable, Supplier<ExpressionType<?>>
    {
        /** Id. */
        private final String id;

        /** Value expression type. */
        private final ExpressionType<?> value;

        /**
         * Constructor.
         * @param id String; id.
         * @param value ExpressionType&lt;?&gt;; value expression type.
         */
        public ParameterWrapper(final String id, final ExpressionType<?> value)
        {
            this.id = id;
            this.value = value;
        }

        /** {@inheritDoc} */
        @Override
        public String getId()
        {
            return this.id;
        }

        /** {@inheritDoc} */
        @Override
        public ExpressionType<?> get()
        {
            return this.value;
        }
    }

    /**
     * Wraps parameters to provide for expressions.
     * <p>
     * Copyright (c) 2023-2024 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved.
     * <br>
     * BSD-style license. See <a href="https://opentrafficsim.org/docs/license.html">OpenTrafficSim License</a>.
     * </p>
     * @author <a href="https://github.com/wjschakel">Wouter Schakel</a>
     */
    private static class ParameterMap implements RetrieveValue
    {
        /** Map of name to suppliers (constant or distribution). */
        private final Map<String, Supplier<?>> map;

        /** More scenario input parameters. */
        private ParameterMap scenario;

        /**
         * Constructor.
         * @param map Map&lt;String, Supplier&lt;?&gt;&gt;; map that underlines input parameters.
         */
        public ParameterMap(final Map<String, Supplier<?>> map)
        {
            this.map = map;
        }

        /**
         * Set scenario input parameters.
         * @param scenario ParameterMap; parameter map of the scenario.
         */
        public void setScenarioMap(final ParameterMap scenario)
        {
            this.scenario = scenario;
        }

        /** {@inheritDoc} */
        @Override
        public Object lookup(final String name)
        {
            Object value;
            if (this.scenario != null && this.scenario.map.containsKey(name))
            {
                value = this.scenario.map.get(name).get();
            }
            else if (this.map.containsKey(name))
            {
                value = this.map.get(name).get();
            }
            else
            {
                throw new RuntimeException("Parameter " + name + " not available.");
            }
            if (value instanceof Double)
            {
                return Dimensionless.instantiateSI((Double) value);
            }
            lastLookedUp = value;
            return value;
        }
    }
}
