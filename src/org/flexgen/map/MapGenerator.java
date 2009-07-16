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

import java.util.HashMap;
import java.util.Map;


/**
 * Class containing logic for randomly generating a map using a specified set of map tile types.
 */
public class MapGenerator
{
    /**
     * Data structure containing the map. Maps map tile locations to map tiles.
     */
    private final Map< MapTileLocation, MapTile > map;

    /**
     * The size of the map unit array that defines the map tile types used by this map generator.
     */
    private final int tileSize;

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
     * Construct a map generator.
     *
     * @param mapTileTypes
     *            Array of map tile types that define the available map tile types for randomly
     *            generating the map. Cannot be null. Must contain at least one element. No element
     *            can be null. Cannot contain two or more elements that are identical. All map tile
     *            types in the array must be the same size.
     * @param minX
     *            Smallest possible X coordinate for map tiles in the map.
     * @param minY
     *            Smallest possible Y coordinate for map tiles in the map.
     * @param maxX
     *            Largest possible X coordinate for map tiles in the map. Must be greater than or
     *            equal to minX.
     * @param maxY
     *            Largest possible Y coordinate for map tiles in the map. Must be greater than or
     *            equal to minY.
     */
    public MapGenerator( MapTileType[] mapTileTypes, int minX, int minY, int maxX, int maxY )
    {
        if ( mapTileTypes == null )
        {
            throw new IllegalArgumentException( "Parameter 'mapTileTypes' cannot be null." );
        }

        if ( mapTileTypes.length == 0 )
        {
            throw new IllegalArgumentException(
                    "Parameter 'mapTileTypes' must contain at least one element." );
        }

        int tileSize = 0;

        for ( int i = 0; i < mapTileTypes.length; i++ )
        {
            // check for a null element
            if ( mapTileTypes[ i ] == null )
            {
                throw new IllegalArgumentException(
                        "Parameter 'mapTileTypes' must not contain any null elements." );
            }

            // check for a duplicate element
            for ( int j = i + 1; j < mapTileTypes.length; j++ )
            {
                if ( mapTileTypes[ i ].equals( mapTileTypes[ j ] ))
                {
                    throw new IllegalArgumentException(
                            "Parameter 'mapTileTypes' must not contain any duplicate elements." );
                }
            }

            // check to see that all elements are the same size
            if ( tileSize == 0 )
            {
                tileSize = mapTileTypes[ i ].getSize();
            }
            else
            {
                if ( tileSize != mapTileTypes[ i ].getSize() )
                {
                    throw new IllegalArgumentException( "All map tile types in parameter " +
                                                        "'mapTileTypes' must be the same size." );
                }
            }
        }

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

        this.map      = new HashMap< MapTileLocation, MapTile >();
        this.tileSize = tileSize;
        this.minX     = minX;
        this.minY     = minY;
        this.maxX     = maxX;
        this.maxY     = maxY;
    }

    /**
     * Get the size of the map unit array that defines the map tile types used by this map
     * generator.
     *
     * @return The size of the map unit array that defines the map tile types used by this map
     * generator.
     */
    public int getTileSize()
    {
        return tileSize;
    }

    /**
     * Get the smallest possible X coordinate for map tiles in the map.
     *
     * @return The smallest possible X coordinate for map tiles in the map.
     */
    public int getMinX()
    {
        return minX;
    }

    /**
     * Get the smallest possible Y coordinate for map tiles in the map.
     *
     * @return The smallest possible Y coordinate for map tiles in the map.
     */
    public int getMinY()
    {
        return minY;
    }

    /**
     * Get the largest possible X coordinate for map tiles in the map.
     *
     * @return The largest possible X coordinate for map tiles in the map.
     */
    public int getMaxX()
    {
        return maxX;
    }

    /**
     * Get the largest possible Y coordinate for map tiles in the map.
     *
     * @return The largest possible Y coordinate for map tiles in the map.
     */
    public int getMaxY()
    {
        return maxY;
    }

    /**
     * Get the map tile at a specified location.
     *
     * @param mapTileLocation
     *            Location for which to get the map tile.
     *
     * @return The map tile at a specified location.
     */
    public MapTile getMapTile( MapTileLocation mapTileLocation )
    {
        if ( mapTileLocation == null )
        {
            throw new IllegalArgumentException( "Parameter 'mapTileLocation' cannot be null." );
        }

        return map.get( mapTileLocation );
    }

    /**
     * Add a map tile to the map at the specified location.
     *
     * @param mapTileLocation
     *            Location at which to add the map tile.
     * @param mapTile
     *            Map tile to add.
     */
    public void addMapTile( MapTileLocation mapTileLocation, MapTile mapTile )
    {
        if ( mapTileLocation == null )
        {
            throw new IllegalArgumentException( "Parameter 'mapTileLocation' cannot be null." );
        }

        map.put( mapTileLocation, mapTile );
    }
}
