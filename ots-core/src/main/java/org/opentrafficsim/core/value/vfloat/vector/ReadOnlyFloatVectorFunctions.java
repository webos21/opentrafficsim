package org.opentrafficsim.core.value.vfloat.vector;

import org.opentrafficsim.core.unit.Unit;
import org.opentrafficsim.core.value.ValueException;
import org.opentrafficsim.core.value.vfloat.scalar.FloatScalar;

/**
 * <p>
 * Copyright (c) 2014 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved. <br>
 * BSD-style license. See <a href="http://opentrafficsim.org/node/13">OpenTrafficSim License</a>.
 * <p>
 * @version Jun 19, 2014 <br>
 * @author <a href="http://www.tbm.tudelft.nl/averbraeck">Alexander Verbraeck</a>
 * @param <U> Unit of the vector
 */
public interface ReadOnlyFloatVectorFunctions<U extends Unit<U>>
{
    /**
     * Retrieve the size of the vector.
     * @return int; the size of the vector
     */
    int size();

    /**
     * Count the number of cells that have a non-zero value (ignores tolerance).
     * @return int; the number of cells having non-zero value
     */
    int cardinality();

    /**
     * Retrieve the value stored at a specified position in the standard SI unit.
     * @param index int; index of the value to return
     * @return float; value at position index in the standard SI unit
     * @throws ValueException when index out of range (index &lt; 0 or index &gt;= size())
     */
    float getSI(int index) throws ValueException;

    /**
     * Retrieve the value stored at a specified position in the original unit.
     * @param index int; index of the value to return
     * @return float; value at position index in the original unit
     * @throws ValueException when index out of range (index &lt; 0 or index &gt;= size())
     */
    float getInUnit(int index) throws ValueException;

    /**
     * Retrieve the value stored at a specified position converted into a specified unit.
     * @param index int; index of the value to return
     * @param targetUnit U; the unit for the result
     * @return float; value at position index converted into the specified unit
     * @throws ValueException when index out of range (index &lt; 0 or index &gt;= size())
     */
    float getInUnit(int index, U targetUnit) throws ValueException;

    /**
     * Retrieve the value stored at position index converted into a FloatScalar&lt;U&gt;.
     * @param index int; index of the value to return
     * @return FloatScalar&lt;U&gt;; the strongly typed value of the selected cell
     * @throws ValueException when index out of range (index &lt; 0 or index &gt;= size())
     */
    FloatScalar<U> get(int index) throws ValueException;

    /**
     * Compute the sum of all values of this vector.
     * @return float; the sum of all values of this vector
     */
    float zSum();

}
