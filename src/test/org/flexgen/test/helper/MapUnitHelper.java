/*

FlexGen : Flexible Map Generator Library

Copyright (C) 2009-2011 Jeffrey J. Weston <jjweston@gmail.com>
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

package org.flexgen.test.helper;

import org.flexgen.map.MapUnit;

/**
 * Test helper class for the MapUnit class.
 */
public class MapUnitHelper
{
    /**
     * Build a map unit with default values.
     *
     * @return The generated map unit.
     */
    public static MapUnit build()
    {
        return new MapUnit( GeneralHelper.getUniqueString() );
    }

    /**
     * Build a two-dimensional array of map units with default values.
     *
     * @param mapUnitArraySize
     *            The size of the array to build.
     *
     * @return The generated map unit array.
     */
    public static MapUnit[][] buildArray( int mapUnitArraySize )
    {
        MapUnit[][] mapUnits = new MapUnit[ mapUnitArraySize ][ mapUnitArraySize ];

        for ( int i = 0; i < mapUnitArraySize; i++ )
        {
            for ( int j = 0; j < mapUnitArraySize; j++ )
            {
                mapUnits[ i ][ j ] = MapUnitHelper.build();
            }
        }

        return mapUnits;
    }

    /**
     * Build a two-dimensional array of map units with default values.
     *
     * @return The generated map unit array.
     */
    public static MapUnit[][] buildArray()
    {
        return MapUnitHelper.buildArray( 1 );
    }

    /**
     * Private constructor to keep this class from being instantiated since all methods are static.
     */
    private MapUnitHelper()
    {
    }
}
