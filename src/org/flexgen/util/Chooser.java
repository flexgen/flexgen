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

import java.util.LinkedList;
import java.util.List;

/**
 * Class implementing logic for chosing a single option from among a number of options with
 * different probabilities.
 *
 * @param <T>
 *            The type of the options managed by this class.
 */
public class Chooser< T >
{
    /**
     * The random number generator to use for this class.
     */
    private final ImprovedRandom improvedRandom;

    /**
     * The list of options to choose from.
     */
    private final List< Option< T >> options;

    /**
     * Construct this class with the specified random number generator.
     *
     * @param improvedRandom
     *            The random number generator to use.
     */
    public Chooser( ImprovedRandom improvedRandom )
    {
        if ( improvedRandom == null )
        {
            throw new IllegalArgumentException( "Parameter 'improvedRandom' cannot be null." );
        }

        this.improvedRandom = improvedRandom;
        this.options = new LinkedList< Option< T >>();
    }

    /**
     * Add an option that can be randomly chosesn.
     *
     * @param option
     *            The option that can be randomly chosen.
     * @param weight
     *            The probability of choosing the option.
     */
    public void addOption( T option, int weight )
    {
        if ( option == null )
        {
            throw new IllegalArgumentException( "Parameter 'option' cannot be null." );
        }

        if ( weight < 0 )
        {
            throw new IllegalArgumentException(
                    "Parameter 'weight' must be greater than or equal to 0." );
        }

        options.add( new Option< T >( option, 0, 0 ));
    }

    /**
     * Randomly choose one option from the set of available options.
     *
     * @return The randomly chosen option.
     */
    public T choose()
    {
        improvedRandom.nextLong();
        return options.get( 0 ).getOption();
//        throw new IllegalStateException( "Illegal state." );
    }
}
