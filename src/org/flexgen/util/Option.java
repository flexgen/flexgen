/*

FlexGen : Flexible Map Generator Library

Copyright (C) 2009 - Jeffrey J. Weston <jjweston@gmail.com>
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, this
  list of conditions and the following disclaimer.
* Redistributions in binary form must reproduce the above copyright notice,
  this list of conditions and the following disclaimer in the documentation
  and/or other materials provided with the distribution.
* Neither the name of the FlexGen project nor the names of its contributors
  may be used to endorse or promote products derived from this software
  without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package org.flexgen.util;

/**
 * Class representing an option that can be randomly selected from among a number of options with
 * different probabilities.
 *
 * @param <T>
 *            The type of the option this class represents.
 */
public class Option< T >
{
    /**
     * The option represented by the class.
     */
    private final T option;

    /**
     * The lower bound of values that will result in this option being selected.
     */
    private final long startRange;

    /**
     * Construct an option.
     *
     * @param option
     *            The option represented by the class. Cannot be null.
     * @param startRange
     *            The lower bound of values that will result in this option being selected. Must be
     *            greater than or equal to 0.
     * @param endRange
     *            The upper bound of values that will result in this option being selected. Must be
     *            greater than or equal to startRange.
     */
    public Option( T option, long startRange, long endRange )
    {
        if ( option == null )
        {
            throw new IllegalArgumentException( "Parameter 'option' cannot be null." );
        }

        if ( startRange < 0 )
        {
            throw new IllegalArgumentException(
                    "Parameter 'startRange' must be greater than or equal to 0." );
        }

        if ( endRange < startRange )
        {
            throw new IllegalArgumentException(
                    "Parameter 'endRange' must be greater than or equal to startRange." );
        }

        this.option     = option;
        this.startRange = startRange;
    }

    /**
     * Get the option represented by this class.
     *
     * @return The option represented by this class.
     */
    public T getOption()
    {
        return option;
    }

    /**
     * Determine whether or not this option is within range of a specified value.
     *
     * @param value
     *            The value to check.
     *
     * @return True if the value is within range of this option, false otherwise.
     */
    public boolean withinRange( long value )
    {
        return value > startRange;
    }
}
