package org.opentrafficsim.core.value;

/**
 * <p>
 * Copyright (c) 2014 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved. <br>
 * BSD-style license. See <a href="http://opentrafficsim.org/node/13">OpenTrafficSim License</a>.
 * <p>
 * @version Jun 18, 2014 <br>
 * @author <a href="http://www.tbm.tudelft.nl/averbraeck">Alexander Verbraeck</a>
 * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
 */
public class ValueException extends Exception
{
    /** */
    private static final long serialVersionUID = 20140920L;

    /**
     * Construct a new ValueException.
     */
    public ValueException()
    {
        super();
    }

    /**
     * Construct a new ValueException.
     * @param message String; description of the problem
     */
    public ValueException(final String message)
    {
        super(message);
    }

    /**
     * Construct a new ValueException.
     * @param cause Throwable; the cause of this ValueException
     */
    public ValueException(final Throwable cause)
    {
        super(cause);
    }

    /**
     * Construct a new ValueException.
     * @param message String; description of the problem
     * @param cause Throwable; the cause of the ValueException
     */
    public ValueException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    /**
     * Construct a new ValueException.
     * @param message String; description of the problem
     * @param cause Throwable; the cause of this ValueException
     * @param enableSuppression boolean; whether or not suppression is enabled or disabled
     * @param writableStackTrace boolean; whether or not the stack trace should be writable
     */
    public ValueException(final String message, final Throwable cause, final boolean enableSuppression,
            final boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
