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

package org.flexgen.example;

import java.util.Collection;
import java.util.LinkedList;

import org.flexgen.map.MapGenerator;
import org.flexgen.map.MapTileAddedListener;
import org.flexgen.map.MapTileLocation;
import org.flexgen.map.MapTileLocationFilter;

/**
 * Class implementing logic for returning a collection of map tile locations that are near to the
 * last location where a tile was added.
 */
public class LocalMapTileLocationFilter implements MapTileLocationFilter, MapTileAddedListener
{
    /**
     * Map tile location filter to use as a base for this filter.
     */
    private final MapTileLocationFilter mapTileLocationFilter;

    /**
     * Location of the last map tile that was added to the map.
     */
    private MapTileLocation lastMapTileLocation;

    /**
     * Construct a local map tile location filter.
     *
     * @param mapTileLocationFilter
     *            Map tile location filter to use as a base for this filter.
     */
    public LocalMapTileLocationFilter( MapTileLocationFilter mapTileLocationFilter )
    {
        this.mapTileLocationFilter = mapTileLocationFilter;
    }

    /**
     * Get the smallest possible X coordinate for allowed map tile locations.
     *
     * @return The smallest possible X coordinate for allowed map tile locations.
     */
    public int getMinX()
    {
        return mapTileLocationFilter.getMinX();
    }

    /**
     * Get the smallest possible Y coordinate for allowed map tile locations.
     *
     * @return The smallest possible Y coordinate for allowed map tile locations.
     */
    public int getMinY()
    {
        return mapTileLocationFilter.getMinY();
    }

    /**
     * Get the largest possible X coordinate for allowed map tile locations.
     *
     * @return The largest possible X coordinate for allowed map tile locations.
     */
    public int getMaxX()
    {
        return mapTileLocationFilter.getMaxX();
    }

    /**
     * Get the largest possible Y coordinate for allowed map tile locations.
     *
     * @return The largest possible Y coordinate for allowed map tile locations.
     */
    public int getMaxY()
    {
        return mapTileLocationFilter.getMaxY();
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
        Collection< MapTileLocation > rawMapTileLocations =
                mapTileLocationFilter.getFilteredMapTileLocations( mapTileLocations );

        Collection< MapTileLocation > localMapTileLocations = new LinkedList< MapTileLocation >();
        int maxDistance = 1;

        if ( ! rawMapTileLocations.isEmpty() )
        {
            while ( localMapTileLocations.isEmpty() )
            {
                for ( MapTileLocation mapTileLocation : rawMapTileLocations )
                {
                    int distance = Math.abs( lastMapTileLocation.getX() - mapTileLocation.getX() ) +
                                   Math.abs( lastMapTileLocation.getY() - mapTileLocation.getY() );

                    if ( distance <= maxDistance )
                    {
                        localMapTileLocations.add( mapTileLocation );
                    }
                }

                maxDistance *= 2;
            }
        }

        return localMapTileLocations;
    }

    /**
     * Informs the listener that a map tile has been added at the specified location.
     *
     * @param mapGenerator
     *            Map generator that added the map tile.
     * @param mapTileLocation
     *            Location at which the map tile was added.
     */
    public void mapTileAdded( MapGenerator mapGenerator, MapTileLocation mapTileLocation )
    {
        lastMapTileLocation = mapTileLocation;
    }
}
