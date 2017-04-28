package org.opentrafficsim.core.gtu.perception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.opentrafficsim.core.gtu.GTU;
import org.opentrafficsim.core.gtu.GTUException;
import org.opentrafficsim.core.gtu.behavioralcharacteristics.ParameterException;
import org.opentrafficsim.core.gtu.plan.operational.OperationalPlanException;
import org.opentrafficsim.core.network.NetworkException;

/**
 * <p>
 * Copyright (c) 2013-2017 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved. <br>
 * BSD-style license. See <a href="http://opentrafficsim.org/docs/current/license.html">OpenTrafficSim License</a>.
 * <p>
 * @version $Revision$, $LastChangedDate$, by $Author$, initial version Jul 29, 2016 <br>
 * @author <a href="http://www.tbm.tudelft.nl/averbraeck">Alexander Verbraeck</a>
 * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
 * @author <a href="http://www.transport.citg.tudelft.nl">Wouter Schakel</a>
 */

public abstract class AbstractPerception implements Perception
{

    /** */
    private static final long serialVersionUID = 20160729L;

    /** Set of available perception categories. */
    private final Map<Class<? extends PerceptionCategory>, PerceptionCategory> perceptionCategories =
        new LinkedHashMap<>();
    
    /** GTU. */
    private GTU gtu;
    
    /**
     * Construct perception.
     * @param gtu GTU
     */
    public AbstractPerception(final GTU gtu)
    {
        this.gtu = gtu;
    }
    
    /** {@inheritDoc} */
    @SuppressWarnings("checkstyle:designforextension")
    public GTU getGtu()
    {
        return this.gtu;
    }

    /** {@inheritDoc} */
    @Override
    public final void addPerceptionCategory(final PerceptionCategory perceptionCategory)
    {
        // guarantees correct combination of class and perception category
        this.perceptionCategories.put(perceptionCategory.getClass(), perceptionCategory);
    }

    /** {@inheritDoc} */
    @Override
    public final <T extends PerceptionCategory> boolean contains(final Class<T> clazz)
    {
        for (Class<?> category : this.perceptionCategories.keySet())
        {
            if (clazz.isAssignableFrom(category))
            {
                // isAssignableFrom takes care of implementation of the category
                return true;
            }
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override
    @SuppressWarnings("unchecked")
    public final <T extends PerceptionCategory> T getPerceptionCategory(final Class<T> category)
        throws OperationalPlanException
    {
        for (Class<?> clazz : this.perceptionCategories.keySet())
        {
            if (category.isAssignableFrom(clazz))
            {
                // addPerceptionCategory guarantees correct combination of class and perception category
                // isAssignableFrom takes care of implementation of the category
                return (T) this.perceptionCategories.get(clazz);
            }
        }
        throw new OperationalPlanException("Perception category" + category + " is not present.");
    }
    
    /** {@inheritDoc} */
    @Override
    public final void removePerceptionCategory(final PerceptionCategory perceptionCategory)
    {
        for (Class<?> category : this.perceptionCategories.keySet())
        {
            if (perceptionCategory.getClass().isAssignableFrom(category))
            {
                // addPerceptionCategory guarantees correct combination of class and perception category
                // isAssignableFrom takes care of implementation of the category
                this.perceptionCategories.remove(perceptionCategory.getClass());
                return;
            }
        }
    }

    /** {@inheritDoc} */
    @SuppressWarnings("checkstyle:designforextension")
    public void perceive() throws GTUException, NetworkException, ParameterException
    {
        for (PerceptionCategory category : this.perceptionCategories.values())
        {
            category.updateAll();
        }
    }
    
    /** {@inheritDoc} */
    @SuppressWarnings("checkstyle:designforextension")
    public String toString()
    {
        StringBuilder s = new StringBuilder("Perception [");
        String sep = "";
        for (PerceptionCategory cat : this.perceptionCategories.values())
        {
            s.append(sep);
            s.append(cat);
            sep = ", ";
        }
        s.append("]");
        return s.toString();
    }
    
}
