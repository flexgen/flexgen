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

package org.flexgen.map;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Class implementing logic for filtering map tile locations based upon a specified rectangular
 * area.
 */
public class RectangularMapTileLocationFilter implements MapTileLocationFilter
{
    /**
     * Smallest possible X coordinate for map tiles in the map.
     */
    private final int minX;

    /**
     * Smallest possible Y coordinate for map tiles in the map.
     */
    private final int minY;

    /**
     * Largest possible X coordinate for map tiles in the map.
     */
    private final int maxX;

    /**
     * Largest possible Y coordinate for map tiles in the map.
     */
    private final int maxY;

    /**
     * Construct a rectangular map tile location filter.
     *
     * @param minX
     *            Smallest possible X coordinate for allowed map tile locations.
     * @param minY
     *            Smallest possible Y coordinate for allowed map tile locations.
     * @param maxX
     *            Largest possible X coordinate for allowed map tile locations. Must be greater than
     *            or equal to minX.
     * @param maxY
     *            Largest possible Y coordinate for allowed map tile locations. Must be greater than
     *            or equal to minY.
     */
    public RectangularMapTileLocationFilter( int minX, int minY, int maxX, int maxY )
    {
        if ( maxX < minX )
        {
            throw new IllegalArgumentException(
                    "Parameter 'maxX' must be greater than or equal to parameter 'minX'." );
        }

        if ( maxY < minY )
        {
            throw new IllegalArgumentException(
                    "Parameter 'maxY' must be greater than or equal to parameter 'minY'." );
        }

        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    /**
     * Get the smallest possible X coordinate for allowed map tile locations.
     *
     * @return The smallest possible X coordinate for allowed map tile locations.
     */
    public int getMinX()
    {
        return minX;
    }

    /**
     * Get the smallest possible Y coordinate for allowed map tile locations.
     *
     * @return The smallest possible Y coordinate for allowed map tile locations.
     */
    public int getMinY()
    {
        return minY;
    }

    /**
     * Get the largest possible X coordinate for allowed map tile locations.
     *
     * @return The largest possible X coordinate for allowed map tile locations.
     */
    public int getMaxX()
    {
        return maxX;
    }

    /**
     * Get the largest possible Y coordinate for allowed map tile locations.
     *
     * @return The largest possible Y coordinate for allowed map tile locations.
     */
    public int getMaxY()
    {
        return maxY;
    }

    /**
     * Get a filtered collection of map tile locations.
     *
     * @param mapTileLocations
     *            Collection of map tile locations to filter.
     *
     * @return A filtered collection of map tile locations.
     */
    public Collection< MapTileLocation > getFilteredMapTileLocations(
            Collection< MapTileLocation > mapTileLocations )
    {
        Collection< MapTileLocation > filteredOpenLocations = new LinkedList< MapTileLocation >();

        for ( MapTileLocation mapTileLocation : mapTileLocations )
        {
            if (( mapTileLocation.getX() >= minX ) &&
                ( mapTileLocation.getY() >= minY ) &&
                ( mapTileLocation.getX() <= maxX ) &&
                ( mapTileLocation.getY() <= maxY ))
            {
                filteredOpenLocations.add( mapTileLocation );
            }
        }

        return filteredOpenLocations;
    }
}
