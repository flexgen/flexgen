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

package org.flexgen.map;

/**
 * Class representing a map tile that can be used in maps. Contains a two-dimensional array of map
 * units that define the map tile.
 */
public class MapTile
{
    /**
     * Construct a map tile.
     *
     * @param mapUnits
     *            Two-dimensional array of map units that define the map tile. Cannot be null. Must
     *            contain at least one row. Each row must contain the same number of elements. Must
     *            contain the same number of columns as it does rows.
     */
    public MapTile( MapUnit[][] mapUnits )
    {
        if ( mapUnits == null )
        {
            throw new IllegalArgumentException( "Parameter 'mapUnits' cannot be null." );
        }

        if ( mapUnits.length == 0 )
        {
            throw new IllegalArgumentException(
                    "Parameter 'mapUnits' must contain at least one element." );
        }

        int columnCount = mapUnits[ 0 ].length;

        for ( MapUnit[] row : mapUnits )
        {
            if ( row.length != columnCount )
            {
                throw new IllegalArgumentException( "Parameter 'mapUnits' must contain the same " +
                                                    "number of elements in each row." );
            }
        }

        int rowCount = mapUnits.length;

        if ( columnCount != rowCount )
        {
            throw new IllegalArgumentException( "Parameter 'mapUnits' must contain the same " +
                                                "number of columns as it does rows." );
        }
    }
}
